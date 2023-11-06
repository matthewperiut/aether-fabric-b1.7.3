package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemDartShooter extends TemplateItemBase
{
    public static int sprNormal;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartShooter.png");
    public static int sprPoison;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartShooterPoison.png");
    public static int sprEnchanted;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartShooterEnchanted.png");

    public ItemDartShooter(Identifier i)
    {
        super(i);
        this.setHasSubItems(true);
        this.maxStackSize = 1;
    }

    public int getTexturePosition(int damage)
    {
        if (damage == 0)
        {
            return sprNormal;
        }
        else if (damage == 1)
        {
            return sprPoison;
        }
        else
        {
            return damage == 2 ? sprEnchanted : sprNormal;
        }
    }

    public String getTranslationKey(ItemStack stack)
    {
        int i = stack.getMeta();
        return this.getTranslationKey() + i;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer)
    {
        int consume = this.consumeItem(entityplayer, AetherItems.Dart.id, itemstack.getMeta());
        if (consume != -1)
        {
            world.playSound(entityplayer, "aether.sound.other.dartshooter.shootDart", 2.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClient)
            {
                /*
                todo: entity
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

                world.spawnEntity((Entity)dart);
                 */
            }
        }

        return itemstack;
    }

    private int consumeItem(PlayerEntity player, int itemID, int maxDamage)
    {
        Inventory inv = player.inventory;

        for (int i = 0; i < inv.getInventorySize(); ++i)
        {
            ItemStack stack = inv.getInventoryItem(i);
            if (stack != null)
            {
                int damage = stack.getMeta();
                if (stack.itemId == itemID && stack.getMeta() == maxDamage)
                {
                    --stack.count;
                    if (stack.count == 0)
                    {
                        stack = null;
                    }

                    inv.setInventoryItem(i, stack);
                    return damage;
                }
            }
        }

        return -1;
    }
}
