package com.muyu.potion.mixin.client;

import com.muyu.potion.SpectreEffect;
import com.muyu.potion.AnimusEffect;
import com.muyu.config.ClientConfig;
import com.muyu.config.ClientConfig.AstralVisionMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class LevelRendererSetupRenderMixin {

    @ModifyVariable(
            method = "setupRender(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;ZZ)V",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 1  // 第2个boolean参数（isSpectator）,跳过视锥剔除
    )
    private boolean astral$forceSpectatorForCulling(boolean isSpectator) {
        if (isSpectator) return true;
        //SPECTATOR 模式
        if (ClientConfig.ASTRAL_VISION_MODE.get() != AstralVisionMode.SPECTATOR) {
            return false;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;

        if (mc.player.hasEffect(SpectreEffect.SPECTRE.get())
                || mc.player.hasEffect(AnimusEffect.ANIMUS.get())) {
            return true;
        }

        return isSpectator;
    }
}