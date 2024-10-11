package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.entity.projectile.EntityDartEnchanted;
import com.matthewperiut.aether.entity.projectile.EntityDartGolden;
import com.matthewperiut.aether.entity.projectile.EntityDartPoison;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemDartShooter extends TemplateItem {
    public static int sprNormal;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartShooter.png");
    public static int sprPoison;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartShooterPoison.png");
    public static int sprEnchanted;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartShooterEnchanted.png");

    public ItemDartShooter(Identifier i) {
        super(i);
        this.setHasSubtypes(true);
        this.maxCount = 1;
    }

    public int getTextureId(int damage) {
        if (damage == 0) {
            return sprNormal;
        } else if (damage == 1) {
            return sprPoison;
        } else {
            return damage == 2 ? sprEnchanted : sprNormal;
        }
    }

    public String getTranslationKey(ItemStack stack) {
        int i = stack.getDamage();
        return this.getTranslationKey() + i;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        int consume = this.consumeItem(entityplayer, AetherItems.Dart.id, itemstack.getDamage());
        if (consume != -1) {
            world.playSound(entityplayer, "aether:other.dartshooter.shootdart", 2.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                EntityDartGolden dart = null;
                if (consume == 1) {
                    dart = new EntityDartPoison(world, entityplayer);
                }

                if (consume == 2) {
                    dart = new EntityDartEnchanted(world, entityplayer);
                }

                if (dart == null) {
                    dart = new EntityDartGolden(world, entityplayer);
                }

                world.spawnEntity((Entity) dart);
            }
        }

        return itemstack;
    }

    private int consumeItem(PlayerEntity player, int itemID, int maxDamage) {
        Inventory inv = player.inventory;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (stack != null) {
                int damage = stack.getDamage();
                if (stack.itemId == itemID && stack.getDamage() == maxDamage) {
                    --stack.count;
                    if (stack.count == 0) {
                        stack = null;
                    }

                    inv.setStack(i, stack);
                    return damage;
                }
            }
        }

        return -1;
    }
}
