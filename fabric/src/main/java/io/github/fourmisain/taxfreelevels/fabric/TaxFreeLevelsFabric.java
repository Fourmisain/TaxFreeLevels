package io.github.fourmisain.taxfreelevels.fabric;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;

public class TaxFreeLevelsFabric  implements ModInitializer {
	@Override
	public void onInitialize() {
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			// Runs on server thread

			if (ServerPlayNetworking.canSend(handler, ServerConfigPayload.ID)) {
				PacketByteBuf buf = PacketByteBufs.create();
				ServerConfigPayload.write(buf, TaxFreeLevelsConfig.LOCAL_CONFIG.get());
				sender.sendPacket(ServerConfigPayload.ID, buf);
			}
		});
	}
}
