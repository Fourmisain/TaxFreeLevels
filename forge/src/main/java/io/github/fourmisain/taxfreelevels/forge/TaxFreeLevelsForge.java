package io.github.fourmisain.taxfreelevels.forge;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.PacketDistributor;

@Mod(TaxFreeLevels.MOD_ID)
@Mod.EventBusSubscriber
public class TaxFreeLevelsForge {
	public TaxFreeLevelsForge() {
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
			() -> new ConfigScreenHandler.ConfigScreenFactory(
				(client, parent) -> AutoConfig.getConfigScreen(TaxFreeLevelsConfig.class, parent).get()));
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayerEntity player) {
			if (TaxFreeLevelsPacketHandler.INSTANCE.isRemotePresent(player.networkHandler.connection)) {
				TaxFreeLevelsPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), TaxFreeLevelsConfig.LOCAL_CONFIG.get());
			}
		}
	}

	@Mod.EventBusSubscriber(modid = TaxFreeLevels.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class CommonModEvents {
		@SubscribeEvent
		public static void commonSetup(FMLCommonSetupEvent event) {
			event.enqueueWork(() -> {
				TaxFreeLevelsConfig.init();
				TaxFreeLevelsPacketHandler.init();
			});
		}
	}

	@Mod.EventBusSubscriber(modid = TaxFreeLevels.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				// send changed config to connected clients in singleplayer, used for Essential or e4mc
				TaxFreeLevelsConfig.LOCAL_CONFIG.registerSaveListener((manager, config) -> {
					// PacketDistributor.ALL would send to vanilla clients as well
					var client = MinecraftClient.getInstance();
					if (client.isInSingleplayer()) {
						var server = client.getServer();
						if (server != null) {
							for (var player : server.getPlayerManager().getPlayerList()) {
								if (TaxFreeLevelsPacketHandler.INSTANCE.isRemotePresent(player.networkHandler.connection)) {
									TaxFreeLevelsPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), TaxFreeLevelsConfig.LOCAL_CONFIG.get());
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
