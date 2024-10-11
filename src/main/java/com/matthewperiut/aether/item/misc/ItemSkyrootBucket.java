package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.entity.living.EntityAechorPlant;
import com.matthewperiut.aether.entity.living.EntityFlyingCow;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.poison.AetherPoison;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemSkyrootBucket extends TemplateItem {
    public static int sprEmpty;// = ModLoader.addOverride("/gui/items.png", "/aether/items/Bucket.png");
    public static int sprWater;// = ModLoader.addOverride("/gui/items.png", "/aether/items/BucketWater.png");
    public static int sprMilk;// = ModLoader.addOverride("/gui/items.png", "/aether/items/BucketMilk.png");
    public static int sprPoison;// = ModLoader.addOverride("/gui/items.png", "/aether/items/BucketPoison.png");
    public static int sprRemedy;// = ModLoader.addOverride("/gui/items.png", "/aether/items/BucketRemedy.png");

    public ItemSkyrootBucket(Identifier i) {
        super(i);
        this.setHasSubtypes(true);
        this.maxCount = 1;
    }

    public int getTextureId(int damage) {
        if (damage == 3) {
            return sprRemedy;
        } else if (damage == 2) {
            return sprPoison;
        } else if (damage == 1) {
            return sprMilk;
        } else {
            return damage == Block.FLOWING_WATER.id ? sprWater : sprEmpty;
        }
    }

    public String getTranslationKey(ItemStack stack) {
        int i = stack.getDamage();
        if (i > 3 && i != Block.FLOWING_WATER.id) {
            i = 0;
        }

        return this.getTranslationKey() + i;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        // todo: server
        if (world.isRemote)
            return itemstack;

        float f = 1.0F;
        float f1 = entityplayer.prevPitch + (entityplayer.pitch - entityplayer.prevPitch) * f;
        float f2 = entityplayer.prevYaw + (entityplayer.yaw - entityplayer.prevYaw) * f;
        double d = entityplayer.prevX + (entityplayer.x - entityplayer.prevX) * (double)f;
        double d1 = entityplayer.prevY + (entityplayer.y - entityplayer.prevY) * (double)f + 1.62 - (double)entityplayer.standingEyeHeight;
        double d2 = entityplayer.prevZ + (entityplayer.z - entityplayer.prevZ) * (double)f;
        Vec3d vec3d = Vec3d.createCached(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5.0;
        Vec3d vec3d1 = vec3d.add((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        HitResult movingobjectposition = world.raycast(vec3d, vec3d1, itemstack.getDamage() == 0);
        if (((((Minecraft)FabricLoader.getInstance().getGameInstance()).crosshairTarget) == null || (((Minecraft)FabricLoader.getInstance().getGameInstance()).crosshairTarget).entity == null || !((((Minecraft)FabricLoader.getInstance().getGameInstance()).crosshairTarget).entity instanceof EntityAechorPlant))) {
            if (itemstack.getDamage() == 2) {
                if (((AetherPoison) entityplayer).getPoison().afflictPoison()) {
                    itemstack.setDamage(0);
                    return itemstack;
                }
            }
            if (itemstack.getDamage() == 1) {
                itemstack.setDamage(0);
                return itemstack;
            }
        } else if (itemstack.getDamage() == 3 && ((AetherPoison) entityplayer).getPoison().curePoison()) {
            itemstack.setDamage(0);
            return itemstack;
        }

        if (movingobjectposition == null || movingobjectposition.type != HitResultType.BLOCK || itemstack.getDamage() != 0 && itemstack.getDamage() != Block.FLOWING_WATER.id) {
            if (itemstack.getDamage() == 0 && (((Minecraft)FabricLoader.getInstance().getGameInstance()).crosshairTarget) != null && (((Minecraft)FabricLoader.getInstance().getGameInstance()).crosshairTarget).entity != null && ((((Minecraft)FabricLoader.getInstance().getGameInstance()).crosshairTarget).entity instanceof CowEntity ||(((Minecraft)FabricLoader.getInstance().getGameInstance()).crosshairTarget).entity instanceof EntityFlyingCow)) {
                itemstack.setDamage(1);
                return itemstack;
            }
        } else {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            if (!world.canInteract(entityplayer, i, j, k)) {
                return itemstack;
            }

            if (itemstack.getDamage() == 0) {
                if (world.getMaterial(i, j, k) == Material.WATER && world.getBlockMeta(i, j, k) == 0) {
                    world.setBlock(i, j, k, 0);
                    itemstack.setDamage(Block.FLOWING_WATER.id);
                    return itemstack;
                }
            } else {
                if (itemstack.getDamage() <= 3 && itemstack.getDamage() != 0) {
                    return new ItemStack(AetherItems.Bucket);
                }

                if (movingobjectposition.side == 0) {
                    --j;
                }

                if (movingobjectposition.side == 1) {
                    ++j;
                }

                if (movingobjectposition.side == 2) {
                    --k;
                }

                if (movingobjectposition.side == 3) {
                    ++k;
                }

                if (movingobjectposition.side == 4) {
                    --i;
                }

                if (movingobjectposition.side == 5) {
                    ++i;
                }

                if (world.isAir(i, j, k) || !world.getMaterial(i, j, k).isSolid()) {
                    if (world.dimension.evaporatesWater && itemstack.getDamage() == Block.FLOWING_WATER.id) {
                        world.playSound(d + 0.5, d1 + 0.5, d2 + 0.5, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                        for(int l = 0; l < 8; ++l) {
                            world.addParticle("largesmoke", (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
                        }
                    } else {
                        world.setBlock(i, j, k, itemstack.getDamage(), 0);
                    }

                    return new ItemStack(AetherItems.Bucket);
                }
            }
        }

        return itemstack;
    }
}
