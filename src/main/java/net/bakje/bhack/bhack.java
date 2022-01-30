package net.bakje.bhack;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class bhack implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // idk what to do here
        MinecraftClient mc = MinecraftClient.getInstance();
    }
}