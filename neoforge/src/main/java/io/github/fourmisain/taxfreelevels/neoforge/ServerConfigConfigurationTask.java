package io.github.fourmisain.taxfreelevels.neoforge;

import io.github.fourmisain.taxfreelevels.ServerConfigPayload;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.network.listener.ServerConfigurationPacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.neoforged.neoforge.network.configuration.ICustomConfigurationTask;

import java.util.function.Consumer;

public record ServerConfigConfigurationTask(ServerConfigurationPacketListener listener) implements ICustomConfigurationTask {
	public static final Key KEY = new Key(TaxFreeLevels.id("server_config"));

	@Override
	public void run(Consumer<CustomPayload> sender) {
		sender.accept(new ServerConfigPayload(TaxFreeLevelsConfig.LOCAL_CONFIG.get()));
		listener.onTaskFinished(getKey());
	}

	@Override
	public Key getKey() {
		return KEY;
	}
}
