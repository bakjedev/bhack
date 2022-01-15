package net.bakje.bhack;

import io.netty.util.internal.MathUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class badfly implements ClientModInitializer {

    private boolean bruhfly;

    @Override
    public void onInitializeClient() {

        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("bruhfly", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "bhack"));
        bruhfly=!bruhfly;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set bruhfly to " + bruhfly), false);
                bruhfly=!bruhfly;
            }

            if(!bruhfly) {
                if (mc.player != null)
                    return;
                double x = mc.player.getVelocity().x;
            }
        });
    }
}