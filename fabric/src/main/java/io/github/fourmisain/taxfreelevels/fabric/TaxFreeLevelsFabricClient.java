package io.github.fourmisain.taxfreelevels.fabric;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;

import static io.github.fourmisain.taxfreelevels.TaxFreeLevelsClient.handleReceivedServerConfig;

public class TaxFreeLevelsFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientConfigurationNetworking.registerGlobalReceiver(ServerConfigPayload.TYPE, (payload, context) -> {
			context.client().execute(() -> {
				handleReceivedServerConfig(payload.config(), context.client());
			});
		});
		ClientPlayNetworking.registerGlobalReceiver(ServerConfigPayload.TYPE, (payload, context) -> {
			// runs on Render thread (surprisingly)
			handleReceivedServerConfig(payload.config(), context.client());
		});

		// send changed config to connected clients in singleplayer, used for Essential or e4mc
		TaxFreeLevelsConfig.LOCAL_CONFIG.registerSaveListener((manager, config) -> {
			var client = Minecraft.getInstance();
			if (client.isLocalServer()) {
				var server = client.getSingleplayerServer();
				if (server != null) {
					for (var player : server.getPlayerList().getPlayers()) {
						if (ServerPlayNetworking.canSend(player, ServerConfigPayload.TYPE)) {
							ServerPlayNetworking.send(player, new ServerConfigPayload(TaxFreeLevelsConfig.LOCAL_CONFIG.get()));
						}
					}
				}
			}

			return InteractionResult.PASS;
		});
	}
}
