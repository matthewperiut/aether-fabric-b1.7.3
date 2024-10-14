package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.impl.mixin.client.LivingEntityRendererAccessor;
import com.matthewperiut.aether.entity.projectile.EntityFlamingArrow;
import com.matthewperiut.aether.entity.projectile.EntityProjectileBase;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ItemRepShield extends ItemMoreArmor implements HasCustomRenderer {
    AccessoryRenderer renderer;

    public ItemRepShield(Identifier i, int j, int k, int l, int col) {
        super(i, j, k, l, col);
    }

    @Override
    public AccessoryRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void constructRenderer() {
        renderer = new EnergyShieldRenderer();
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance) {
        repShieldUpdate(player.world, player, itemInstance);
        return itemInstance;
    }

    public void repShieldUpdate(final World world, PlayerEntity player, final ItemStack shieldItem) {
        if (world != null && !world.isRemote) {
            final PlayerInventory inv = player.inventory;
            if ((player.onGround || (player.vehicle != null && player.vehicle.onGround)) && ((LivingEntityAccessor) player).getForwardVelocity() == 0.0f && ((LivingEntityAccessor) player).getHorizontalVelocity() == 0.0f) {
                final List list2 = world.getEntities(player, player.boundingBox.expand(4.0, 4.0, 4.0));
                for (int j = 0; j < list2.size() && shieldItem != null && shieldItem.getDamage2() < 500; ++j) {
                    final Entity entity = (Entity) list2.get(j);
                    boolean flag2 = false;
                    if (entity instanceof EntityFlamingArrow proj && entity.getDistance(player) < 2.5f && (entity.prevX != entity.x || entity.prevY != entity.y || entity.prevZ != entity.z)) {
                        if (proj.owner == null || proj.owner != player) {
                            final Entity dick = proj.owner;
                            proj.owner = player;
                            flag2 = true;
                            double a;
                            double b;
                            double c;
                            if (dick != null) {
                                a = proj.x - dick.x;
                                b = proj.boundingBox.minY - dick.boundingBox.minY;
                                c = proj.z - dick.z;
                            } else {
                                a = player.x - proj.x;
                                b = player.y - proj.y;
                                c = player.z - proj.z;
                            }
                            final double d = Math.sqrt(a * a + b * b + c * c);
                            a /= -d;
                            b /= -d;
                            c /= -d;
                            proj.velocityX = a * 0.75;
                            proj.velocityY = b * 0.75 + 0.05;
                            proj.velocityZ = c * 0.75;
                            //method_1291
                            proj.setArrowHeading(proj.velocityX, proj.velocityY, proj.velocityZ, 0.8f, 0.5f);
                            world.playSound(proj, "note.snare", 1.0f, ((random.nextFloat() - random.nextFloat()) * 0.4f + 0.8f) * 1.1f);
                            for (int k = 0; k < 12; ++k) {
                                double d2 = -proj.velocityX * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                double e1 = -proj.velocityY * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                double f1 = -proj.velocityZ * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                d2 *= 0.625;
                                e1 *= 0.625;
                                f1 *= 0.625;
                                world.addParticle("flame", proj.x, proj.y, proj.z, d2, e1, f1);
                            }
                        }
                    } else if ((entity instanceof EntityProjectileBase projectile) && entity.getDistance(player) < 2.5f && (entity.prevX != entity.x || entity.prevY != entity.y || entity.prevZ != entity.z)) {
                        if (projectile.owner == null || projectile.owner != player) {
                            final Entity dick = projectile.owner;
                            projectile.owner = player;
                            flag2 = true;
                            double a;
                            double b;
                            double c;
                            if (dick != null) {
                                a = projectile.x - dick.x;
                                b = projectile.boundingBox.minY - dick.boundingBox.minY;
                                c = projectile.z - dick.z;
                            } else {
                                a = player.x - projectile.x;
                                b = player.y - projectile.y;
                                c = player.z - projectile.z;
                            }
                            final double d = Math.sqrt(a * a + b * b + c * c);
                            a /= -d;
                            b /= -d;
                            c /= -d;
                            projectile.velocityX = a * 0.75;
                            projectile.velocityY = b * 0.75 + 0.15;
                            projectile.velocityZ = c * 0.75;
                            projectile.setArrowHeading(projectile.velocityX, projectile.velocityY, projectile.velocityZ, 0.8f, 0.5f);
                            world.playSound(projectile, "note.snare", 1.0f, ((random.nextFloat() - random.nextFloat()) * 0.4f + 0.8f) * 1.1f);
                            for (int k = 0; k < 12; ++k) {
                                double d2 = -projectile.velocityX * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                double e1 = -projectile.velocityY * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                double f1 = -projectile.velocityZ * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                d2 *= 0.625;
                                e1 *= 0.625;
                                f1 *= 0.625;
                                world.addParticle("flame", projectile.x, projectile.y, projectile.z, d2, e1, f1);
                            }
                        }
                    } else if (entity instanceof EntityFlamingArrow proj && entity.getDistance(player) < 2.5f && (entity.prevX != entity.x || entity.prevY != entity.y || entity.prevZ != entity.z)) {
                        if (proj.owner == null || proj.owner != player) {
                            final Entity dick = proj.owner;
                            proj.owner = player;
                            flag2 = true;
                            double a;
                            double b;
                            double c;
                            if (dick != null) {
                                a = proj.x - dick.x;
                                b = proj.boundingBox.minY - dick.boundingBox.minY;
                                c = proj.z - dick.z;
                            } else {
                                a = player.x - proj.x;
                                b = player.y - proj.y;
                                c = player.z - proj.z;
                            }
                            final double d = Math.sqrt(a * a + b * b + c * c);
                            a /= -d;
                            b /= -d;
                            c /= -d;
                            proj.velocityX = a * 0.75;
                            proj.velocityY = b * 0.75 + 0.05;
                            proj.velocityZ = c * 0.75;
                            //method_1291
                            proj.setArrowHeading(proj.velocityX, proj.velocityY, proj.velocityZ, 0.8f, 0.5f);
                            world.playSound(proj, "note.snare", 1.0f, ((random.nextFloat() - random.nextFloat()) * 0.4f + 0.8f) * 1.1f);
                            for (int k = 0; k < 12; ++k) {
                                double d2 = -proj.velocityX * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                double e1 = -proj.velocityY * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                double f1 = -proj.velocityZ * 0.15000000596046448 + (random.nextFloat() - 0.5f) * 0.05f;
                                d2 *= 0.625;
                                e1 *= 0.625;
                                f1 *= 0.625;
                                world.addParticle("flame", proj.x, proj.y, proj.z, d2, e1, f1);
                            }
                        }
                        if (flag2 && shieldItem != null) {
                            shieldItem.damage(1, null);
                            if (shieldItem.getDamage2() >= 500) {
                                player.inventory.armor[6] = null;
                            }
                        }
                    }
                }
            }
        }
    }

    private static class EnergyShieldRenderer implements AccessoryRenderer {
        static HashMap<PlayerEntity, ShouldRender> prevPos = new HashMap<>();
        BipedEntityModel modelEnergyShield = new BipedEntityModel(1.25F);

        public void renderThirdPerson(PlayerEntity player, PlayerEntityRenderer renderer, ItemStack ItemStack, double x, double y, double z, float h, float v) {
            final ItemStack itemstack = player.inventory.getSelectedItem();
            modelEnergyShield.rightArmPose = (itemstack != null);
            modelEnergyShield.sneaking = player.isSneaking();
            double d3 = y - player.standingEyeHeight;
            if (player.isSneaking() && !(player instanceof PlayerEntity)) {
                d3 -= 0.125;
            }
            doRenderEnergyShield(player, renderer, modelEnergyShield, x, d3, z, h, v);
            modelEnergyShield.rightArmPose = false;
            modelEnergyShield.sneaking = false;
        }

        @Override
        public void renderHUD(PlayerEntity player, ItemStack ItemStack, Minecraft minecraft, ScreenScaler screenScaler, int scaledWidth, int scaledHeight) {
            if (!(player.onGround || (player.vehicle != null && player.vehicle.onGround)))
                return;
            if (!(((LivingEntityAccessor) player).getForwardVelocity() == 0.0f && ((LivingEntityAccessor) player).getHorizontalVelocity() == 0.0f))
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
            tessellator.startQuads();
            tessellator.vertex(0.0, scaledHeight, -90.0, 0.0, 1.0);
            tessellator.vertex(scaledWidth, scaledHeight, -90.0, 1.0, 1.0);
            tessellator.vertex(scaledWidth, 0.0, -90.0, 1.0, 0.0);
            tessellator.vertex(0.0, 0.0, -90.0, 0.0, 0.0);
            tessellator.draw();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3008);
            GL11.glDisable(2977);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }

        private void doRenderEnergyShield(final LivingEntity entityliving, PlayerEntityRenderer playerRenderer, BipedEntityModel modelEnergyShield, final double d, final double d1, final double d2, final float f, final float f1) {
            GL11.glPushMatrix();
            GL11.glEnable(2884);
            modelEnergyShield.handSwingProgress = ((LivingEntityRendererAccessor) playerRenderer).invoke820(entityliving, f1);
            modelEnergyShield.riding = entityliving.hasVehicle();
            try {
                final float f2 = entityliving.lastBodyYaw + (entityliving.bodyYaw - entityliving.lastBodyYaw) * f1;
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
                float f7 = entityliving.lastWalkAnimationSpeed + (entityliving.walkAnimationSpeed - entityliving.lastWalkAnimationSpeed) * f1;
                final float f8 = entityliving.walkAnimationProgress - entityliving.walkAnimationSpeed * (1.0f - f1);
                if (f7 > 1.0f) {
                    f7 = 1.0f;
                }
                GL11.glEnable(3008);
                if (setEnergyShieldBrightness((PlayerEntity) entityliving, playerRenderer, 0, f1)) {
                    modelEnergyShield.render(f8, f7, f5, f3 - f2, f4, f6);
                    GL11.glDisable(3042);
                    GL11.glEnable(3008);
                }
                GL11.glDisable(32826);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            GL11.glEnable(2884);
            GL11.glPopMatrix();
        }

        protected boolean setEnergyShieldBrightness(final PlayerEntity player, PlayerEntityRenderer playerRenderer, final int i, final float f) {
            if (i != 0) return false;

            if (prevPos.containsKey(player)) {
                ShouldRender prev = prevPos.get(player);

                if (System.currentTimeMillis() - prev.time > 100) {
                    Vec3d current = new Vec3d(player.x, player.y, player.z);
                    prev.shouldRender = prev.pos.distanceTo(current) < 0.05;
                    prevPos.replace(player, new ShouldRender(current, prev.shouldRender));
                }

                if (prev.shouldRender) {
                    ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager.bindTexture(
                            ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager.getTextureId("aether:stationapi/textures/mobs/energyGlow.png"));
                    GL11.glEnable(2977);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                } else {
                    GL11.glEnable(2977);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager.bindTexture(
                            ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager.getTextureId("aether:stationapi/textures/mobs/energyNotGlow.png"));
                }
                return true;
            } else {
                prevPos.put(player, new ShouldRender(new Vec3d(player.x, player.y, player.z)));
            }

            return false;
        }

        static class ShouldRender {
            Vec3d pos;
            long time;
            boolean shouldRender = true;

            ShouldRender(Vec3d pos) {
                this.pos = pos;
                this.time = System.currentTimeMillis();
            }

            ShouldRender(Vec3d pos, boolean prevShouldRender) {
                this.pos = pos;
                this.time = System.currentTimeMillis();
                this.shouldRender = prevShouldRender;
            }
        }
    }
}
