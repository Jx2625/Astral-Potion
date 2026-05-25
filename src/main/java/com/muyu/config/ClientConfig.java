package com.muyu.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.EnumValue<AstralVisionMode> ASTRAL_VISION_MODE;

    public enum AstralVisionMode {
        VANILLA,    //当前，黑屏+雾收缩
        SPECTATOR,  //旁观者模式：完全透明，无视觉惩罚
        FADE    //折中：半透明+轻微雾，保留氛围感
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("client");

        ASTRAL_VISION_MODE = builder
                .comment("魂体/灵息状态下的视觉模式",
                        "VANILLA - 原版黑屏效果（沉浸但影响导航）",
                        "SPECTATOR - 旁观者式完全透明（清晰但可能破坏氛围)",
                        "FADE - 半透明淡化（平衡方案）")
                .defineEnum("astralVisionMode", AstralVisionMode.SPECTATOR);

        builder.pop();
        SPEC = builder.build();
    }
}