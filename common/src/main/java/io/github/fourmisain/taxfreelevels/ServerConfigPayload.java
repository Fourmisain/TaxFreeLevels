package io.github.fourmisain.taxfreelevels;

import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ServerConfigPayload(TaxFreeLevelsConfig config) implements CustomPacketPayload {
	public static final Type<ServerConfigPayload> TYPE = new Type<>(TaxFreeLevels.id("server_config"));

	public static final StreamCodec<FriendlyByteBuf, ServerConfigPayload> CODEC = StreamCodec.ofMember(
		(value, buf) -> {
			String json = TaxFreeLevelsConfig.GSON.toJson(TaxFreeLevelsConfig.LOCAL_CONFIG.get());
			buf.writeUtf(json);
		},
		buf -> {
			String json = buf.readUtf(32767);
			try {
				TaxFreeLevelsConfig config = TaxFreeLevelsConfig.GSON.fromJson(json, TaxFreeLevelsConfig.class);
				return new ServerConfigPayload(config);
			} catch (JsonSyntaxException e) {
				TaxFreeLevels.LOGGER.error("couldn't parse received config! \"{}\"", json, e);
				return null;
			}
		});

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
