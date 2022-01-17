package net.bakje.bhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class ragesprint implements ClientModInitializer {

    private boolean Sprint;

    @Override
    public void onInitializeClient() {

        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("RageSprint", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));

        Sprint=!Sprint;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set RageSprint to " + Sprint), false);
                Sprint=!Sprint;
            }

            if (!Sprint){
                if (mc.player != null) {
                    mc.player.setSprinting(true);
                    if (mc.player.sidewaysSpeed !=0 || mc.player.forwardSpeed !=0){
                        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
                    }
                }
            }
        });
    }
}