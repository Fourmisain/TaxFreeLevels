package io.github.fourmisain.taxfreelevels.forge;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

@Mod(TaxFreeLevels.MOD_ID)
public class TaxFreeLevelsForge {
	private static final String IGNORESERVERONLY = "OHNOES\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31";

	public TaxFreeLevelsForge() {
		if (ModList.get().getModContainerById("forge").get().getModInfo().getVersion().compareTo(new DefaultArtifactVersion("41.0.15")) < 0) {
			// Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
			ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
				() -> new IExtensionPoint.DisplayTest(() -> IGNORESERVERONLY, (a, b) -> true));
		}
	}
}
