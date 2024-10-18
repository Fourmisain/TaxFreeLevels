package io.github.fourmisain.taxfreelevels;

import net.minecraft.client.MinecraftClient;

public class TaxFreeLevelsClient {
	public static void handleReceivedServerConfig(TaxFreeLevelsConfig config, MinecraftClient client) {
		if (client.isInSingleplayer()) {
			// use local config in singleplayer
			TaxFreeLevelsConfig.SERVER_CONFIG = null;
		} else {
			TaxFreeLevelsConfig.SERVER_CONFIG = config;
		}
	}
}
