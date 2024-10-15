package io.github.fourmisain.taxfreelevels.fabric;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;

public class TaxFreeLevelsFabric  implements ModInitializer {
	@Override
	public void onInitialize() {
		PayloadTypeRegistry.configurationS2C().register(ServerConfigPayload.ID, ServerConfigPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(ServerConfigPayload.ID, ServerConfigPayload.CODEC);

		ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
			server.execute(() -> {
				if (ServerConfigurationNetworking.canSend(handler, ServerConfigPayload.ID)) {
					ServerConfigurationNetworking.send(handler, new ServerConfigPayload(TaxFreeLevelsConfig.LOCAL_CONFIG.get()));
				}
			});
		});
	}
}
