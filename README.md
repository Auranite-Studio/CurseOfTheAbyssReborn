# Curse of the Abyss

> To those who offer up their bodies and challenge the chasm for which even darkness is no match, the Abyss is said to provide all. Life and death, Curses and Blessings - all of it. At the end of their journey, what fate will they choose to meet? <div align="right"> &mdash; *Unknown* </div>

## Integration

The sole purpose of this mod is to introduce the **Curse of the Abyss** from *Made in Abyss* into Minecraft, a phenomenon that seems reminiscent of decompression sickness. Making the world's depths or the experience itself more Abyss-like (e.g. adding cave biomes, changing cave generation, adding mobs, scaling difficulty with depth, enhancing atmosphere) should be handled by other mods. This section mentions some recommended mods and how they can be configured to achieve this, to provide an example.

### World Depth

Let's address the modification of the world's depth, as scale is arguably an essential striking feature of the Abyss. To do this, we must provide some sort of datapack. If we are not using a data-based terrain generation mod like Terralith, we must base our datapack off of vanilla Minecraft. Otherwise, we can just modify specific data files inside the Terralith jar file. Let's start with the vanilla case; the specific fields in the files that need to be modified to change the world depth are the following:

```
data/minecraft/dimension_type/overworld.json -> min_y, height
data/minecraft/worldgen/noise_settings/overworld.json -> min_y, height
```

The field `min_y` denotes the lowest point in the world; this should be a value between -64 (default for Minecraft's overworld) and -2016 (maximum supported by this mod) that is a multiple of 16. The field `height = 320 - min_y` should be calculated as shown, if you'd like to keep the default vanilla overworld height limit of 320. Keep in mind that the deeper/higher the world is, the more memory is required and the longer it'll take to generate/load/save chunks. What about for data-based mods like Terralith? This will depend on the mod and you're going to have to do your own testing. Open the mod jar as a zip file, look into every data file, change the `min_y` and `height` fields that affect the overworld dimension. For example, for Terralith, you modify the following:

```
data/minecraft/dimension_type/overworld.json -> min_y, height
data/minecraft/worldgen/noise_settings/copy.json -> min_y, height
data/minecraft/worldgen/noise_settings/no.json -> min_y, height
data/minecraft/worldgen/noise_settings/overworld.json -> min_y, height
data/minecraft/worldgen/overworld.json -> min_y, height
resources/terralith_default/data/minecraft/worldgen/noise_settings/overworld.json -> min_y, height
```

Finally, after setting your desired depth, **remember to configure the curseoftheabyss-common.toml config file** to ensure the Abyss span corresponds to the absolute value of the `min_y` you set.