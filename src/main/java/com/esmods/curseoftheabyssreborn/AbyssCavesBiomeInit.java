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
					if (currentRuleSource instanceof SurfaceRules.SequenceRuleSource(
                            List<SurfaceRules.RuleSource> sequence
                    )) {
						List<SurfaceRules.RuleSource> surfaceRules = new ArrayList<>(sequence);
						addSurfaceRule(surfaceRules, FirstLayer());
						addSurfaceRule(surfaceRules, SecondLayer());
						addSurfaceRule(surfaceRules, ThirdLayer());
						addSurfaceRule(surfaceRules, FourthLayer());
						addSurfaceRule(surfaceRules, FifthLayer());
						addSurfaceRule(surfaceRules, SixthLayer());
						addSurfaceRule(surfaceRules, SeventhLayer());



						NoiseGeneratorSettings moddedNoiseGeneratorSettings = new NoiseGeneratorSettings(noiseGeneratorSettings.noiseSettings(), noiseGeneratorSettings.defaultBlock(), noiseGeneratorSettings.defaultFluid(),
								noiseGeneratorSettings.noiseRouter(), SurfaceRules.sequence(surfaceRules.toArray(SurfaceRules.RuleSource[]::new)), noiseGeneratorSettings.spawnTarget(), noiseGeneratorSettings.seaLevel(),
								noiseGeneratorSettings.disableMobGeneration(), noiseGeneratorSettings.aquifersEnabled(), noiseGeneratorSettings.oreVeinsEnabled(), noiseGeneratorSettings.useLegacyRandomSource());
						noiseGenerator.settings = new Holder.Direct<>(moddedNoiseGeneratorSettings);
					}
				}
			}
		}
	}

	private static SurfaceRules.RuleSource FirstLayer() {
		return SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(-189), 0),
				SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-65 + 1), 0)),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.waterBlockCheck(-1, 0),
														SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
												)
										)
								)
						)
				)
		);
	}

	private static SurfaceRules.RuleSource SecondLayer() {
		return SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(-336), 0),
				SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-190 + 1), 0)),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.waterBlockCheck(-1, 0),
														SurfaceRules.state(Blocks.MUD.defaultBlockState())
												)
										)
								)
						)
				)
		);
	}

	private static SurfaceRules.RuleSource ThirdLayer() {
		return SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(-483), 0),
				SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-337 + 1), 0)),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.waterBlockCheck(-1, 0),
														SurfaceRules.state(Blocks.CALCITE.defaultBlockState())
												)
										)
								)
						)
				)
		);
	}

	private static SurfaceRules.RuleSource FourthLayer() {
		return SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(-641), 0),
				SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-484 + 1), 0)),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.waterBlockCheck(-1, 0),
														SurfaceRules.state(Blocks.GREEN_TERRACOTTA.defaultBlockState())
												)
										)
								)
						)
				)
		);
	}
	private static SurfaceRules.RuleSource FifthLayer() {
		return SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(-737), 0),
				SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-642 + 1), 0)),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.waterBlockCheck(-1, 0),
														SurfaceRules.state(Blocks.PACKED_ICE.defaultBlockState())
												)
										)
								)
						)
				)
		);
	}
	private static SurfaceRules.RuleSource SixthLayer() {
		return SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(-897), 0),
				SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-738 + 1), 0)),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.waterBlockCheck(-1, 0),
														SurfaceRules.state(Blocks.SANDSTONE.defaultBlockState())
												)
										)
								)
						)
				)
		);
	}
	private static SurfaceRules.RuleSource SeventhLayer() {
		return SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(-1024), 0),
				SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-898 + 1), 0)),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.waterBlockCheck(-1, 0),
														SurfaceRules.state(Blocks.ROOTED_DIRT.defaultBlockState())
												)
										)
								)
						)
				)
		);
	}
	private static void addParameterPoint(List<Pair<Climate.ParameterPoint, Holder<Biome>>> parameters, Pair<Climate.ParameterPoint, Holder<Biome>> point) {
		if (!parameters.contains(point))
			parameters.add(point);
	}

	private static void addSurfaceRule(List<SurfaceRules.RuleSource> surfaceRules, SurfaceRules.RuleSource rule) {
		if (!surfaceRules.contains(rule)) {
			if (1 >= surfaceRules.size()) {
				surfaceRules.add(rule);
			} else {
				surfaceRules.add(1, rule);
			}
		}
	}
}