package com.mow.mod.worldbridge.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class DimensionData {
    public String pairId;
    public String name;
    public String worldType = "default";
    public String biome = "";
    public long seed = 0;
    public boolean seedSet = false;
    public String difficulty = "normal";
    public boolean structures = true;
    public String password = "";
    public List<AnchorPoint> anchorPoints = new ArrayList<>();
    public GenerationConfig generationConfig = new GenerationConfig();
    public PermissionSettings permissionSettings = new PermissionSettings();
    public GameRuleConfig gameRuleConfig = new GameRuleConfig();
    public String creatorUuid = "";
    public String customDimensionId = "";
    /** 创建时玩家所在维度（维度地球仪父子连接用；空 = 旧数据，回退主世界）。 */
    public String parentDim = "";
    public String dimType = "overworld";
    public int customSeaLevel = 63;
    public String surfaceBlock = "minecraft:grass_block";
    public String stoneBlock = "minecraft:stone";
    public String deepslateBlock = "minecraft:deepslate";
    public boolean bedrockRoof = false;
    public boolean bedrockFloor = true;
    public String skyColor = "#87CEEB";
    public String fogColor = "#C0D8FF";
    public String waterColor = "#3F76E4";
    public String waterFogColor = "#050533";
    public double ambientLight = 0.0;
    public int minY = -64;
    public int maxY = 320;
    public boolean respawn = true;
    public boolean hasBiomeMix = false;
    public String biomeMixData = "";

    public static class AnchorPoint {
        public ResourceKey<Level> dimension;
        public BlockPos pos;

        public AnchorPoint(ResourceKey<Level> dimension, BlockPos pos) {
            this.dimension = dimension;
            this.pos = pos;
        }

        public CompoundTag toNbt() {
            CompoundTag tag = new CompoundTag();
            tag.putString("Dimension", dimension.location().toString());
            tag.put("Pos", NbtUtils.writeBlockPos(pos));
            return tag;
        }

        public static AnchorPoint fromNbt(CompoundTag tag) {
            ResourceLocation dimLoc = ResourceLocation.parse(tag.getString("Dimension"));
            ResourceKey<Level> dim = ResourceKey.create(ResourceKey.createRegistryKey(
                    ResourceLocation.parse("minecraft:dimension")), dimLoc);
            BlockPos pos = NbtUtils.readBlockPos(tag.getCompound("Pos"));
            return new AnchorPoint(dim, pos);
        }
    }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("PairId", pairId);
        tag.putString("Name", name);
        tag.putString("WorldType", worldType);
        tag.putString("Biome", biome);
        tag.putLong("Seed", seed);
        tag.putBoolean("SeedSet", seedSet);
        tag.putString("Difficulty", difficulty);
        tag.putBoolean("Structures", structures);
        tag.putString("Password", password);
        ListTag list = new ListTag();
        for (AnchorPoint ap : anchorPoints) {
            list.add(ap.toNbt());
        }
        tag.put("AnchorPoints", list);
        tag.put("GenerationConfig", generationConfig.toNbt());
        tag.put("PermissionSettings", permissionSettings.toNbt());
        tag.put("GameRuleConfig", gameRuleConfig.toNbt());
        if (!creatorUuid.isEmpty()) tag.putString("CreatorUuid", creatorUuid);
        if (!customDimensionId.isEmpty()) tag.putString("CustomDimensionId", customDimensionId);
        if (!parentDim.isEmpty()) tag.putString("ParentDim", parentDim);
        tag.putString("DimType", dimType);
        tag.putInt("CustomSeaLevel", customSeaLevel);
        tag.putString("SurfaceBlock", surfaceBlock);
        tag.putString("StoneBlock", stoneBlock);
        tag.putString("DeepslateBlock", deepslateBlock);
        tag.putBoolean("BedrockRoof", bedrockRoof);
        tag.putBoolean("BedrockFloor", bedrockFloor);
        tag.putString("SkyColor", skyColor);
        tag.putString("FogColor", fogColor);
        tag.putString("WaterColor", waterColor);
        tag.putString("WaterFogColor", waterFogColor);
        tag.putDouble("AmbientLight", ambientLight);
        tag.putInt("MinY", minY);
        tag.putInt("MaxY", maxY);
        tag.putBoolean("Respawn", respawn);
        tag.putBoolean("HasBiomeMix", hasBiomeMix);
        if (!biomeMixData.isEmpty()) tag.putString("BiomeMixData", biomeMixData);
        return tag;
    }

    public static DimensionData fromNbt(CompoundTag tag) {
        DimensionData data = new DimensionData();
        data.pairId = tag.getString("PairId");
        data.name = tag.getString("Name");
        data.worldType = tag.getString("WorldType");
        data.biome = tag.getString("Biome");
        data.seed = tag.getLong("Seed");
        data.seedSet = tag.getBoolean("SeedSet");
        data.difficulty = tag.getString("Difficulty");
        data.structures = tag.getBoolean("Structures");
        data.password = tag.getString("Password");
        if (tag.contains("GenerationConfig")) data.generationConfig = GenerationConfig.fromNbt(tag.getCompound("GenerationConfig"));
        if (tag.contains("PermissionSettings")) data.permissionSettings = PermissionSettings.fromNbt(tag.getCompound("PermissionSettings"));
        if (tag.contains("GameRuleConfig")) data.gameRuleConfig = GameRuleConfig.fromNbt(tag.getCompound("GameRuleConfig"));
        if (tag.contains("CreatorUuid")) data.creatorUuid = tag.getString("CreatorUuid");
        if (tag.contains("CustomDimensionId")) data.customDimensionId = tag.getString("CustomDimensionId");
        if (tag.contains("ParentDim")) data.parentDim = tag.getString("ParentDim");
        if (tag.contains("DimType")) data.dimType = tag.getString("DimType");
        if (tag.contains("CustomSeaLevel")) data.customSeaLevel = tag.getInt("CustomSeaLevel");
        if (tag.contains("SurfaceBlock")) data.surfaceBlock = tag.getString("SurfaceBlock");
        if (tag.contains("StoneBlock")) data.stoneBlock = tag.getString("StoneBlock");
        if (tag.contains("DeepslateBlock")) data.deepslateBlock = tag.getString("DeepslateBlock");
        if (tag.contains("BedrockRoof")) data.bedrockRoof = tag.getBoolean("BedrockRoof");
        if (tag.contains("BedrockFloor")) data.bedrockFloor = tag.getBoolean("BedrockFloor");
        if (tag.contains("SkyColor")) data.skyColor = tag.getString("SkyColor");
        if (tag.contains("FogColor")) data.fogColor = tag.getString("FogColor");
        if (tag.contains("WaterColor")) data.waterColor = tag.getString("WaterColor");
        if (tag.contains("WaterFogColor")) data.waterFogColor = tag.getString("WaterFogColor");
        if (tag.contains("AmbientLight")) data.ambientLight = tag.getDouble("AmbientLight");
        if (tag.contains("MinY")) data.minY = tag.getInt("MinY");
        if (tag.contains("MaxY")) data.maxY = tag.getInt("MaxY");
        if (tag.contains("Respawn")) data.respawn = tag.getBoolean("Respawn");
        if (tag.contains("HasBiomeMix")) data.hasBiomeMix = tag.getBoolean("HasBiomeMix");
        if (tag.contains("BiomeMixData")) data.biomeMixData = tag.getString("BiomeMixData");
        ListTag list = tag.getList("AnchorPoints", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            data.anchorPoints.add(AnchorPoint.fromNbt(list.getCompound(i)));
        }
        return data;
    }
}
