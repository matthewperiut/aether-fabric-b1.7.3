package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemCloudParachute extends TemplateItemBase
{
    public static int tex;// = ModLoader.addOverride("/gui/items.png", "/aether/items/CloudParachute.png");

    public ItemCloudParachute(Identifier i, boolean golden)
    {
        super(i);
        this.setTexturePosition(tex);
        this.maxStackSize = 1;
        this.setDurability(golden ? 20 : 0);
    }

    @Override
    public int getTexturePosition(int i)
    {
        return tex;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer)
    {
        /* todo: entity
        if (EntityCloudParachute.entityHasRoomForCloud(world, entityplayer)) {
            for(int i = 0; i < 32; ++i) {
                EntityCloudParachute.doCloudSmoke(world, entityplayer);
            }

            if (this.id == AetherItems.CloudParachuteGold.id) {
                itemstack.applyDamage(1, entityplayer);
            } else {
                --itemstack.count;
            }

            world.playSound(entityplayer, "cloud", 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
            if (!world.isClient) {
                world.spawnEntity(new EntityCloudParachute(world, entityplayer, this.id == AetherItems.CloudParachuteGold.id));
            }
        }
*/
        return itemstack;
    }

    public int getNameColor(int i)
    {
        return this.id == AetherItems.CloudParachuteGold.id ? 16777087 : 16777215;
    }
}
