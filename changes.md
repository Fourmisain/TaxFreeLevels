## 1.4.25

- [Forge 1.20.1, NeoForge 1.21.1] compatibility with Quark (Oddities)'s Matrix Enchanting

## 1.4.24

- [1.21.1 - 1.21.11] compatibility with Fixed

## 1.5.4 / 1.4.23

- compatibility with Sophisticated Backpacks' Anvil Upgrade (purely visual "Too Expensive" is removed, everything else already worked)

## 1.5.3 / 1.4.22

- add Chinese (zh_cn) translation, thanks to qinglin-zcr!
- [Fabric 1.17 - 1.21.9] bump minimum Fabric Loader version to 0.16
- [Forge 1.18.2] compatiblity with Twilight Forest's Uncrafting Table

## 1.4.21

- [Fabric 1.20.2 - 1.20.4] fix startup crash due to reference to non-existent `Charm1_17Mixin`

## 1.5.2 / 1.4.20

- [1.20.1 - 26.1.2] compatiblity with Backpacked (unlock cost is flattened)
- [NeoForge 1.20.2 - 1.20.6] compatibility with DisenchantingForge

## 1.4.19

- [Forge 1.20.1] compatiblity with Alex's Mobs' Transmutation Table

## 1.5.1

- [26.1+] fix Enchanting Infuser compatibility, thanks to qtqgyt!

## 1.4.18

- [NeoForge 1.21.1] compatibility with DisenchantingForge

## 1.4.17

- [Forge 1.20.1] compatiblity with Twilight Forest's Uncrafting Table

## 1.5.0

- port to 26.1

## 1.4.16

- [1.21.11] fix experience not being recalculated and synchronized

## 1.4.15

- NeoForge: port to 1.21.11

## 1.4.14

- [1.18.2 - 1.20.1] compatibility with DisenchantingForge/Fabric

## 1.4.13

- [1.19.2 Fabric] fix wrong Spectrum compat being used

## 1.4.12

- NeoForge: port to 1.21.9

## 1.4.11

- update for NeoForge 21.5.73-beta (fix boot crash due to moved code)

## 1.4.10

- more robust `RemoveAnvilLimitMixin`, fixing a boot crash on NeoForge 21.5.1-beta and up

## 1.4.9

- port to 1.21.5

## 1.4.8

- [1.18 - 1.20.4] fix regression: renaming items didn't cost only 1 level anymore

## 1.4.7

- update Enchanting Infuser compatibility to work with all versions, potentially even future ones (also fixes 1.20.1 Fabric crash)

## 1.4.6

- [1.21.1 Fabric/NeoForge, 1.20.1 Fabric/Forge] add compatibility for Dis-Enchanting Table

## 1.4.5

- [1.21.1] update Enchanting Infuser compatibility, remove from Forge

## 1.4.4

- better compatibility with mods modifying enchantment costs. fixes an issue where you could gain experience through a Passive Skill Tree skill that was only supposed to refund the cost
- [NeoForge 1.20.2 - 1.20.6] add a newer MixinExtras version

## 1.4.3

- [Forge 1.18 - 1.20.1] fix forge client not being able to connect to forge server when the client doesn't have the mod installed (and disallow joining server when client has the mod but not the server)
- [Forge 1.18 - 1.20.1] add support for Tetra's Workbench

## 1.4.2

- [1.21.2+] fix crash when saving config

## 1.4.1

- fix Fabric build
- respect level requirement for Reroll

## 1.4.0

This is a large rework of the mod, please have a read!

- the "Too Expensive" anvil limit is now removed (by default, configurable)
- new cost calculation bases cost off of level 0 instead of level 30, except for the Enchanting Table, where the cost is based off of the level requirement instead
- new config with level base and anvil limit options
- the config is automatically synced from server to client, fixing visual issues on the client
- (the mod can still work server-only, but you have to live with visually wrong level costs and "Too Expensive")

Quick cost comparison:  
In 1.3 the cost is the amount of XP needed to get from level 30-X to 30, having no effect below player level 30 (besides small tax).  
In 1.4 the cost is the amount of XP needed to get from level 0 to X, with the exception of the Enchanting Table.  
In an Enchanting Table with e.g. level requirement 21, the cost is the amount of XP needed to get from level 21-X to 21.

To restore the behavior of 1.3, you can set the level base setting to 30.

Backports to older Minecraft versions might take a while...