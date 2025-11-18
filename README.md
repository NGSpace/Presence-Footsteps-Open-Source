# Presence-Footsteps Lite
[![Build Status](https://github.com/amiralimollaei/Presence-Footsteps-Lite/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/MineLittlePony/Kirin/actions/workflows/gradle-build.yml)
[![Version](https://img.shields.io/github/v/release/amiralimollaei/Presence-Footsteps-Lite)](https://github.com/amiralimollaei/Presence-Footsteps-Lite/releases/latest)
![License](https://img.shields.io/github/license/amiralimollaei/Presence-Footsteps-Lite)
![](https://img.shields.io/badge/api-fabric-orange.svg)

_...An Overly Complicated Sound Mod Made Slightly Less Complicated..._

This is a Fork of Sollace's Fork of Huricaaan (Ha3)'s original mod that removes technical debt, this fork should be far easier to maintain, and faster to update to the latest versions of the game, while being more flexible.

Removed Dependencies:

- kirin Fabric GUI library: used for the configuration menu, replaced with YetAnotherConfigLib as kirin usually lags behind the latest minecraft versions.
- Mine Little Pony Fabric mod: compatibility for the Mine Little Pony mod that turns players and mobs into Ponies, the compatibility is dropped as it is not a widely used feature

> Removing these dependencies should not affect the functionality of the mod for most people, while having them forces support for newer minecraft versions to come with significant delay, sometimes by up to several weeks.

Removing these dependencies enables us to maintain this mod way easier and faster in the future, and potentially enable support for Forge and other mod loaders.

## Original README:

---

This is the continuation of Huricaaan (Ha3)'s original mod, maintained and updated to the latest version of Minecraft. All previous features have been restored and some new ones added, with new development promised for the future on the opensource github profile!

---

Expect to see a more rich and immersive Minecraft world, one where Presence Footsteps adds new and more dynamic sounds for every block the player walks on. Every block has its own material assigned to it, and can make their own, customized sounds to better match what they're made of.

---

- Wooden chests are creaky and old.
- Stone is dusty and rough.
- Piston heads sound like wood, whilst their bodies are stone,
- Cake sounds like you might destroy it if you step in the frosting!
- Tall grass and bristles make a dry, rustling sound as you step through them.
- Magma sounds like it's literally cooking your soles!

---

Not only that, but the latest version includes step sounds for certain non-block entities:

---

Shulkers make the same creaky box noises as Shulker boxes, boats sound like wood, and armour stands creak and produce a stone stepping sound as you walk on their stone slab base.

 ---

## Sound Packs

Presence Footsteps is **fully customisable** with resource packs. The mod by default includes a pack with its own sounds
and material mappings for the vanilla blocks, however if you don't like those sounds or want to use your own, this pack can be turned off
or used together with packs from the community.

Note that you need **at least one** sounds pack enabled for the mod to function.

![image](https://github.com/Sollace/Presence-Footsteps/assets/6429283/9c50aa59-0f59-4324-aab7-cb360acac2ad)

## Mod Support

Presence Footsteps will attempt to figure out the sounds of modded blocks by looking at the vanilla material/sound types they use.
If your mod is using very unique sounds though, you can add mappings to your mod to directly control what sounds presence footsteps uses for your block by
following the [guide on our wiki](https://github.com/Sollace/Presence-Footsteps/wiki/Information-for-Resourcepack-Creators)
