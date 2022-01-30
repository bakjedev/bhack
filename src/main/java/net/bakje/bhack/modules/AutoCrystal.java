package net.bakje.bhack.modules;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;


public class AutoCrystal implements ClientModInitializer {

    private boolean AutoCrystal =false;

    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("AutoCrystal", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set AutoCrystal to " + !AutoCrystal), false);
                AutoCrystal =!AutoCrystal;
            }

            if (AutoCrystal & mc.player != null){
                for (Entity target: mc.world.getEntities()) {
                    if (target instanceof EndCrystalEntity & mc.player.distanceTo(target) < 5 & mc.player.getHealth() > 10) {
                        mc.interactionManager.attackEntity(mc.player, target);
                    }
                    if (target instanceof PlayerEntity & mc.player.distanceTo(target) > 0.1 & mc.player.distanceTo(target) < 5) {
                        double cx = target.getBlockX();
                        double cy = target.getBlockY();
                        double cz = target.getBlockZ();
                        if (mc.world.getBlockState(new BlockPos(cx+1, cy-1, cz)).isOf(Blocks.OBSIDIAN) ||
                                mc.world.getBlockState(new BlockPos(cx+1, cy-1, cz)).isOf(Blocks.BEDROCK)) {
                            if (mc.world.getBlockState(new BlockPos(cx+1, cy, cz)).isOf(Blocks.AIR)) {
                                client.player.sendMessage(new LiteralText("[bhack] AutoCrystal"), false);

                            }
                        }
                    }
                }
            }
        });
    }
}