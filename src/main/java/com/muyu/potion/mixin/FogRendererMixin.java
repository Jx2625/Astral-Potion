package com.muyu.potion.mixin;

import com.muyu.potion.SpectreEffect;
import com.muyu.potion.AnimusEffect;
import com.muyu.config.ClientConfig;
import com.muyu.config.ClientConfig.AstralVisionMode;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Unique
    private static float spectre$distance = 24.0F;

    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V",
                    shift = At.Shift.BEFORE
            ),
            method = "setupFog",
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void spectre$setupFog(
            Camera camera,
            FogRenderer.FogMode fogMode,
            float f,
            boolean bl,
            float g,
            CallbackInfo ci,
            FogType fogType,
            Entity entity,
            FogRenderer.FogData fogData
    ) {
        Entity cameraEntity = camera.getEntity();
        if (!spectre$renderShadowPhase(cameraEntity)) return;

        AstralVisionMode mode = ClientConfig.ASTRAL_VISION_MODE.get();

        if (mode == AstralVisionMode.SPECTATOR) {
            if (fogType == FogType.NONE) {
                fogData.start = Float.MAX_VALUE;
                fogData.end = Float.MAX_VALUE;
            }
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            boolean lock = spectre$getViewBlockingState(livingEntity) != null;

            if (mode == AstralVisionMode.FADE && lock) {
                fogData.start = 8.0f;
                fogData.end = 32.0f;
                return;
            }

            if (spectre$distance == f && !lock) return;
            if (spectre$distance > 12 && lock) spectre$distance -= 2.0F;
            if (!lock && spectre$distance < f) spectre$distance += 1.0F;

            float h = spectre$distance;
            fogData.start = fogData.mode == FogRenderer.FogMode.FOG_SKY ? 0.0f : h * 0.75f;
            fogData.end = h;
        }
    }

    // ===== 辅助方法 =====

    @Unique
    private static boolean spectre$renderShadowPhase(Entity entity) {
        return entity instanceof LivingEntity livingEntity
                && (livingEntity.hasEffect(SpectreEffect.SPECTRE.get())
                || livingEntity.hasEffect(AnimusEffect.ANIMUS.get()));
    }

    @Unique
    @Nullable
    private static BlockState spectre$getViewBlockingState(LivingEntity player) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 8; ++i) {
            double d = player.getX() + (double)(((float)((i) % 2) - 0.5f) * player.getBbWidth() * 0.8f);
            double e = player.getEyeY() + (double)(((float)((i >> 1) % 2) - 0.5f) * 0.1f);
            double f = player.getZ() + (double)(((float)((i >> 2) % 2) - 0.5f) * player.getBbWidth() * 0.8f);
            mutableBlockPos.set(d, e, f);
            BlockState blockState = player.level().getBlockState(mutableBlockPos);
            if (blockState.getRenderShape() == RenderShape.INVISIBLE
                    || !blockState.isViewBlocking(player.level(), mutableBlockPos)) {
                continue;
            }
            return blockState;
        }
        return null;
    }
}