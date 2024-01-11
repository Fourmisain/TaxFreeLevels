package io.github.fourmisain.taxfreelevels;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MixinConfig implements IMixinConfigPlugin {
	private final Set<String> disabledMixins = new HashSet<>();
	private String mixinPackage;

	public static boolean testVersion(String modId, String versionRange) {
		try {
			Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(modId);
			if (!container.isPresent())
				return false;

			VersionPredicate pred = VersionPredicate.parse(versionRange);
			Version version = container.get().getMetadata().getVersion();

			return pred.test(version);
		} catch (VersionParsingException e) {
			TaxFreeLevels.LOGGER.error("version matching failed!", e);
			return false;
		}
	}

	@Override
	public void onLoad(String mixinPackage) {
		this.mixinPackage = mixinPackage;

		// check for custom options in other mods
		for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
			ModMetadata metadata = container.getMetadata();

			if (!metadata.containsCustomValue(TaxFreeLevels.CUSTOM_OPTIONS_FIELD))
				continue;

			for (var entry : metadata.getCustomValue(TaxFreeLevels.CUSTOM_OPTIONS_FIELD).getAsObject()) {
				String key = entry.getKey();
				CustomValue value = entry.getValue();

				// parse mixin options
				if (key.startsWith("mixin.")) {
					String mixinName = key.substring("mixin.".length());

					// disable mixins - trying to enable them will do nothing
					if (!value.getAsBoolean()) {
						TaxFreeLevels.LOGGER.debug("{} disabled {}", metadata.getId(), mixinName);
						disabledMixins.add(mixinName);
					}
				}
			}
		}

		// don't target Reroll if it doesn't exist
		if (!FabricLoader.getInstance().isModLoaded("reroll")) {
			disabledMixins.add("RerollMixin");
		}

		// don't target Charm if it doesn't exist
		if (!FabricLoader.getInstance().isModLoaded("charm")) {
			disabledMixins.add("Charm1_17Mixin");
			disabledMixins.add("CharmMixin");
		} else {
			disabledMixins.add(testVersion("minecraft", ">=1.18") ? "Charm1_17Mixin" : "CharmMixin");
		}

		// don't target Spectrum if it doesn't exist
		if (!FabricLoader.getInstance().isModLoaded("spectrum")) {
			disabledMixins.add("SpectrumMixin");
		}

		// Enchanting Infuser
		if (!FabricLoader.getInstance().isModLoaded("enchantinginfuser")) {
			disabledMixins.add("EnchantingInfuserMixin");
		}

		// Zenith (Apotheosis port) for 1.20 has it's own (conflicting) optimal cost implementation
		if (FabricLoader.getInstance().isModLoaded("zenith") && testVersion("fakerlib", ">=0.1.0")) {
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
