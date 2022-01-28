package io.github.fourmisain.taxfreelevels.forge;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(TaxFreeLevels.MOD_ID)
public class TaxFreeLevelsForge {
	public TaxFreeLevelsForge() {
		// Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
			() -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
	}
}
