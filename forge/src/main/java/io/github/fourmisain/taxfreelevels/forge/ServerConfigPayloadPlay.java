package io.github.fourmisain.taxfreelevels.forge;

import com.google.gson.JsonSyntaxException;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

// copy of ServerConfigPayload for play phase, to prevent "class already registered" (why Forge, why?)
public record ServerConfigPayloadPlay(TaxFreeLevelsConfig config) implements CustomPayload {
	public static final Id<ServerConfigPayloadPlay> ID = new Id<>(TaxFreeLevels.id("server_config"));

	public static final PacketCodec<RegistryByteBuf, ServerConfigPayloadPlay> CODEC = PacketCodec.of(
		(value, buf) -> {
			String json = TaxFreeLevelsConfig.GSON.toJson(TaxFreeLevelsConfig.LOCAL_CONFIG.get());
			buf.writeString(json);
		},
		buf -> {
			String json = buf.readString(32767);
			try {
				TaxFreeLevelsConfig config = TaxFreeLevelsConfig.GSON.fromJson(json, TaxFreeLevelsConfig.class);
				return new ServerConfigPayloadPlay(config);
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
