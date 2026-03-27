package io.github.fourmisain.taxfreelevels;

import net.minecraft.client.Minecraft;

public class TaxFreeLevelsClient {
	public static void handleReceivedServerConfig(TaxFreeLevelsConfig config, Minecraft client) {
		if (client.isLocalServer()) {
			// use local config in singleplayer
			TaxFreeLevelsConfig.SERVER_CONFIG = null;
		} else {
			TaxFreeLevelsConfig.SERVER_CONFIG = config;
		}
	}
}
