package com.muyu.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.muyu.potion.init.ModItems;

public class AstralPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, AstralPotionMod.MODID);

    // 魂体药水（3分钟）
    public static final RegistryObject<Potion> ASTRAL = POTIONS.register("astral",
            () -> new Potion("astral", new net.minecraft.world.effect.MobEffectInstance(
                    AstralEffect.ASTRAL.get(), 3600, 0))); // 3600 ticks = 3分钟
    // 延长版魂体药水（8分钟）
    public static final RegistryObject<Potion> LONG_ASTRAL = POTIONS.register("long_astral",
            () -> new Potion("astral", new net.minecraft.world.effect.MobEffectInstance(
                    AstralEffect.ASTRAL.get(), 9600, 0))); // 9600 ticks = 8分钟
    // 粗制药水 + 灵魂碎片 = 魂体药水
    public static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.of(ModItems.SOUL_SHARD.get()),
                PotionUtils.setPotion(new ItemStack(Items.POTION), AstralPotions.ASTRAL.get())
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), AstralPotions.ASTRAL.get())),
                Ingredient.of(Items.REDSTONE),
                PotionUtils.setPotion(new ItemStack(Items.POTION), AstralPotions.LONG_ASTRAL.get())
        );


        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), AstralPotions.ASTRAL.get())),
                Ingredient.of(Items.GUNPOWDER),
                PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AstralPotions.ASTRAL.get())
        );


        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AstralPotions.ASTRAL.get())),
                Ingredient.of(Items.DRAGON_BREATH),
                PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AstralPotions.ASTRAL.get())
        );
    }
}