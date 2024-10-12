package io.github.fourmisain.taxfreelevels.fabric;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

import static io.github.fourmisain.taxfreelevels.TaxFreeLevelsClient.handleReceivedServerConfig;

public class TaxFreeLevelsFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientConfigurationNetworking.registerGlobalReceiver(ServerConfigPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				handleReceivedServerConfig(payload.config(), context.client());
			});
		});
		ClientPlayNetworking.registerGlobalReceiver(ServerConfigPayload.ID, (payload, context) -> {
			// runs on Render thread (surprisingly)
			handleReceivedServerConfig(payload.config(), context.client());
		});

		// send changed config to connected clients in singleplayer, used for Essential or e4mc
		TaxFreeLevelsConfig.LOCAL_CONFIG.registerSaveListener((manager, config) -> {
			var client = MinecraftClient.getInstance();
			if (client.isInSingleplayer()) {
				var server = client.getServer();
				if (server != null) {
					for (var player : server.getPlayerManager().getPlayerList()) {
						if (ServerPlayNetworking.canSend(player, ServerConfigPayload.ID)) {
							ServerPlayNetworking.send(player, new ServerConfigPayload(TaxFreeLevelsConfig.LOCAL_CONFIG.get()));
						}
					}
				}
			}

			return ActionResult.PASS;
		});
	}
}
