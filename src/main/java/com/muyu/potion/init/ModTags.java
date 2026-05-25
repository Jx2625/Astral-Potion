package com.muyu.potion.init;

import com.muyu.potion.AstralPotionMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    // 魂体效果无法穿过的方块（如基岩）
    public static final TagKey<Block> OMIT_SPECTRE = BlockTags.create(
            new ResourceLocation(AstralPotionMod.MODID, "omit_spectre"));
}