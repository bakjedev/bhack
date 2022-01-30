package net.bakje.bhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class Yaw implements ClientModInitializer {

    private boolean Yaw = false;

    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("45Yaw", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set 45Yaw to " + !Yaw), false);
                Yaw=!Yaw;
            }

            if (Yaw){
                if (mc.player != null) {
                    mc.player.setYaw(Math.round(mc.player.headYaw/45)*45);

                }
            }
        });
    }
}