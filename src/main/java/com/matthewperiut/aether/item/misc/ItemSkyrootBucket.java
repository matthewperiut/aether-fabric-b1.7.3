package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.entity.living.EntityAechorPlant;
import com.matthewperiut.aether.entity.living.EntityFlyingCow;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.poison.AetherPoison;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.animal.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
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
        this.setHasSubItems(true);
        this.maxStackSize = 1;
    }

    public int getTexturePosition(int damage) {
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
        int i = stack.getMeta();
        if (i > 3 && i != Block.FLOWING_WATER.id) {
            i = 0;
        }

        return this.getTranslationKey() + i;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        // todo: server
        if (world.isClient)
            return itemstack;

        float f = 1.0F;
        float f1 = entityplayer.prevPitch + (entityplayer.pitch - entityplayer.prevPitch) * f;
        float f2 = entityplayer.prevYaw + (entityplayer.yaw - entityplayer.prevYaw) * f;
        double d = entityplayer.prevX + (entityplayer.x - entityplayer.prevX) * (double)f;
        double d1 = entityplayer.prevY + (entityplayer.y - entityplayer.prevY) * (double)f + 1.62 - (double)entityplayer.standingEyeHeight;
        double d2 = entityplayer.prevZ + (entityplayer.z - entityplayer.prevZ) * (double)f;
        Vec3d vec3d = Vec3d.from(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5.0;
        Vec3d vec3d1 = vec3d.translate((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        HitResult movingobjectposition = world.method_161(vec3d, vec3d1, itemstack.getMeta() == 0);
        if (((((Minecraft)FabricLoader.getInstance().getGameInstance()).hitResult) == null || (((Minecraft)FabricLoader.getInstance().getGameInstance()).hitResult).field_1989 == null || !((((Minecraft)FabricLoader.getInstance().getGameInstance()).hitResult).field_1989 instanceof EntityAechorPlant))) {
            if (itemstack.getMeta() == 2) {
                if (((AetherPoison) entityplayer).getPoison().afflictPoison()) {
                    itemstack.setMeta(0);
                    return itemstack;
                }
            }
            if (itemstack.getMeta() == 1) {
                itemstack.setMeta(0);
                return itemstack;
            }
        } else if (itemstack.getMeta() == 3 && ((AetherPoison) entityplayer).getPoison().curePoison()) {
            itemstack.setMeta(0);
            return itemstack;
        }

        if (movingobjectposition == null || movingobjectposition.type != HitType.field_789 || itemstack.getMeta() != 0 && itemstack.getMeta() != Block.FLOWING_WATER.id) {
            if (itemstack.getMeta() == 0 && (((Minecraft)FabricLoader.getInstance().getGameInstance()).hitResult) != null && (((Minecraft)FabricLoader.getInstance().getGameInstance()).hitResult).field_1989 != null && ((((Minecraft)FabricLoader.getInstance().getGameInstance()).hitResult).field_1989 instanceof CowEntity ||(((Minecraft)FabricLoader.getInstance().getGameInstance()).hitResult).field_1989 instanceof EntityFlyingCow)) {
                itemstack.setMeta(1);
                return itemstack;
            }
        } else {
            int i = movingobjectposition.x;
            int j = movingobjectposition.y;
            int k = movingobjectposition.z;
            if (!world.method_171(entityplayer, i, j, k)) {
                return itemstack;
            }

            if (itemstack.getMeta() == 0) {
                if (world.getMaterial(i, j, k) == Material.WATER && world.getBlockMeta(i, j, k) == 0) {
                    world.setBlock(i, j, k, 0);
                    itemstack.setMeta(Block.FLOWING_WATER.id);
                    return itemstack;
                }
            } else {
                if (itemstack.getMeta() <= 3 && itemstack.getMeta() != 0) {
                    return new ItemStack(AetherItems.Bucket);
                }

                if (movingobjectposition.field_1987 == 0) {
                    --j;
                }

                if (movingobjectposition.field_1987 == 1) {
                    ++j;
                }

                if (movingobjectposition.field_1987 == 2) {
                    --k;
                }

                if (movingobjectposition.field_1987 == 3) {
                    ++k;
                }

                if (movingobjectposition.field_1987 == 4) {
                    --i;
                }

                if (movingobjectposition.field_1987 == 5) {
                    ++i;
                }

                if (world.isAir(i, j, k) || !world.getMaterial(i, j, k).isSolid()) {
                    if (world.dimension.evaporatesWater && itemstack.getMeta() == Block.FLOWING_WATER.id) {
                        world.playSound(d + 0.5, d1 + 0.5, d2 + 0.5, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                        for(int l = 0; l < 8; ++l) {
                            world.addParticle("largesmoke", (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
                        }
                    } else {
                        world.placeBlockWithMetaData(i, j, k, itemstack.getMeta(), 0);
                    }

                    return new ItemStack(AetherItems.Bucket);
                }
            }
        }

        return itemstack;
    }
}
