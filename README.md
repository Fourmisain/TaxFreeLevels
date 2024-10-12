# Tax Free Levels

Reaching high levels in Minecraft is hard, because each level requires more XP than the last.  
Since Anvils and Enchantment Tables work off of levels, not off of collected XP, that means the higher your level, the higher the actual cost in terms of XP.

Tax Free Levels flattens the curve of the ever-increasing anvil and enchantment costs, meaning you'll always pay the same amount of XP, regardless of how high your level gets.  
It by default also removes the anvil limit ("Too Expensive!") and makes it so that renaming an item always only costs 1 level.

More precisely:  
Instead of paying X levels in an Anvil, you pay the amount of XP needed to get from level 0 to X.  
If your Enchanting Table has a level requirement of 30, instead of paying 3 levels, you pay the XP needed to get from level 27 to 30.

Note: For version 1.3 the XP cost is always calculated as the amount of XP needed to get from level 30-X to 30, having no effect on costs below player level 30. This behavior can be restored in 1.4 by setting the "Level Base" to 30. Version 1.3 also has no option to remove the anvil limit.

* for Minecraft 1.16 and above
* required on the server and client
* (it *does* work server-only, but there's visual issues like the "Too Expensive!" message popping up and the level cost not showing correctly when renaming items.)
* compatible with Draylar1's [Reroll](https://www.curseforge.com/minecraft/mc-mods/reroll)
* compatible with [Enchanting Overhauled](https://www.curseforge.com/minecraft/mc-mods/enchanting-overhauled)
* compatible with [Enchanting Infuser](https://modrinth.com/mod/enchanting-infuser)
* compatible with [Charm](https://modrinth.com/mod/charm) / Charm Reforged's Grindstone enchantment extraction
* compatible with [Spectrum](https://modrinth.com/mod/spectrum)'s Bedrock Anvil
* compatible with [Waystones](https://modrinth.com/mod/waystones) (older (Neo)Forge versions, newer ones use an XP cost by default. Fabric tip: [Wraith Waystones]([https://modrinth.com/mod/fwaystones](https://modrinth.com/mod/fwaystones)) can set fixed XP cost)
* compatible with [Apotheosis](https://www.curseforge.com/minecraft/mc-mods/apotheosis) / [Zenith]([https://www.curseforge.com/minecraft/mc-mods/zenith](https://www.curseforge.com/minecraft/mc-mods/zenith)) (7.2.0+ / 1.0.0+ cost calculation will be replaced, note that the shown XP cost isn't always accurate)
* **incompatible** with [Balanced Enchanting](https://www.curseforge.com/minecraft/mc-mods/balanced-enchanting) (the mod does effectively the same thing as this mod, minus all the compatibility stuff, choose one or the other)

## Comparison

Say you've just slain the Ender Dragon for the first time and reached level 68.  
In Vanilla, you can level 3 enchant a total of 13 items.  
With Tax Free Levels, this changes to 35 items, your Ender Dragon kill actually counts for something!

Say again, you reached level 68 and want to apply another enchantment on your pick which has a very high repair cost and it'd cost 35 levels.  
In Vanilla, you pay 10220 XP (the XP needed to get from level 33 to 68).  
With Tax Free Levels, you pay 2045 XP (the XP needed to get from level 0 to 35).  
You'd normally lose 8000 of your 12000 XP dragon reward here and there!

## No Small Tax

One small tidbit about vanilla Minecraft is that paying levels doesn't actually touch the percental experience progress.  
Since higher levels are worth more XP, this means you actually not only pay levels but also a small amount of XP.  
Tax Free Levels gets rid of this tax as well, even at lower player levels.


---

This is meant as a partial replacement for user11681's limitless, specifically the "level normalization". No code was taken, only the idea.

## For (Fabric) Mod Devs

The mod can be compiled against with [JitPack](https://jitpack.io/#Fourmisain/TaxFreeLevels/loom-SNAPSHOT) using

```
modImplementation 'com.github.Fourmisain:TaxFreeLevels:loom-SNAPSHOT'
``` 

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