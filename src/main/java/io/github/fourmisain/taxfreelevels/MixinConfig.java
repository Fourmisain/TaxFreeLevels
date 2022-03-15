package io.github.fourmisain.taxfreelevels;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MixinConfig implements IMixinConfigPlugin {
	private final Set<String> disabledMixins = new HashSet<>();

	@Override
	public void onLoad(String mixinPackage) {
		// check for custom options in other mods
		for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
			ModMetadata metadata = container.getMetadata();

			if (!metadata.containsCustomValue(TaxFreeLevels.CUSTOM_OPTIONS_FIELD))
				continue;

			for (Map.Entry<String, CustomValue> entry : metadata.getCustomValue(TaxFreeLevels.CUSTOM_OPTIONS_FIELD).getAsObject()) {
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
			disabledMixins.add("CharmMixin");
		}
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		String mixinName = mixinClassName.substring(mixinClassName.lastIndexOf(".mixin") + ".mixin".length() + 1);
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
