#!/usr/bin/env bash
# Usage: mcsrc <fully.qualified.ClassName> [--all] [--no-decompile]
#
# WE WILL USE THIS for stationapi classes to understand how they work for networking in our use case.
#
# Examples:
#   ./mcsrc.sh net.minecraft.server.MinecraftServer              # print full source
#   ./mcsrc.sh net.minecraft.server.MinecraftServer | head -50   # first 50 lines
#   ./mcsrc.sh net.minecraft.server.MinecraftServer | tail -50   # last 50 lines
#   ./mcsrc.sh net.minecraft.server.MinecraftServer | sed -n '100,150p'  # lines 100-150
#   ./mcsrc.sh net.minecraft.server.MinecraftServer | grep -n "methodName"  # find method
#   ./mcsrc.sh net.minecraft.server.MinecraftServer | cat -n | less  # scroll with line numbers
#
# Flags:
#   --all          Show all locations if class found in multiple jars
#   --no-decompile Skip vineflower fallback, sources only
set -euo pipefail

if [[ $# -lt 1 ]]; then
  echo "Usage: mcsrc <fully.qualified.ClassName> [--all] [--no-decompile]" >&2
  exit 2
fi

FQCN="$1"
shift || true

SHOW_ALL=0
NO_DECOMPILE=0
for arg in "$@"; do
  case "$arg" in
    --all) SHOW_ALL=1 ;;
    --no-decompile) NO_DECOMPILE=1 ;;
    *) echo "Unknown arg: $arg" >&2; exit 2 ;;
  esac
done

REL_PATH="${FQCN//./\/}.java"
REL_CLASS="${FQCN//./\/}.class"

# Search roots: project-local .gradle only
ROOTS=()
ROOTS+=("$PWD/.gradle")

# Collect candidate sources jars
SOURCE_JARS=()
while IFS= read -r line; do
  SOURCE_JARS+=("$line")
done < <(
  for r in "${ROOTS[@]}"; do
    [[ -d "$r" ]] || continue
    find "$r" -type f -name "*sources*.jar" -o -name "*-sources.jar" 2>/dev/null
  done | awk '!seen[$0]++'
)

# Collect unpacked sources dirs (Loom sometimes leaves these around)
SOURCE_DIRS=()
while IFS= read -r line; do
  SOURCE_DIRS+=("$line")
done < <(
  for r in "${ROOTS[@]}"; do
    [[ -d "$r" ]] || continue
    find "$r" -type f -path "*/$REL_PATH" 2>/dev/null | sed "s#/$REL_PATH\$##"
  done | awk '!seen[$0]++'
)

# Prefer printing from a sources.jar (fast, exact)
FOUND=0
FOUND_LOCATIONS=()

for jar in ${SOURCE_JARS[@]+"${SOURCE_JARS[@]}"}; do
  if unzip -l "$jar" "$REL_PATH" >/dev/null 2>&1; then
    FOUND=1
    FOUND_LOCATIONS+=("$jar")
    if [[ $SHOW_ALL -eq 0 ]]; then
      unzip -p "$jar" "$REL_PATH"
      exit 0
    fi
  fi
done

# Next: check unpacked dirs
for d in ${SOURCE_DIRS[@]+"${SOURCE_DIRS[@]}"}; do
  if [[ -f "$d/$REL_PATH" ]]; then
    FOUND=1
    FOUND_LOCATIONS+=("$d")
    if [[ $SHOW_ALL -eq 0 ]]; then
      cat "$d/$REL_PATH"
      exit 0
    fi
  fi
done

if [[ $SHOW_ALL -eq 1 && $FOUND -eq 1 ]]; then
  echo "Found in:" >&2
  printf '  %s\n' ${FOUND_LOCATIONS[@]+"${FOUND_LOCATIONS[@]}"} >&2
  echo >&2
  # Print newest hit (by mtime) if multiple
  newest="$(for p in ${FOUND_LOCATIONS[@]+"${FOUND_LOCATIONS[@]}"}; do
    stat -c '%Y %n' "$p" 2>/dev/null || stat -f '%m %N' "$p"
  done | sort -nr | head -n1 | cut -d' ' -f2-)"
  if [[ "$newest" == *.jar ]]; then
    unzip -p "$newest" "$REL_PATH"
  else
    cat "$newest/$REL_PATH"
  fi
  exit 0
fi

# Fallback: find class file and decompile (if allowed)
if [[ $NO_DECOMPILE -eq 1 ]]; then
  echo "No .java sources found for $FQCN in gradle caches (and --no-decompile was set)." >&2
  exit 1
fi

# Find jars that might contain the class file
CLASS_JARS=()
while IFS= read -r line; do
  CLASS_JARS+=("$line")
done < <(
  for r in "${ROOTS[@]}"; do
    [[ -d "$r" ]] || continue
    find "$r" -type f -name "*.jar" 2>/dev/null
  done | awk '!seen[$0]++'
)

for jar in ${CLASS_JARS[@]+"${CLASS_JARS[@]}"}; do
  if unzip -l "$jar" "$REL_CLASS" >/dev/null 2>&1; then
    tmp="$(mktemp -d /tmp/mcsrc.XXXXXX)"
    cleanup() { rm -rf "$tmp"; }
    trap cleanup EXIT

    # Extract the class, run vineflower, print .java
    (cd "$tmp" && unzip -q "$jar" "$REL_CLASS")
    # "vineflower" might be "java -jar ..." or a wrapper; assume it's on PATH as user said
    vineflower "$tmp" "$tmp/out" >/dev/null 2>&1 || {
      echo "Vineflower failed. If your vineflower command needs different args, tell me what `vineflower --help` shows." >&2
      exit 1
    }

    out_java="$tmp/out/$REL_CLASS.java"
    if [[ -f "$out_java" ]]; then
      cat "$out_java"
      exit 0
    fi

    # Some vineflower versions output path without .class suffix
    out_java2="$tmp/out/${REL_CLASS%.class}.java"
    if [[ -f "$out_java2" ]]; then
      cat "$out_java2"
      exit 0
    fi

    echo "Decompiled but couldn't locate output .java. Output tree is in: $tmp/out" >&2
    exit 1
  fi
done

echo "Could not locate $FQCN as .java or .class in gradle caches." >&2
echo "Tip: run ./gradlew genSources (Fabric Loom) once, then try again." >&2
exit 1
