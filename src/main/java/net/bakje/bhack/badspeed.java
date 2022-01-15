package net.bakje.bhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class badspeed implements ClientModInitializer {

    private boolean bruhspeed;

    @Override
    public void onInitializeClient() {
        // dont use this, this is just me testing with setVelocity
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("LongJump", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        bruhspeed =!bruhspeed;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set LongJump to " + bruhspeed), false);
                bruhspeed =!bruhspeed;
            }

            if(!bruhspeed) {
                if (mc.player != null) {
                    mc.player.setVelocity(mc.player.getVelocity().x*1.1, mc.player.getVelocity().y, mc.player.getVelocity().z*1.1);
                }
            }
        });
    }
}