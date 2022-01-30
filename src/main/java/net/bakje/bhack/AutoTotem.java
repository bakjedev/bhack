package net.bakje.bhack;

import net.bakje.bhack.util.TimerUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

public class AutoTotem implements ClientModInitializer {

    private boolean AutoTotem = false;
    public TimerUtil timer = new TimerUtil();
    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("AutoTotem", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set AutoTotem to " + !AutoTotem), false);
                AutoTotem =!AutoTotem;
            }
            // WARNING!!!! this autototem only moves totems from JUST your HOTBAR to your offhand
            // when your offhand slot is empty, this is purely because when testing on ecme im scared
            // this also isnt silent swap at all and actually switches to your totem client and server side
            if (AutoTotem && mc.player != null) {
                boolean hasTotem = mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING;
                if (!hasTotem) {
                    for (int slot = 0; slot < 9; slot++){
                            if (mc.player.getInventory().getStack(slot).getItem() == Items.TOTEM_OF_UNDYING) { //look for totem
                                if (slot != mc.player.getInventory().selectedSlot) { // if its not already selected
                                    mc.player.getInventory().selectedSlot = slot; // select it
                                    mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(slot)); // send that to server
                                }
                                if (timer.passed(100)) {
                                    mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN)); // honestly dont get the blockpos.origin and direction.down part this line is skidded
                                    timer.reset();
                                }
                            }
                    }
                }
            }
        });
    }
}