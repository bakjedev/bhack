package net.bakje.bhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class ElytraEquip implements ClientModInitializer {

    private int elytraSlot;

    @Override
    public void onInitializeClient() {
        //this is for testing purposes. and learning how to find items in inventory, basically useless
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("ElytraEquip", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Equipped elytra"), false);
                int currentSlot = mc.player.getInventory().selectedSlot;
                if (mc.player != null) {
                    // this do the finding of the elytra in your inventory
                    for (int slot= 0; slot < 36; slot++){
                        ItemStack stack = mc.player.getInventory().getStack(slot);
                        if (stack.getItem() instanceof ElytraItem && slot<=8) {
                            elytraSlot = slot;
                            // this thing make it do the silent swap
                            mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(elytraSlot));
                            mc.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND));
                            mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(currentSlot));
                        }
                    }
                }
            }
        });
    }
}