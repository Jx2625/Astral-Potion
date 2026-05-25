package com.muyu.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AstralEffect extends MobEffect {

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AstralPotionMod.MODID);

    public static final RegistryObject<MobEffect> ASTRAL = EFFECTS.register("astral",
            () -> new AstralEffect(MobEffectCategory.NEUTRAL, 0x8E44AD));

    public AstralEffect(MobEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}