package net.blay09.mods.waystones.core;

import net.blay09.mods.waystones.api.IWaystone;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;

public class PlayerWaystoneManager {
	public static boolean tryTeleportToWaystone(Entity entity, IWaystone waystone, WarpMode warpMode, @Nullable IWaystone fromWaystone) {
		return true;
	}
}
