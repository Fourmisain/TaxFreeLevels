package io.github.fourmisain.taxfreelevels.neoforge;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ServerConfigurationPacketListener;
import net.neoforged.neoforge.network.configuration.ICustomConfigurationTask;

import java.util.function.Consumer;

public record ServerConfigConfigurationTask(ServerConfigurationPacketListener listener) implements ICustomConfigurationTask {
	public static final Type TYPE = new Type(TaxFreeLevels.id("server_config"));

	@Override
	public void run(Consumer<CustomPacketPayload> sender) {
		sender.accept(new ServerConfigPayload(TaxFreeLevelsConfig.LOCAL_CONFIG.get()));
		listener.finishCurrentTask(type());
	}

	@Override
	public Type type() {
		return TYPE;
	}
}
