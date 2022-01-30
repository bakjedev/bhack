package net.bakje.bhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.DistancePredicate;
import net.minecraft.text.LiteralText;
import net.bakje.bhack.util.TimerUtil;
import org.lwjgl.glfw.GLFW;

import java.sql.Time;

public class aura implements ClientModInitializer {

    private boolean aura;
    private boolean auraRotations;
    public TimerUtil timer = new TimerUtil();

    @Override
    public void onInitializeClient() {
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("aura", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        KeyBinding binding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding("auraRotations", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "bhack"));
        aura =!aura;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set aura to " + aura), false);
                aura =!aura;
            }

            while (binding2.wasPressed()) {
                client.player.sendMessage(new LiteralText("[bhack] Set auraRotations to " + aura), false);
                auraRotations =!auraRotations;
            }

            if (!aura & mc.player != null){
                for (Entity b: mc.world.getEntities()) {
                    if (b instanceof PlayerEntity) {
                        if (mc.player.distanceTo(b) > 0.1 & mc.player.distanceTo(b) < 5) {
                            if (auraRotations) {
                                double dX = mc.player.getX() - b.getX();
                                double dY = mc.player.getY() - b.getY();
                                double dZ = mc.player.getZ() - b.getZ();

                                double DistanceXZ = Math.sqrt(dX * dX + dZ * dZ);
                                double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + dY * dY);

                                double newYaw = Math.acos(dX / DistanceXZ) * 180 / Math.PI;
                                double newPitch = Math.acos(dY / -DistanceY) * 180 / Math.PI - 90;

                                if (dZ < 0.0)
                                    newYaw = newYaw + Math.abs(180 - newYaw) * 2;
                                newYaw = (newYaw + 90);

                                float headyaw = (float) newYaw;

                                mc.player.setYaw((float) newYaw);
                                mc.player.setPitch((float) newPitch);
                                mc.player.setHeadYaw(headyaw);
                            }

                            if (timer.passed(600)){
                                mc.interactionManager.attackEntity(mc.player, b);
                                timer.reset();
                            }
                        }
                    }
                }
            }
        });
    }
}