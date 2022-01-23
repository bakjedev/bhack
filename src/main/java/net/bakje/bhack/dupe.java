package net.bakje.bhack;

import net.bakje.bhack.util.TimerUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class dupe implements ClientModInitializer {

    MinecraftClient mc = MinecraftClient.getInstance();
    private boolean dupe;
    private int a=0;
    public TimerUtil timer = new TimerUtil();

    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("dupe", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        dupe =!dupe;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set dupe to " + dupe), false);
                dupe =!dupe;
            }

            if (!dupe && mc.player != null){
                client.player.sendMessage(new LiteralText("[bhack] waiting..."), false);
                if (timer.passed(5000)) {
                    client.player.sendMessage(new LiteralText("[bhack] a"), false);
                    a=a+1;
                    timer.reset();
                }
                if (a==2) {
                    dotDBypsas();
                    a=0;
                }
            }
        });
    }

    public void dotDBypsas() { //shhhhhhh
        if(mc.player.getVehicle() == null) {
            return;
        }
        mc.player.sendMessage(new LiteralText("[bhack] Trying to kick the player."), false);
        for (int i = 0; i < 38; i++) {
            mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
        }
        dupe=!dupe;
    }
}