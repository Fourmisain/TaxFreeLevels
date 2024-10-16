package io.github.fourmisain.taxfreelevels.neoforge;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.neoforged.neoforge.network.PacketDistributor;

@Mod(value = TaxFreeLevels.MOD_ID)
@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class TaxFreeLevelsNeoForgeClient {
	public TaxFreeLevelsNeoForgeClient() {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ModLoadingContext.get().registerExtensionPoint(ConfigScreenFactory.class, () -> new ConfigScreenFactory(
				(client, parent) -> AutoConfig.getConfigScreen(TaxFreeLevelsConfig.class, parent).get()));
		}
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			// send changed config to connected clients in singleplayer, used for Essential or e4mc
			TaxFreeLevelsConfig.LOCAL_CONFIG.registerSaveListener((manager, config) -> {
				// PacketDistributor.sendToAllPlayers would send to vanilla clients as well
				var client = MinecraftClient.getInstance();
				if (client.isInSingleplayer()) {
					var server = client.getServer();
					if (server != null) {
						for (var player : server.getPlayerManager().getPlayerList()) {
							if (player.networkHandler.isConnected(ServerConfigPayload.ID)) {
								PacketDistributor.PLAYER.with(player).send(new ServerConfigPayload(TaxFreeLevelsConfig.LOCAL_CONFIG.get()));
							}
						}
					}
				}

				return ActionResult.PASS;
			});
		});
	}
}
