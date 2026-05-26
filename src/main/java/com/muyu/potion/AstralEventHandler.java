package com.muyu.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AstralPotionMod.MODID)
public class AstralEventHandler {

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player player)) return;

        boolean hasSpectre = player.hasEffect(SpectreEffect.SPECTRE.get());
        boolean hasAnimus = player.hasEffect(AnimusEffect.ANIMUS.get());

        if (hasAnimus) {
            // 穿墙
            player.noPhysics = true;
            // 创造模式飞行：自由上下移动、无重力
            player.getAbilities().flying = true;
            // 同步能力到客户端
            player.onUpdateAbilities();
        } else if (player.noPhysics) {
            player.noPhysics = false;
            player.onUpdateAbilities();
        }
    }
}