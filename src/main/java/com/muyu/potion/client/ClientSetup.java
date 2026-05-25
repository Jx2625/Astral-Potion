package com.muyu.potion.client;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ClientSetup::clientSetup);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        // 注册Forge事件总线的事件监听器
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new CameraEvents());
    }
}