package com.mow.mod.worldbridge.dimension;

import net.minecraft.nbt.CompoundTag;

public class GenerationConfig {
    private int seaLevel = 63;
    private boolean cavesEnabled = true;
    private boolean oreVeinsEnabled = true;
    private boolean useLegacyRandom = false;
    private String surfaceBlock = "minecraft:grass_block";
    private String stoneBlock = "minecraft:stone";
    private String deepslateBlock = "minecraft:deepslate";
    private boolean bedrockRoof = false;
    private boolean bedrockFloor = true;

    public int getSeaLevel() { return seaLevel; }
    public void setSeaLevel(int seaLevel) { this.seaLevel = seaLevel; }

    public boolean isCavesEnabled() { return cavesEnabled; }
    public void setCavesEnabled(boolean cavesEnabled) { this.cavesEnabled = cavesEnabled; }

    public boolean isOreVeinsEnabled() { return oreVeinsEnabled; }
    public void setOreVeinsEnabled(boolean oreVeinsEnabled) { this.oreVeinsEnabled = oreVeinsEnabled; }

    public boolean isUseLegacyRandom() { return useLegacyRandom; }
    public void setUseLegacyRandom(boolean useLegacyRandom) { this.useLegacyRandom = useLegacyRandom; }

    public String getSurfaceBlock() { return surfaceBlock; }
    public void setSurfaceBlock(String surfaceBlock) { this.surfaceBlock = surfaceBlock; }

    public String getStoneBlock() { return stoneBlock; }
    public void setStoneBlock(String stoneBlock) { this.stoneBlock = stoneBlock; }

    public String getDeepslateBlock() { return deepslateBlock; }
    public void setDeepslateBlock(String deepslateBlock) { this.deepslateBlock = deepslateBlock; }

    public boolean isBedrockRoof() { return bedrockRoof; }
    public void setBedrockRoof(boolean bedrockRoof) { this.bedrockRoof = bedrockRoof; }

    public boolean isBedrockFloor() { return bedrockFloor; }
    public void setBedrockFloor(boolean bedrockFloor) { this.bedrockFloor = bedrockFloor; }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("SeaLevel", seaLevel);
        tag.putBoolean("CavesEnabled", cavesEnabled);
        tag.putBoolean("OreVeinsEnabled", oreVeinsEnabled);
        tag.putBoolean("UseLegacyRandom", useLegacyRandom);
        tag.putString("SurfaceBlock", surfaceBlock);
        tag.putString("StoneBlock", stoneBlock);
        tag.putString("DeepslateBlock", deepslateBlock);
        tag.putBoolean("BedrockRoof", bedrockRoof);
        tag.putBoolean("BedrockFloor", bedrockFloor);
        return tag;
    }

    public static GenerationConfig fromNbt(CompoundTag tag) {
        GenerationConfig config = new GenerationConfig();
        config.seaLevel = tag.getInt("SeaLevel");
        config.cavesEnabled = tag.getBoolean("CavesEnabled");
        config.oreVeinsEnabled = tag.getBoolean("OreVeinsEnabled");
        config.useLegacyRandom = tag.getBoolean("UseLegacyRandom");
        config.surfaceBlock = tag.contains("SurfaceBlock") ? tag.getString("SurfaceBlock") : config.surfaceBlock;
        config.stoneBlock = tag.contains("StoneBlock") ? tag.getString("StoneBlock") : config.stoneBlock;
        config.deepslateBlock = tag.contains("DeepslateBlock") ? tag.getString("DeepslateBlock") : config.deepslateBlock;
        config.bedrockRoof = tag.contains("BedrockRoof") ? tag.getBoolean("BedrockRoof") : config.bedrockRoof;
        config.bedrockFloor = tag.contains("BedrockFloor") ? tag.getBoolean("BedrockFloor") : config.bedrockFloor;
        return config;
    }
}