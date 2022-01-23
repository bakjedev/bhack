package net.bakje.bhack.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
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
            mc.player.sendMessage(new LiteralText("[bhack] Commands : enable <module>, disable <module> "), false);
        }

        if(message.equals(".enable fly")) {
            mc.player.sendMessage(new LiteralText("[bhack] Usage : enable fly, disable fly"), false);
            mc.player.getAbilities().allowFlying = true;
            ci.cancel();
        }

        if (message.equals(".disable fly")) {
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

        if(mc.player.getVehicle() == null) {
            return;
        }

       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
       mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
    }

}

