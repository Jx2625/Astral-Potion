package com.muyu.potion;

import com.muyu.potion.client.ClientSetup;
import com.muyu.potion.command.CommandEventHandler;
import com.muyu.config.ClientConfig;
import com.muyu.potion.init.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(AstralPotionMod.MODID)
public class AstralPotionMod {
    public static final String MODID = "astralpotion";

    public AstralPotionMod() {
        // 注册客户端配置
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.CLIENT,
                ClientConfig.SPEC
        );

        // 先获取事件总线
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // 注册灵魂碎片
        ModItems.ITEMS.register(modEventBus);

        SpectreEffect.EFFECTS.register(modEventBus);     //魂体效果
        AnimusEffect.EFFECTS.register(modEventBus);      //灵息效果
        AstralPotions.POTIONS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientSetup.register(modEventBus);
        }

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(AstralEventHandler.class);
        MinecraftForge.EVENT_BUS.register(CommandEventHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(AstralPotions::registerBrewingRecipes);
    }
}