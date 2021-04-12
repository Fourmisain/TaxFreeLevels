# Tax Free Levels

A Fabric Minecraft mod which flattens the ever-increasing anvil and enchantment costs and makes renaming items always cost 1 level.

Reaching high levels in Minecraft is hard, because each level requires more XP than the last.  
Since Anvils and Enchantment Tables work off of levels, not off of collected XP, that means the higher your level, the higher the actual cost in terms of XP.

Tax Free Levels "flattens" the ever-increasing anvil costs and enchantment costs (above level 30), meaning you'll always pay the same amount, regardless of how high your level is.  
It also makes it so that renaming an item always only costs 1 ("flattened") level.

More precisely:  
For Anvils, instead of paying the levels directly, you pay the amount of XP needed to get from level 0 to that level.  
For Enchantment Tables, if you are above level 30, instead of e.g. paying 3 levels, you pay the amount of XP needed to get from level 27 to 30.

* requires Minecraft 1.16.2+, Fabric Loader 0.11.1+
* required on the server and client
* (it *does* work server-only, but the level cost won't show up correctly when renaming items)
* compatible with with Draylar1's Reroll

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