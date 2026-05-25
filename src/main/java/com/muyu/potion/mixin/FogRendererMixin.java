package com.muyu.potion.mixin;

import com.muyu.potion.AstralEffect;
import com.muyu.potion.config.ClientConfig;
import com.muyu.potion.config.ClientConfig.AstralVisionMode;
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
    private static float astral$distance = 24.0F;

    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V",
                    shift = At.Shift.BEFORE
            ),
            method = "setupFog",
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void astral$setupFog(
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
        if (!astral$renderShadowPhase(cameraEntity)) return;

        AstralVisionMode mode = ClientConfig.ASTRAL_VISION_MODE.get();

        if (mode == AstralVisionMode.SPECTATOR) {
            if (fogType == FogType.NONE) {
                fogData.start = Float.MAX_VALUE;
                fogData.end = Float.MAX_VALUE;
            }
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            boolean lock = astral$getViewBlockingState(livingEntity) != null;

            if (mode == AstralVisionMode.FADE && lock) {
                fogData.start = 8.0f;
                fogData.end = 32.0f;
                return;
            }

            if (astral$distance == f && !lock) return;
            if (astral$distance > 12 && lock) astral$distance -= 2.0F;
            if (!lock && astral$distance < f) astral$distance += 1.0F;

            float h = astral$distance;
            fogData.start = fogData.mode == FogRenderer.FogMode.FOG_SKY ? 0.0f : h * 0.75f;
            fogData.end = h;
        }
    }

    // ===== 补全：辅助方法 =====

    @Unique
    private static boolean astral$renderShadowPhase(Entity entity) {
        return entity instanceof LivingEntity livingEntity
                && livingEntity.hasEffect(AstralEffect.ASTRAL.get());
    }

    @Unique
    @Nullable
    private static BlockState astral$getViewBlockingState(LivingEntity player) {
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