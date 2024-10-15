package io.github.fourmisain.taxfreelevels;

import com.google.gson.JsonSyntaxException;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record ServerConfigPayload(TaxFreeLevelsConfig config) implements CustomPayload {
	public static final Id<ServerConfigPayload> ID = new Id<>(TaxFreeLevels.id("server_config"));

	public static final PacketCodec<PacketByteBuf, ServerConfigPayload> CODEC = PacketCodec.of(
		(value, buf) -> {
			String json = TaxFreeLevelsConfig.GSON.toJson(TaxFreeLevelsConfig.LOCAL_CONFIG.get());
			buf.writeString(json);
		},
		buf -> {
			String json = buf.readString(32767);
			try {
				TaxFreeLevelsConfig config = TaxFreeLevelsConfig.GSON.fromJson(json, TaxFreeLevelsConfig.class);
				return new ServerConfigPayload(config);
			} catch (JsonSyntaxException e) {
				TaxFreeLevels.LOGGER.error("couldn't parse received config! \"{}\"", json, e);
				return null;
			}
		});

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
