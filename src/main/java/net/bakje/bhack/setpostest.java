package net.bakje.bhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class setpostest implements ClientModInitializer {

    private boolean setpostest;
    private Object entity;

    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("setpostest", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        setpostest =!setpostest;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set setpostest to " + setpostest), false);
                setpostest =!setpostest;
            }

            if (!setpostest){
                if (mc.player != null) {
                    double playerX = mc.player.getX();
                    double playerY = mc.player.getY();
                    double playerZ = mc.player.getZ();
                    mc.player.setPos(playerX, playerY+1, playerZ);
                }
            }
        });
    }
}