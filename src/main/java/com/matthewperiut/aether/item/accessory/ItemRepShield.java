package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.impl.mixin.client.LivingEntityRendererAccessor;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ItemRepShield extends ItemMoreArmor implements HasCustomRenderer
{
    AccessoryRenderer renderer;

    public ItemRepShield(Identifier i, int j, int k, int l, int col)
    {
        super(i, j, k, l, col);
    }

    @Override
    public Optional<AccessoryRenderer> getRenderer()
    {
        return Optional.ofNullable(renderer);
    }

    @Override
    public void constructRenderer()
    {
        renderer = new EnergyShieldRenderer();
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance)
    {
        repShieldUpdate(player.world, player, itemInstance);
        return itemInstance;
    }

    public void repShieldUpdate(final World world, PlayerEntity player, final ItemStack shieldItem)
    {
        if (world != null && !world.isClient)
        {
            final PlayerInventory inv = player.inventory;
            if ((player.onGround || (player.vehicle != null && player.vehicle.onGround)) && ((LivingEntityAccessor) player).get1029() == 0.0f && ((LivingEntityAccessor) player).get1060() == 0.0f)
            {
                final List list2 = world.getEntities(player, player.boundingBox.expand(4.0, 4.0, 4.0));
                for (int j = 0; j < list2.size() && shieldItem != null && shieldItem.getDamage() < 500; ++j)
                {
                    final Entity entity = (Entity) list2.get(j);
                    boolean flag2 = false;
                    if (entity instanceof ArrowEntity proj && entity.distanceTo(player) < 2.5f && (entity.prevX != entity.x || entity.prevY != entity.y || entity.prevZ != entity.z))
                    {
                        if (proj.owner == null || proj.owner != player)
                        {
                            final Entity dick = proj.owner;
                            proj.owner = player;
                            flag2 = true;
                            double a;
                            double b;
                            double c;
                            if (dick != null)
                            {
                                a = proj.x - dick.x;
                                b = proj.boundingBox.minY - dick.boundingBox.minY;
                                c = proj.z - dick.z;
                            }
                            else
                            {
                                a = player.x - proj.x;
                                b = player.y - proj.y;
                                c = player.z - proj.z;
                            }
                            final double d = Math.sqrt(a * a + b * b + c * c);
                            a /= -d;
                            b /= -d;
                            c /= -d;
                            proj.xVelocity = a * 0.75;
                            proj.yVelocity = b * 0.75 + 0.05;
                            proj.zVelocity = c * 0.75;
                            proj.method_1291(proj.xVelocity, proj.yVelocity, proj.zVelocity, 0.8f, 0.5f);
                            world.playSound(proj, "note.snare", 1.0f, ((rand.nextFloat() - rand.nextFloat()) * 0.4f + 0.8f) * 1.1f);
                            for (int k = 0; k < 12; ++k)
                            {
                                double d2 = -proj.xVelocity * 0.15000000596046448 + (rand.nextFloat() - 0.5f) * 0.05f;
                                double e1 = -proj.yVelocity * 0.15000000596046448 + (rand.nextFloat() - 0.5f) * 0.05f;
                                double f1 = -proj.zVelocity * 0.15000000596046448 + (rand.nextFloat() - 0.5f) * 0.05f;
                                d2 *= 0.625;
                                e1 *= 0.625;
                                f1 *= 0.625;
                                world.addParticle("flame", proj.x, proj.y, proj.z, d2, e1, f1);
                            }
                        }
                    }
                    else if (entity instanceof ArrowEntity proj2 && entity.distanceTo(player) < 2.5f && (entity.prevX != entity.x || entity.prevY != entity.y || entity.prevZ != entity.z))
                    {
                        if (proj2.owner == null || proj2.owner != player)
                        {
                            final Entity dick = proj2.owner;
                            proj2.owner = player;
                            flag2 = true;
                            double a;
                            double b;
                            double c;
                            if (dick != null)
                            {
                                a = proj2.x - dick.x;
                                b = proj2.boundingBox.minY - dick.boundingBox.minY;
                                c = proj2.z - dick.z;
                            }
                            else
                            {
                                a = player.x - proj2.x;
                                b = player.y - proj2.y;
                                c = player.z - proj2.z;
                            }
                            final double d = Math.sqrt(a * a + b * b + c * c);
                            a /= -d;
                            b /= -d;
                            c /= -d;
                            proj2.xVelocity = a * 0.75;
                            proj2.yVelocity = b * 0.75 + 0.15;
                            proj2.zVelocity = c * 0.75;
                            proj2.method_1291(proj2.xVelocity, proj2.yVelocity, proj2.zVelocity, 0.8f, 0.5f);
                            world.playSound(proj2, "note.snare", 1.0f, ((rand.nextFloat() - rand.nextFloat()) * 0.4f + 0.8f) * 1.1f);
                            for (int k = 0; k < 12; ++k)
                            {
                                double d2 = -proj2.xVelocity * 0.15000000596046448 + (rand.nextFloat() - 0.5f) * 0.05f;
                                double e1 = -proj2.yVelocity * 0.15000000596046448 + (rand.nextFloat() - 0.5f) * 0.05f;
                                double f1 = -proj2.zVelocity * 0.15000000596046448 + (rand.nextFloat() - 0.5f) * 0.05f;
                                d2 *= 0.625;
                                e1 *= 0.625;
                                f1 *= 0.625;
                                world.addParticle("flame", proj2.x, proj2.y, proj2.z, d2, e1, f1);
                            }
                        }
                    }
                    if (flag2 && shieldItem != null)
                    {
                        shieldItem.applyDamage(1, null);
                        if (shieldItem.getDamage() >= 500)
                        {
                            player.inventory.armor[6] = null;
                        }
                    }
                }
            }
        }
    }

    private static class EnergyShieldRenderer implements AccessoryRenderer
    {
        static HashMap<PlayerEntity, ShouldRender> prevPos = new HashMap<>();
        BipedEntityModel modelEnergyShield = new BipedEntityModel(1.25F);

        public void renderThirdPerson(PlayerEntity player, PlayerRenderer renderer, ItemStack ItemStack, double x, double y, double z, float h, float v)
        {
            final ItemStack itemstack = player.inventory.getHeldItem();
            modelEnergyShield.swingingRight = (itemstack != null);
            modelEnergyShield.sneaking = player.method_1373();
            double d3 = y - player.standingEyeHeight;
            if (player.method_1373() && !(player instanceof PlayerEntity))
            {
                d3 -= 0.125;
            }
            doRenderEnergyShield(player, renderer, modelEnergyShield, x, d3, z, h, v);
            modelEnergyShield.swingingRight = false;
            modelEnergyShield.sneaking = false;
        }

        @Override
        public void renderHUD(PlayerEntity player, ItemStack ItemStack, Minecraft minecraft, ScreenScaler screenScaler, int scaledWidth, int scaledHeight)
        {
            if (!(player.onGround || (player.vehicle != null && player.vehicle.onGround)))
                return;
            if (!(((LivingEntityAccessor) player).get1029() == 0.0f && ((LivingEntityAccessor) player).get1060() == 0.0f))
                return;
            if (minecraft.options.thirdPerson)
                return;

            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3008);
            GL11.glEnable(2977);
            GL11.glEnable(3042);
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("aether:stationapi/textures/other/shieldEffect.png"));
            final Tessellator tessellator = Tessellator.INSTANCE;
            tessellator.start();
            tessellator.vertex(0.0, scaledHeight, -90.0, 0.0, 1.0);
            tessellator.vertex(scaledWidth, scaledHeight, -90.0, 1.0, 1.0);
            tessellator.vertex(scaledWidth, 0.0, -90.0, 1.0, 0.0);
            tessellator.vertex(0.0, 0.0, -90.0, 0.0, 0.0);
            tessellator.tessellate();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3008);
            GL11.glDisable(2977);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }

        private void doRenderEnergyShield(final LivingEntity entityliving, PlayerRenderer playerRenderer, BipedEntityModel modelEnergyShield, final double d, final double d1, final double d2, final float f, final float f1)
        {
            GL11.glPushMatrix();
            GL11.glEnable(2884);
            modelEnergyShield.handSwingProgress = ((LivingEntityRendererAccessor) playerRenderer).invoke820(entityliving, f1);
            modelEnergyShield.isRiding = entityliving.hasVehicle();
            try
            {
                final float f2 = entityliving.field_1013 + (entityliving.field_1012 - entityliving.field_1013) * f1;
                final float f3 = entityliving.prevYaw + (entityliving.yaw - entityliving.prevYaw) * f1;
                final float f4 = entityliving.prevPitch + (entityliving.pitch - entityliving.prevPitch) * f1;
                ((LivingEntityRendererAccessor) playerRenderer).invoke826(entityliving, d, d1, d2);
                final float f5 = ((LivingEntityRendererAccessor) playerRenderer).invoke828(entityliving, f1);
                ((LivingEntityRendererAccessor) playerRenderer).invoke824(entityliving, f5, f2, f1);
                final float f6 = 0.0625f;
                GL11.glEnable(32826);
                GL11.glScalef(-1.0f, -1.0f, 1.0f);
                ((LivingEntityRendererAccessor) playerRenderer).invoke823(entityliving, f1);
                GL11.glTranslatef(0.0f, -24.0f * f6 - 0.0078125f, 0.0f);
                float f7 = entityliving.field_1048 + (entityliving.limbDistance - entityliving.field_1048) * f1;
                final float f8 = entityliving.field_1050 - entityliving.limbDistance * (1.0f - f1);
                if (f7 > 1.0f)
                {
                    f7 = 1.0f;
                }
                GL11.glEnable(3008);
                if (setEnergyShieldBrightness((PlayerEntity) entityliving, playerRenderer, 0, f1))
                {
                    modelEnergyShield.render(f8, f7, f5, f3 - f2, f4, f6);
                    GL11.glDisable(3042);
                    GL11.glEnable(3008);
                }
                GL11.glDisable(32826);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
            GL11.glEnable(2884);
            GL11.glPopMatrix();
        }

        protected boolean setEnergyShieldBrightness(final PlayerEntity player, PlayerRenderer playerRenderer, final int i, final float f)
        {
            if (i != 0) return false;

            if (prevPos.containsKey(player))
            {
                ShouldRender prev = prevPos.get(player);

                if (System.currentTimeMillis() - prev.time > 100)
                {
                    Vec3d current = new Vec3d(player.x, player.y, player.z);
                    prev.shouldRender = prev.pos.distanceTo(current) < 0.05;
                    prevPos.replace(player, new ShouldRender(current, prev.shouldRender));
                }

                if (prev.shouldRender)
                {
                    ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager.bindTexture(
                            ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager.getTextureId("aether:stationapi/textures/mobs/energyGlow.png"));
                    GL11.glEnable(2977);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }
                else
                {
                    GL11.glEnable(2977);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager.bindTexture(
                            ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager.getTextureId("aether:stationapi/textures/mobs/energyNotGlow.png"));
                }
                return true;
            }
            else
            {
                prevPos.put(player, new ShouldRender(new Vec3d(player.x, player.y, player.z)));
            }

            return false;
        }

        static class ShouldRender
        {
            Vec3d pos;
            long time;
            boolean shouldRender = true;

            ShouldRender(Vec3d pos)
            {
                this.pos = pos;
                this.time = System.currentTimeMillis();
            }

            ShouldRender(Vec3d pos, boolean prevShouldRender)
            {
                this.pos = pos;
                this.time = System.currentTimeMillis();
                this.shouldRender = prevShouldRender;
            }
        }
    }
}
