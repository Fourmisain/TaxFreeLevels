package io.github.fourmisain.taxfreelevels;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.jetbrains.annotations.Nullable;

@Config(name = TaxFreeLevels.MOD_ID)
public class TaxFreeLevelsConfig implements ConfigData {
	@ConfigEntry.Gui.Excluded
	public static final Gson GSON = new GsonBuilder().create();
	@ConfigEntry.Gui.Excluded
	public static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();

	@ConfigEntry.Gui.Excluded
	public static final ConfigHolder<TaxFreeLevelsConfig> LOCAL_CONFIG = AutoConfig.register(TaxFreeLevelsConfig.class, (definition, configClass) -> new GsonConfigSerializer<>(definition, configClass, GSON_PRETTY));
	@ConfigEntry.Gui.Excluded @Nullable
	public static TaxFreeLevelsConfig SERVER_CONFIG = null; // aka received server config

	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.BoundedDiscrete(min = 0, max = 100)
	public int levelBase = 0;
	@ConfigEntry.Gui.Tooltip
	public boolean removeAnvilLimit = true;

	public static TaxFreeLevelsConfig get() {
		return (SERVER_CONFIG != null ? SERVER_CONFIG : LOCAL_CONFIG.get());
	}

	public static void init() {
		// used for static initialization
	}
}
