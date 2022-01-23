package net.bakje.bhack.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;

@Mixin(value={ClientPlayerEntity.class})
public abstract class ClientPlayerEntityMixin {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(at={@At(value="HEAD")}, method={"sendChatMessage"}, cancellable=true)
    public void onChatMessage(String message, CallbackInfo ci) {
        message = message.toLowerCase();
        if (message.startsWith(".") &&
            !message.equals(".help") &&
            !message.equals(".toggle") &&
            !message.equals(".toggle fly") &&
            !message.equals(".d") &&
            !message.equals(".server")) {
            mc.player.sendMessage(new LiteralText("[bhack] Unkown or incomplete command, try .help for the list of commands."), false);
            ci.cancel();
        }

        if(message.equals(".help")) {
            mc.player.sendMessage(new LiteralText("[bhack] Commands : toggle <module>, server, d "), false);
            ci.cancel();
        }

        if(message.equals(".toggle")) {
            mc.player.sendMessage(new LiteralText("[bhack] Modules: fly"), false);
            ci.cancel();
        }

        if(message.equals(".toggle fly")) {
            mc.player.getAbilities().allowFlying = !mc.player.getAbilities().allowFlying;
            if (mc.player.getAbilities().allowFlying) {mc.player.sendMessage(new LiteralText("[bhack] Toggled fly on."), false);} else {mc.player.sendMessage(new LiteralText("[bhack] Toggled fly off."), false);}
            ci.cancel();
        }

        if (message.equals(".server")) {
            mc.player.sendMessage(new LiteralText("[bhack] " + mc.player.getServerBrand()), false);
            ci.cancel();
        }

        else if (message.equals(".d")) {
            mc.player.sendMessage(new LiteralText("[bhack] Trying to kick the player."), false);
            dotDBypsas();
            ci.cancel();
        }
    }
    public void dotDBypsas() { //shhhhhhh
        if(mc.player.getVehicle() == null) {
            return;
        }
        for (int i = 0; i < 38; i++) {
            mc.player.networkHandler.sendPacket(new EntitySetHeadYawS2CPacket(mc.player.getVehicle(), Byte.MAX_VALUE));
        }
    }
}

