package io.github.fourmisain.taxfreelevels.forge;

import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MixinConfig implements IMixinConfigPlugin {
	private final Set<String> disabledMixins = new HashSet<>();
	private String mixinPackage;

	public static boolean isModInstalled(String modId) {
		return isModInstalled(modId, null);
	}

	public static boolean isModInstalled(String modId, ArtifactVersion version) {
		// ModList seems to always be null when shouldApplyMixin is executed
		// While not ideal, we can check which mods are loading
		for (ModInfo mod : FMLLoader.getLoadingModList().getMods())
			if (mod.getModId().equals(modId) && (version == null || mod.getVersion().compareTo(version) >= 0))
				return true;

		return false;
	}

	@Override
	public void onLoad(String mixinPackage) {
		this.mixinPackage = mixinPackage;

		if (isModInstalled("enchanting_overhauled")) {
			disabledMixins.add("CheapAnvilRenameMixin");
			disabledMixins.add("FlattenAnvilCostMixin");
		}

		// note: Fabric Waystones has the same mod id
		if (!isModInstalled("waystones"))
			disabledMixins.add("WaystonesMixin");

		// Enchanting Infuser
		if (!isModInstalled("enchantinginfuser")) {
			disabledMixins.add("EnchantingInfuserMixin");
		}

		// Apotheosis 7.2.0 added it's own (conflicting) optimal cost implementation
		if (isModInstalled("apotheosis", new DefaultArtifactVersion("7.2.0"))) {
			disabledMixins.add("FlattenAnvilCostMixin");
		} else {
			disabledMixins.add("EnchantmentUtilsMixin");
		}
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		String mixinName = mixinClassName.substring(mixinPackage.length() + 1);
		return !disabledMixins.contains(mixinName);
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}
