package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.Aether;
import com.matthewperiut.aether.entity.living.*;
import com.matthewperiut.aether.entity.projectile.*;
import com.matthewperiut.aether.entity.special.EntityCloudParachute;
import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import com.matthewperiut.aether.entity.special.EntityMiniCloud;
import com.matthewperiut.aether.optional.AetherSPCSupport;
import com.periut.retroapi.entity.EntityRegistration;
import com.periut.retroapi.entity.RetroEntities;
import com.periut.retroapi.entity.client.EntityFactory;
import com.periut.retroapi.entity.client.MobFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class AetherEntities {
    public static final String MOD_ID = Aether.MOD_ID;

    private static void mob(String name, Class<? extends LivingEntity> clazz, MobFactory factory) {
        RetroEntities.register(Aether.id(name), clazz).factory(factory);
    }

    /** Projectiles/specials: StationAPI's @HasTrackingParameters(50, TriState.TRUE, 1) equivalent. */
    private static void projectile(String name, Class<? extends Entity> clazz, EntityFactory factory) {
        RetroEntities.register(Aether.id(name), clazz)
            .factory(factory)
            .tracking(50, 1, EntityRegistration.SEND_VELOCITY_TRUE);
    }

    public static void registerEntities() {
        mob("AechorPlant", EntityAechorPlant.class, EntityAechorPlant::new);
        mob("Aerbunny", EntityAerbunny.class, EntityAerbunny::new);
        mob("Aerwhale", EntityAerwhale.class, EntityAerwhale::new);
        mob("Cockatrice", EntityCockatrice.class, EntityCockatrice::new);
        mob("FlyingCow", EntityFlyingCow.class, EntityFlyingCow::new);
        mob("Mimic", EntityMimic.class, EntityMimic::new);
        mob("Minicloud", EntityMiniCloud.class, EntityMiniCloud::new);
        mob("Moa", EntityMoa.class, EntityMoa::new);
        mob("Slider", EntitySlider.class, EntitySlider::new);
        mob("Phyg", EntityPhyg.class, EntityPhyg::new);
        mob("Sheepuff", EntitySheepuff.class, EntitySheepuff::new);
        mob("Swet", EntitySwet.class, EntitySwet::new);
        mob("Valkyrie", EntityValkyrie.class, EntityValkyrie::new);
        mob("Sentry", EntitySentry.class, EntitySentry::new);
        mob("Whirlwind", EntityWhirly.class, EntityWhirly::new);
        mob("Zephyr", EntityZephyr.class, EntityZephyr::new);
        mob("FireMonster", EntityFireMonster.class, EntityFireMonster::new);
        mob("FireMinion", EntityFireMinion.class, EntityFireMinion::new);

        projectile("AetherLightning", EntityAetherLightning.class, (World w, double x, double y, double z) -> new EntityAetherLightning(w, x, y, z));
        projectile("FlamingArrow", EntityFlamingArrow.class, (World w, double x, double y, double z) -> new EntityFlamingArrow(w, x, y, z));
        projectile("ZephyrSnowball", EntityZephyrSnowball.class, (World w, double x, double y, double z) -> new EntityZephyrSnowball(w, x, y, z));
        projectile("PoisonNeedle", EntityPoisonNeedle.class, (World w, double x, double y, double z) -> new EntityPoisonNeedle(w, x, y, z));
        projectile("PoisonDart", EntityDartPoison.class, (World w, double x, double y, double z) -> new EntityDartPoison(w, x, y, z));
        projectile("GoldenDart", EntityDartGolden.class, (World w, double x, double y, double z) -> new EntityDartGolden(w, x, y, z));
        projectile("EnchantedDart", EntityDartEnchanted.class, (World w, double x, double y, double z) -> new EntityDartEnchanted(w, x, y, z));
        projectile("CloudParachute", EntityCloudParachute.class, (World w, double x, double y, double z) -> new EntityCloudParachute(w, x, y, z));
        projectile("FloatingBlock", EntityFloatingBlock.class, (World w, double x, double y, double z) -> new EntityFloatingBlock(w, x, y, z));
        projectile("FiroBall", EntityFiroBall.class, (World w, double x, double y, double z) -> new EntityFiroBall(w, x, y, z));
        projectile("HomeShot", EntityHomeShot.class, (World w, double x, double y, double z) -> {
            EntityHomeShot e = new EntityHomeShot(w);
            e.setPosition(x, y, z);
            return e;
        });
        projectile("LightningKnife", EntityLightningKnife.class, (World w, double x, double y, double z) -> new EntityLightningKnife(w, x, y, z));
        projectile("NotchWave", EntityNotchWave.class, (World w, double x, double y, double z) -> new EntityNotchWave(w, x, y, z));

        if (FabricLoader.getInstance().isModLoaded("spc")) {
            AetherSPCSupport.init();
        }
    }
}
