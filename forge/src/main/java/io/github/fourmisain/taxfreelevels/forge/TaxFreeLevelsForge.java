package io.github.fourmisain.taxfreelevels.forge;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(TaxFreeLevels.MOD_ID)
public class TaxFreeLevelsForge {
	public TaxFreeLevelsForge() {
		// Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST,
			() -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
	}
}
