package io.github.fourmisain.taxfreelevels.forge;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerConfigurationTask;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

import static io.github.fourmisain.taxfreelevels.TaxFreeLevelsClient.handleReceivedServerConfig;

public class TaxFreeLevelsPacketHandler {
	public static final ServerPlayerConfigurationTask.Key KEY = new ServerPlayerConfigurationTask.Key(ServerConfigPayload.ID.id().toString());
	public static final SimpleChannel INSTANCE = ChannelBuilder
		.named(ServerConfigPayload.ID.id())
		.optional()
		.simpleChannel()
			.configuration()
				.clientbound()
					.add(ServerConfigPayload.class, ServerConfigPayload.CODEC, (payload, context) -> {
						context.setPacketHandled(true);
						context.enqueueWork(() -> {
							handleReceivedServerConfig(payload.config(), MinecraftClient.getInstance());
						});
					})
			.play()
				.clientbound()
					.add(ServerConfigPayloadPlay.class, ServerConfigPayloadPlay.CODEC, (ServerConfigPayloadPlay payload, CustomPayloadEvent.Context context) -> {
						context.setPacketHandled(true);
						context.enqueueWork(() -> {
							handleReceivedServerConfig(payload.config(), MinecraftClient.getInstance());
						});
					})
		.build();

	public static void init() {
		// used for static initialization
	}
}
