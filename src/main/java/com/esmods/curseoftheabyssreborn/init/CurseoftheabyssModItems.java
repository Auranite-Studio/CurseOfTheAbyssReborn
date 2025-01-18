
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.esmods.curseoftheabyssreborn.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import net.minecraft.world.item.Item;

import com.esmods.curseoftheabyssreborn.item.StarCompassItem;
import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

public class CurseoftheabyssModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(CurseoftheabyssMod.MODID);
	public static final DeferredItem<Item> STAR_COMPASS = REGISTRY.register("star_compass", StarCompassItem::new);
	// Start of user code block custom items
	// End of user code block custom items
}
