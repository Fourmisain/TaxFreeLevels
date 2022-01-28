# Tax Free Levels

Reaching high levels in Minecraft is hard, because each level requires more XP than the last.  
Since Anvils and Enchantment Tables work off of levels, not off of collected XP, that means the higher your level, the higher the actual cost in terms of XP.

Tax Free Levels "flattens the curve" of the ever-increasing anvil and enchantment costs above level 30, meaning you'll always pay the same amount of XP, regardless of how high your level is.  

More precisely:  
If you are above level 30, instead of e.g. paying 3 levels, you pay the amount of XP needed to get from level 27 to 30.    
If the level cost is above 30, you pay the amount of XP needed to get from 0 to that level.
* for Minecraft Fabric 1.16-1.18 and Forge 1.17-1.18
* required on the server and client
* (it *does* work server-only, but the level cost won't show up correctly when renaming items)
* compatible with with Draylar1's [Reroll](https://www.curseforge.com/minecraft/mc-mods/reroll)

## Comparison

Say you've just slain the Ender Dragon for the first time and reached level 68.  
In Vanilla, you can level 3 enchant a total of 13 items.  
With Tax Free Levels, this changes to 35 items, your Ender Dragon kill actually counts for something!

Say again, you reached level 68 and want to apply another enchantment on your pick which has a very high repair cost and it'd cost 35 levels.  
In Vanilla, you pay 10220 XP (the XP needed to get from level 33 to 68).  
With Tax Free Levels, you pay 2045 XP (the XP needed to get from level 0 to 35).  
You'd normally lose 8000 of your 12000 XP dragon reward here and there!

---

This is meant as a partial replacement for user11681's limitless, specifically the "level normalization". No code was taken, only the idea.

## For (Fabric) Mod Devs

The mod can be compiled against with [JitPack](https://jitpack.io/#Fourmisain/TaxFreeLevels) (using regular `modImplementation`).  
The main `TaxFreeLevels` class contains some hopefully useful methods to help mod integration.  
If you want to disable certain mixins, you can do so by adding a custom field to your `fabric.mod.json` like so:

```
"custom": {
	"taxfreelevels:options": {
		"mixin.CheapAnvilRenameMixin": true,
		"mixin.FlattenAnvilCostMixin": true,
		"mixin.FlattenEnchantmentCostMixin": true,
		"mixin.RerollMixin": true
	}
}
```

Setting any value to `false` will disable that particular mixin (`true` values are ignored and can be left out).