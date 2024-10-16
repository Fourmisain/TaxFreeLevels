package io.github.fourmisain.taxfreelevels.neoforge;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.client.MinecraftClient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.OnGameConfigurationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;

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
	public static void register(OnGameConfigurationEvent event) {
		if (event.getListener().isConnected(ServerConfigPayload.ID)) {
			event.register(new ServerConfigConfigurationTask(event.getListener()));
		}
	}

	@SubscribeEvent
	public static void register(RegisterPayloadHandlerEvent event) {
		event.registrar(TaxFreeLevels.MOD_ID)
			.optional()
			.common(
				ServerConfigPayload.ID,
				buf -> new ServerConfigPayload(ServerConfigPayload.read(buf)),
				builder -> builder.client((payload, context) -> {
					context.workHandler().execute(() -> {
						handleReceivedServerConfig(payload.config(), MinecraftClient.getInstance());
					});
				})
			);
	}
}
