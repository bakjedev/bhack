package net.bakje.bhack.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientPlayerEntity.class})
public abstract class ClientPlayerEntityMixin {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(at={@At(value="HEAD")}, method={"sendChatMessage"}, cancellable=true)
    public void onChatMessage(String message, CallbackInfo ci) {

        if(message.equals(".help")) {
            mc.player.sendMessage(new LiteralText("[bhack] Commands : fly, "), false);
        }

        if(message.equals(".fly")) {
            mc.player.sendMessage(new LiteralText("[bhack] Usage : fly true, fly false"), false);
        }

        if (message.equals(".fly true")) {
            mc.player.getAbilities().allowFlying = true;
            mc.player.sendMessage(new LiteralText("[bhack] fly enabled"), false);
            ci.cancel();
        }

        else if (message.equals(".fly false")) {
            mc.player.getAbilities().allowFlying = false;
            mc.player.sendMessage(new LiteralText("[bhack] fly disabled"), false);
            ci.cancel();
        }

        else if (message.equals(".d") || message.equals(".dupe") || message.equals(".Dupe") || message.equals(".D")) {
            dotDBypsas();
            ci.cancel();
        }
    }
    public void dotDBypsas() {
        mc.getNetworkHandler().sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), (byte) 1000000000));
    }
}

