package com.esmods.curseoftheabyssreborn;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder;

import java.util.List;
import java.util.ArrayList;

import com.mojang.datafixers.util.Pair;

import com.google.common.base.Suppliers;

@EventBusSubscriber
public class AbyssCavesBiomeInit {
	@SubscribeEvent
	public static void onServerAboutToStart(ServerAboutToStartEvent event) {
		MinecraftServer server = event.getServer();
		Registry<DimensionType> dimensionTypeRegistry = server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE);
		Registry<LevelStem> levelStemTypeRegistry = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
		Registry<Biome> biomeRegistry = server.registryAccess().registryOrThrow(Registries.BIOME);
		for (LevelStem levelStem : levelStemTypeRegistry.stream().toList()) {
			DimensionType dimensionType = levelStem.type().value();
			if (dimensionType == dimensionTypeRegistry.getOrThrow(BuiltinDimensionTypes.OVERWORLD)) {
				ChunkGenerator chunkGenerator = levelStem.generator();
				if (chunkGenerator.getBiomeSource() instanceof MultiNoiseBiomeSource noiseSource) {
					List<Pair<Climate.ParameterPoint, Holder<Biome>>> parameters = new ArrayList<>(noiseSource.parameters().values());
					addParameterPoint(parameters, new Pair<>(new Climate.ParameterPoint(Climate.Parameter.span(-1f, 1f), Climate.Parameter.span(-1f, 1f), Climate.Parameter.span(-1f, 1f), Climate.Parameter.span(-1f, 1f),
							Climate.Parameter.span(1f, 2f), Climate.Parameter.span(-1f, 1f), 0), biomeRegistry.getHolderOrThrow(ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("curseoftheabyss", "abyss")))));
					chunkGenerator.biomeSource = MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(parameters));
					chunkGenerator.featuresPerStep = Suppliers
							.memoize(() -> FeatureSorter.buildFeaturesPerStep(List.copyOf(chunkGenerator.biomeSource.possibleBiomes()), biome -> chunkGenerator.generationSettingsGetter.apply(biome).features(), true));
				}
				if (chunkGenerator instanceof NoiseBasedChunkGenerator noiseGenerator) {
					NoiseGeneratorSettings noiseGeneratorSettings = noiseGenerator.settings.value();
					SurfaceRules.RuleSource currentRuleSource = noiseGeneratorSettings.surfaceRule();
					if (currentRuleSource instanceof SurfaceRules.SequenceRuleSource sequenceRuleSource) {
						List<SurfaceRules.RuleSource> surfaceRules = new ArrayList<>(sequenceRuleSource.sequence());
						addSurfaceRule(surfaceRules, 1, abyssSurfaceRule(ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("curseoftheabyss", "abyss"))));
						NoiseGeneratorSettings moddedNoiseGeneratorSettings = new NoiseGeneratorSettings(noiseGeneratorSettings.noiseSettings(), noiseGeneratorSettings.defaultBlock(), noiseGeneratorSettings.defaultFluid(),
								noiseGeneratorSettings.noiseRouter(), SurfaceRules.sequence(surfaceRules.toArray(SurfaceRules.RuleSource[]::new)), noiseGeneratorSettings.spawnTarget(), noiseGeneratorSettings.seaLevel(),
								noiseGeneratorSettings.disableMobGeneration(), noiseGeneratorSettings.aquifersEnabled(), noiseGeneratorSettings.oreVeinsEnabled(), noiseGeneratorSettings.useLegacyRandomSource());
						noiseGenerator.settings = new Holder.Direct<>(moddedNoiseGeneratorSettings);
					}
				}
			}
		}
	}

	private static SurfaceRules.RuleSource abyssSurfaceRule(ResourceKey<Biome> biomeKey) {
		return SurfaceRules.ifTrue(
				SurfaceRules.isBiome(biomeKey),
				SurfaceRules.sequence(
						createLayer(-128, -64, Blocks.GRASS_BLOCK, Blocks.DEEPSLATE, Blocks.DEEPSLATE),
						createLayer(-256, -129, Blocks.MUD, Blocks.BLACKSTONE, Blocks.BLACKSTONE)
				)
		);
	}

	private static SurfaceRules.RuleSource createLayer(int minY, int maxY, Block surfaceBlock, Block groundBlock, Block wallBlock) {
		return SurfaceRules.ifTrue(
				// Y >= minY
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(minY), 0),
				SurfaceRules.ifTrue(
						// Y <= maxY (проверяем, что Y < maxY + 1)
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(maxY + 1), 0)),
						SurfaceRules.sequence(
								// Поверхность (пол)
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.waterBlockCheck(-1, 0),
														SurfaceRules.state(surfaceBlock.defaultBlockState())
												),
												SurfaceRules.state(groundBlock.defaultBlockState())
										)
								),
								// Стены (потолок)
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.CEILING),
										SurfaceRules.state(wallBlock.defaultBlockState())
								),
								// Основной слой
								SurfaceRules.state(groundBlock.defaultBlockState())
						)
				)
		);
	}
	private static void addParameterPoint(List<Pair<Climate.ParameterPoint, Holder<Biome>>> parameters, Pair<Climate.ParameterPoint, Holder<Biome>> point) {
		if (!parameters.contains(point))
			parameters.add(point);
	}

	private static void addSurfaceRule(List<SurfaceRules.RuleSource> surfaceRules, int index, SurfaceRules.RuleSource rule) {
		if (!surfaceRules.contains(rule)) {
			if (index >= surfaceRules.size()) {
				surfaceRules.add(rule);
			} else {
				surfaceRules.add(index, rule);
			}
		}
	}
}