package io.github.fourmisain.taxfreelevels.forge;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerConfigurationTask;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

import static io.github.fourmisain.taxfreelevels.TaxFreeLevelsClient.handleReceivedServerConfig;

public class TaxFreeLevelsPacketHandler {
	public static final ServerPlayerConfigurationTask.Key KEY = new ServerPlayerConfigurationTask.Key(ServerConfigPayload.ID.toString());
	public static final SimpleChannel INSTANCE = ChannelBuilder
		.named(KEY.id())
		.optional()
		.simpleChannel()
			.messageBuilder(TaxFreeLevelsConfig.class)
			.encoder((config, buf) -> ServerConfigPayload.write(buf, config))
			.decoder(ServerConfigPayload::read)
			.consumerMainThread(((config, context) -> {
				context.setPacketHandled(true);
				handleReceivedServerConfig(config, MinecraftClient.getInstance());
			}))
			.add();

	public static void init() {
		// used for static initialization
	}
}
