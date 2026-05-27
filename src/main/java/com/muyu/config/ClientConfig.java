package com.muyu.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.EnumValue<AstralVisionMode> ASTRAL_VISION_MODE;

    public enum AstralVisionMode {
        VANILLA,    // 黑屏+雾收缩
        SPECTATOR,  // 旁观者模式：setupRender强制isSpectator=true（开销大）
        XRAY,       // 透视模式：updateRenderChunks禁用smartCull（开销相较低）
        FADE        // 半透明+轻微雾
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("client");

        ASTRAL_VISION_MODE = builder
                .comment("魂体/灵息状态下的视觉模式",
                        "VANILLA - 原版黑屏效果（沉浸但影响导航）",
                        "SPECTATOR - 旁观者式完全透明（直接跳过视锥剔除，渲染所有已加载区块，性能开销大）",
                        "XRAY - 透视模式（仅禁用遮挡剔除，只渲染视距内的区块，性能相较友好）",
                        "FADE - 半透明淡化（平衡方案）")
                .defineEnum("astralVisionMode", AstralVisionMode.SPECTATOR);

        builder.pop();
        SPEC = builder.build();
    }
}