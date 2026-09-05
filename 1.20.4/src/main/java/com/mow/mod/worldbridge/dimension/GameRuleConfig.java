package com.mow.mod.worldbridge.dimension;

import net.minecraft.nbt.CompoundTag;

public class GameRuleConfig {
    private Boolean doDaylightCycle;
    private Boolean doWeatherCycle;
    private Boolean doMobSpawning;
    private Boolean doFireTick;
    private Boolean doMobGriefing;
    private Boolean doPatrolSpawning;
    private Boolean doTraderSpawning;
    private Boolean doWardenSpawning;
    private Boolean randomTickSpeed;

    public Boolean getDoDaylightCycle() { return doDaylightCycle; }
    public void setDoDaylightCycle(Boolean v) { doDaylightCycle = v; }

    public Boolean getDoWeatherCycle() { return doWeatherCycle; }
    public void setDoWeatherCycle(Boolean v) { doWeatherCycle = v; }

    public Boolean getDoMobSpawning() { return doMobSpawning; }
    public void setDoMobSpawning(Boolean v) { doMobSpawning = v; }

    public Boolean getDoFireTick() { return doFireTick; }
    public void setDoFireTick(Boolean v) { doFireTick = v; }

    public Boolean getDoMobGriefing() { return doMobGriefing; }
    public void setDoMobGriefing(Boolean v) { doMobGriefing = v; }

    public Boolean getDoPatrolSpawning() { return doPatrolSpawning; }
    public void setDoPatrolSpawning(Boolean v) { doPatrolSpawning = v; }

    public Boolean getDoTraderSpawning() { return doTraderSpawning; }
    public void setDoTraderSpawning(Boolean v) { doTraderSpawning = v; }

    public Boolean getDoWardenSpawning() { return doWardenSpawning; }
    public void setDoWardenSpawning(Boolean v) { doWardenSpawning = v; }

    public Boolean getRandomTickSpeed() { return randomTickSpeed; }
    public void setRandomTickSpeed(Boolean v) { randomTickSpeed = v; }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        if (doDaylightCycle != null) tag.putBoolean("DoDaylightCycle", doDaylightCycle);
        if (doWeatherCycle != null) tag.putBoolean("DoWeatherCycle", doWeatherCycle);
        if (doMobSpawning != null) tag.putBoolean("DoMobSpawning", doMobSpawning);
        if (doFireTick != null) tag.putBoolean("DoFireTick", doFireTick);
        if (doMobGriefing != null) tag.putBoolean("DoMobGriefing", doMobGriefing);
        if (doPatrolSpawning != null) tag.putBoolean("DoPatrolSpawning", doPatrolSpawning);
        if (doTraderSpawning != null) tag.putBoolean("DoTraderSpawning", doTraderSpawning);
        if (doWardenSpawning != null) tag.putBoolean("DoWardenSpawning", doWardenSpawning);
        if (randomTickSpeed != null) tag.putBoolean("RandomTickSpeed", randomTickSpeed);
        return tag;
    }

    public static GameRuleConfig fromNbt(CompoundTag tag) {
        GameRuleConfig config = new GameRuleConfig();
        if (tag.contains("DoDaylightCycle")) config.doDaylightCycle = tag.getBoolean("DoDaylightCycle");
        if (tag.contains("DoWeatherCycle")) config.doWeatherCycle = tag.getBoolean("DoWeatherCycle");
        if (tag.contains("DoMobSpawning")) config.doMobSpawning = tag.getBoolean("DoMobSpawning");
        if (tag.contains("DoFireTick")) config.doFireTick = tag.getBoolean("DoFireTick");
        if (tag.contains("DoMobGriefing")) config.doMobGriefing = tag.getBoolean("DoMobGriefing");
        if (tag.contains("DoPatrolSpawning")) config.doPatrolSpawning = tag.getBoolean("DoPatrolSpawning");
        if (tag.contains("DoTraderSpawning")) config.doTraderSpawning = tag.getBoolean("DoTraderSpawning");
        if (tag.contains("DoWardenSpawning")) config.doWardenSpawning = tag.getBoolean("DoWardenSpawning");
        if (tag.contains("RandomTickSpeed")) config.randomTickSpeed = tag.getBoolean("RandomTickSpeed");
        return config;
    }
}
