package io.github.fourmisain.taxfreelevels.fabric;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.ActionResult;

import static io.github.fourmisain.taxfreelevels.TaxFreeLevelsClient.handleReceivedServerConfig;

public class TaxFreeLevelsFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientConfigurationNetworking.registerGlobalReceiver(ServerConfigPayload.ID, (client, handler, buf, responseSender) -> {
			TaxFreeLevelsConfig serverConfig = ServerConfigPayload.read(buf);
			MinecraftClient.getInstance().execute(() -> {
				handleReceivedServerConfig(serverConfig, client);
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(ServerConfigPayload.ID, (client, handler, buf, responseSender) -> {
			TaxFreeLevelsConfig serverConfig = ServerConfigPayload.read(buf);
			client.execute(() -> {
				handleReceivedServerConfig(serverConfig, client);
			});
		});

		// send changed config to connected clients in singleplayer, used for Essential or e4mc
		TaxFreeLevelsConfig.LOCAL_CONFIG.registerSaveListener((manager, config) -> {
			var client = MinecraftClient.getInstance();
			if (client.isInSingleplayer()) {
				var server = client.getServer();
				if (server != null) {
					for (var player : server.getPlayerManager().getPlayerList()) {
						if (ServerPlayNetworking.canSend(player, ServerConfigPayload.ID)) {
							PacketByteBuf buf = PacketByteBufs.create();
							ServerConfigPayload.write(buf, TaxFreeLevelsConfig.LOCAL_CONFIG.get());
							ServerPlayNetworking.send(player, ServerConfigPayload.ID, buf);
						}
					}
				}
			}

			return ActionResult.PASS;
		});
	}
}
