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
public class LevelRendererUpdateChunksMixin {

    @ModifyVariable(
            method = "updateRenderChunks",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0 // boolean p_194367_ (smartCull),禁用遮挡剔除
    )
    private boolean astral$disableOcclusion(boolean smartCull) {
        //XRAY 模式
        if (ClientConfig.ASTRAL_VISION_MODE.get() != AstralVisionMode.XRAY) {
            return smartCull;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return smartCull;

        if (mc.player.hasEffect(SpectreEffect.SPECTRE.get())
                || mc.player.hasEffect(AnimusEffect.ANIMUS.get())) {
            return false;  // 禁用遮挡剔除
        }

        return smartCull;
    }
}