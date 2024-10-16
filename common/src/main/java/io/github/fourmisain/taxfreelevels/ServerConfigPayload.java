package io.github.fourmisain.taxfreelevels;

import com.google.gson.JsonSyntaxException;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ServerConfigPayload(TaxFreeLevelsConfig config) implements CustomPayload {
	public static final Identifier ID = TaxFreeLevels.id("server_config");

	public static void write(PacketByteBuf buf, TaxFreeLevelsConfig config) {
		buf.writeString(TaxFreeLevelsConfig.GSON.toJson(config));
	}

	public static TaxFreeLevelsConfig read(PacketByteBuf buf) {
		String json = buf.readString(32767);
		try {
			return TaxFreeLevelsConfig.GSON.fromJson(json, TaxFreeLevelsConfig.class);
		} catch (JsonSyntaxException e) {
			TaxFreeLevels.LOGGER.error("couldn't parse received config! \"{}\"", json, e);
			return null;
		}
	}

	@Override
	public void write(PacketByteBuf buf) {
		write(buf, config);
	}

	@Override
	public Identifier id() {
		return ID;
	}
}
