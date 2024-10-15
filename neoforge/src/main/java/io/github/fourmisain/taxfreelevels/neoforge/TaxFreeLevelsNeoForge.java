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
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static io.github.fourmisain.taxfreelevels.TaxFreeLevelsClient.handleReceivedServerConfig;

@Mod(TaxFreeLevels.MOD_ID)
@EventBusSubscriber(bus = Bus.MOD)
public class TaxFreeLevelsNeoForge {
	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			TaxFreeLevelsConfig.init();
		});
	}

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1")
			.optional();

		registrar.configurationToClient(
			ServerConfigPayload.ID,
			ServerConfigPayload.CODEC,
			(payload, context) -> {
				handleReceivedServerConfig(payload.config(), MinecraftClient.getInstance());
			}
		);

		registrar.playToClient(
			ServerConfigPayload.ID,
			ServerConfigPayload.CODEC,
			(payload, context) -> {
				handleReceivedServerConfig(payload.config(), MinecraftClient.getInstance());
			}
		);
	}

	@SubscribeEvent
	public static void register(RegisterConfigurationTasksEvent event) {
		if (event.getListener().hasChannel(ServerConfigPayload.ID)) {
			event.register(new ServerConfigConfigurationTask(event.getListener()));
		}
	}

	@Mod(value = TaxFreeLevels.MOD_ID, dist = Dist.CLIENT)
	@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		public ClientModEvents() {
			ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () ->
				(container, parent) -> AutoConfig.getConfigScreen(TaxFreeLevelsConfig.class, parent).get());
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
								if (player.networkHandler.hasChannel(ServerConfigPayload.ID)) {
									PacketDistributor.sendToPlayer(player, new ServerConfigPayload(TaxFreeLevelsConfig.LOCAL_CONFIG.get()));
								}
							}
						}
					}

					return ActionResult.PASS;
				});
			});
		}
	}
}
