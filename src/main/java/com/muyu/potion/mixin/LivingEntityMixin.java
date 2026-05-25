package com.muyu.potion.mixin;

import com.muyu.potion.SpectreEffect;
import com.muyu.potion.AnimusEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At("HEAD"), method = "isInWall", cancellable = true)
    private void spectre$isInWall(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        if (self.hasEffect(SpectreEffect.SPECTRE.get())
                || self.hasEffect(AnimusEffect.ANIMUS.get())) {
            cir.setReturnValue(false);
        }
    }
}