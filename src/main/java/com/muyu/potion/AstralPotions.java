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

    // ========== 魂体药水 ==========
    public static final RegistryObject<Potion> SPECTRE = POTIONS.register("spectre",
            () -> new Potion(new net.minecraft.world.effect.MobEffectInstance(
                    SpectreEffect.SPECTRE.get(), 3600, 0)));

    public static final RegistryObject<Potion> LONG_SPECTRE = POTIONS.register("long_spectre",
            () -> new Potion(new net.minecraft.world.effect.MobEffectInstance(
                    SpectreEffect.SPECTRE.get(), 9600, 0)));

    // ========== 灵息药水（Animus） ==========
    public static final RegistryObject<Potion> ANIMUS = POTIONS.register("animus",
            () -> new Potion(new net.minecraft.world.effect.MobEffectInstance(
                    AnimusEffect.ANIMUS.get(), 900, 0)));

    public static final RegistryObject<Potion> LONG_ANIMUS = POTIONS.register("long_animus",
            () -> new Potion(new net.minecraft.world.effect.MobEffectInstance(
                    AnimusEffect.ANIMUS.get(), 2400, 0)));

    public static void registerBrewingRecipes() {
        // 魂体药水配方
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.of(ModItems.SOUL_SHARD.get()),
                PotionUtils.setPotion(new ItemStack(Items.POTION), SPECTRE.get())
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), SPECTRE.get())),
                Ingredient.of(Items.REDSTONE),
                PotionUtils.setPotion(new ItemStack(Items.POTION), LONG_SPECTRE.get())
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), SPECTRE.get())),
                Ingredient.of(Items.GUNPOWDER),
                PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), SPECTRE.get())
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), SPECTRE.get())),
                Ingredient.of(Items.DRAGON_BREATH),
                PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), SPECTRE.get())
        );

        // 灵息药水配方：魂体 + 灵魂碎片
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), SPECTRE.get())),
                Ingredient.of(ModItems.SOUL_SHARD.get()),
                PotionUtils.setPotion(new ItemStack(Items.POTION), ANIMUS.get())
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), LONG_SPECTRE.get())),
                Ingredient.of(ModItems.SOUL_SHARD.get()),
                PotionUtils.setPotion(new ItemStack(Items.POTION), LONG_ANIMUS.get())
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), ANIMUS.get())),
                Ingredient.of(Items.REDSTONE),
                PotionUtils.setPotion(new ItemStack(Items.POTION), LONG_ANIMUS.get())
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), ANIMUS.get())),
                Ingredient.of(Items.GUNPOWDER),
                PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ANIMUS.get())
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ANIMUS.get())),
                Ingredient.of(Items.DRAGON_BREATH),
                PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ANIMUS.get())
        );
    }
}