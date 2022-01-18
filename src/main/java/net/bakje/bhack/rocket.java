package net.bakje.bhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class rocket implements ClientModInitializer {

    private boolean rocket;
    private int rocketSlot;

    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("Rocket", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        rocket =!rocket;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] did a rocket "), false);
                int currentSlot = mc.player.getInventory().selectedSlot;
                for (int slot= 0; slot < 36; slot++){
                    ItemStack stack = mc.player.getInventory().getStack(slot);
                    if (stack.getItem() instanceof FireworkRocketItem && slot<=8) {
                        rocketSlot = slot;
                        // this thing make it do the silent swap
                        mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(rocketSlot));
                        mc.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND));
                        mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(currentSlot));
                    }
                }
            }

            if (!rocket){

                if (mc.player != null) {

                }
            }
        });
    }
    }