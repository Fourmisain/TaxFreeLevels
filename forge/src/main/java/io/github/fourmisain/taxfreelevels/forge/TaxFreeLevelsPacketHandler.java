package io.github.fourmisain.taxfreelevels.forge;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;

import static io.github.fourmisain.taxfreelevels.TaxFreeLevelsClient.handleReceivedServerConfig;

public class TaxFreeLevelsPacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		ServerConfigPayload.ID,
		() -> PROTOCOL_VERSION,
		PROTOCOL_VERSION::equals,
		PROTOCOL_VERSION::equals
	);

	public static void init() {
		INSTANCE.messageBuilder(TaxFreeLevelsConfig.class, 0, NetworkDirection.PLAY_TO_CLIENT)
			.encoder((config, buf) -> ServerConfigPayload.write(buf, config))
			.decoder(ServerConfigPayload::read)
			.consumer(((config, context) -> {
				context.get().enqueueWork(() -> {
					LogManager.getLogger("debug").warn("ASDHASKIHFDASKFHASF");
					handleReceivedServerConfig(config, MinecraftClient.getInstance());
				});
			}))
			.add();
	}
}
