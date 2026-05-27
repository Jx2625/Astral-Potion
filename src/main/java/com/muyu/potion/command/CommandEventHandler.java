package com.muyu.potion.command;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.muyu.potion.AstralPotionMod;

@Mod.EventBusSubscriber(modid = AstralPotionMod.MODID)
public class CommandEventHandler {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        AstralCommand.register(event.getDispatcher());
    }
}