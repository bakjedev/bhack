package net.bakje.bhack.modules;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class Aura implements ClientModInitializer {

    private boolean Aura = false;
    private boolean AuraRotations = false;

    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("Aura", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        KeyBinding binding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding("AuraRotations", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set Aura to " + !Aura), false);
                Aura =!Aura;
            }

            while (binding2.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set AuraRotations to " + !AuraRotations), false);
                AuraRotations =!AuraRotations;
            }

            if (Aura & mc.player != null){
                for (Entity target: mc.world.getEntities()) {
                    if (target instanceof PlayerEntity) {
                        if (mc.player.distanceTo(target) > 0.1 & mc.player.distanceTo(target) < 5) {
                            if (AuraRotations) {
                                double dX = mc.player.getX() - target.getX();
                                double dY = mc.player.getY() - target.getY();
                                double dZ = mc.player.getZ() - target.getZ();

                                double DistanceXZ = Math.sqrt(dX * dX + dZ * dZ);
                                double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + dY * dY);

                                double newYaw = Math.acos(dX / DistanceXZ) * 180 / Math.PI;
                                double newPitch = Math.acos(dY / -DistanceY) * 180 / Math.PI - 90;

                                if (dZ < 0.0)
                                    newYaw = newYaw + Math.abs(180 - newYaw) * 2;
                                newYaw = (newYaw + 90);


                                //client side
                                //mc.player.setYaw((float) newYaw);
                                //mc.player.setPitch((float) newPitch);

                                //just serverside
                                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), (float) newYaw, (float) newPitch, mc.player.isOnGround()));
                            }
                            float cooldown = mc.player.getAttackCooldownProgress(mc.getTickDelta());
                            if (cooldown==1) {
                                mc.interactionManager.attackEntity(mc.player, target);
                            }
                        }
                    }
                }
            }
        });
    }
}