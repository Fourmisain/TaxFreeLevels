package io.github.fourmisain.taxfreelevels.fabric;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.PacketByteBuf;

public class TaxFreeLevelsFabric  implements ModInitializer {
	@Override
	public void onInitialize() {
		ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
			server.execute(() -> {
				if (ServerConfigurationNetworking.canSend(handler, ServerConfigPayload.ID)) {
					PacketByteBuf buf = PacketByteBufs.create();
					ServerConfigPayload.write(buf, TaxFreeLevelsConfig.LOCAL_CONFIG.get());
					ServerConfigurationNetworking.send(handler, ServerConfigPayload.ID, buf);
				}
			});
		});
	}
}
