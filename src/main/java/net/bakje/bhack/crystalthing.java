package net.bakje.bhack;

import com.google.gson.internal.Streams;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class crystalthing implements ClientModInitializer {

    private boolean crystalthing;
    private int crystalSlot;

    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("crystalthing", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        crystalthing =!crystalthing;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set crystalthing to " + crystalthing), false);
                crystalthing =!crystalthing;
            }

            if (!crystalthing & mc.player != null){
                for (Entity b: mc.world.getEntities()) {
                    if (b instanceof PlayerEntity) {
                        client.player.sendMessage(new LiteralText("[bhack] " + b.getEntityName() + b.getBlockPos()), false);
                        mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, BlockHitResult.createMissed(Vec3d.ZERO, Direction.DOWN, BlockPos.ORIGIN)));
                        mc.player.interactAt(mc.player, new Vec3d(b.getBlockPos().getX()+1, b.getBlockPos().getY(), b.getBlockPos().getZ()),Hand.MAIN_HAND);
                    }
                }
                for (Entity a: mc.world.getEntities()) {
                    if (a instanceof EndCrystalEntity & mc.player.distanceTo(a) < 5 & mc.player.getHealth() < 10) {
                        mc.interactionManager.attackEntity(mc.player, a);
                    }
                }
            }
        });
    }
}