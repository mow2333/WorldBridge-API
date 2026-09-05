package com.mow.mod.worldbridge.api;

import com.mow.mod.worldbridge.dimension.DimensionData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.Collection;

/**
 * WorldBridge 公开 API 门面。
 * 附属 mod 应仅通过本类与 DimensionFactoryRegistry / WorldBridgeEvents 交互，
 * 不要直接操作内部实现类。
 * <p>
 * 本门面采用服务定位模式：所有依赖主 mod 实现的调用都转发给
 * {@link #registerService(IWorldBridgeService)} 注册的服务实现；
 * 主 mod 启动时必须注册，否则维度操作相关方法会抛出 IllegalStateException。
 * 纯逻辑方法（如 isWorldBridgeDimension）不依赖服务，可直接使用。
 */
public final class WorldBridgeAPI {
    private WorldBridgeAPI() {}

    private static volatile IWorldBridgeService service;

    /**
     * 注册 WorldBridge 核心服务实现（由主 mod 在启动时调用一次）。
     * 重复注册会覆盖旧实例。
     */
    public static void registerService(IWorldBridgeService impl) {
        service = impl;
    }

    /** 当前是否已注册服务实现（主 mod 是否已加载） */
    public static boolean isServiceAvailable() {
        return service != null;
    }

    private static IWorldBridgeService svc() {
        if (service == null) {
            throw new IllegalStateException("WorldBridge service not registered - is WorldBridge mod loaded?");
        }
        return service;
    }

    // ========== 维度判断（纯逻辑，不依赖服务） ==========

    /** WorldBridge 维度命名空间前缀 */
    public static final String DIMENSION_NAMESPACE = "world_bridge";

    /** 判断维度是否为 WorldBridge 创建的维度（world_bridge: 命名空间） */
    public static boolean isWorldBridgeDimension(ResourceKey<Level> dimensionKey) {
        return dimensionKey != null && DIMENSION_NAMESPACE.equals(dimensionKey.location().getNamespace());
    }

    /** 判断维度是否为 WorldBridge 创建的维度 */
    public static boolean isWorldBridgeDimension(ServerLevel level) {
        return level != null && isWorldBridgeDimension(level.dimension());
    }

    // ========== 数据访问 ==========

    /** 按 pairId 获取维度数据 */
    public static DimensionData getDimensionData(ServerLevel level, String pairId) {
        return svc().getDimensionData(level, pairId);
    }

    /** 获取全部维度数据 */
    public static Collection<DimensionData> getAllDimensions(ServerLevel level) {
        return svc().getAllDimensions(level);
    }

    /** 判断维度数据是否存在 */
    public static boolean hasDimension(ServerLevel level, String pairId) {
        return svc().hasDimension(level, pairId);
    }

    // ========== 维度操作 ==========

    /** 注册维度数据（触发 DimensionCreateEvent，可被事件取消） */
    public static void createDimension(ServerLevel level, DimensionData data) {
        svc().createDimension(level, data);
    }

    /** 删除维度数据（触发 DimensionRemoveEvent，可被事件取消） */
    public static void removeDimension(ServerLevel level, String pairId) {
        svc().removeDimension(level, pairId);
    }

    /** 更新维度数据（触发 DimensionUpdateEvent） */
    public static void updateDimension(ServerLevel level, DimensionData data) {
        svc().updateDimension(level, data);
    }

    /** 创建动态维度并放置锚点（维度已存在时直接返回现有维度） */
    public static ServerLevel createDynamicDimension(MinecraftServer server, String pairId, DimensionData data) {
        return svc().createDynamicDimension(server, pairId, data);
    }

    /** 清理维度（移除 CREATED_PAIRS 与生成器记录） */
    public static void cleanupDimension(String pairId) {
        svc().cleanupDimension(pairId);
    }

    /** 由 pairId 生成维度 ResourceKey */
    public static ResourceKey<Level> getDimensionKey(String pairId) {
        return svc().getDimensionKey(pairId);
    }

    /** 生成维度 LevelStem（会优先调用附属注册的 IDimensionFactory） */
    public static LevelStem createLevelStem(MinecraftServer server, DimensionData data) {
        return svc().createLevelStem(server, data);
    }

    // ========== 传送 ==========

    /** 将玩家传送到指定 pairId 维度（触发 PlayerTeleportEvent，可被事件取消） */
    public static void teleportPlayerToDimension(ServerPlayer player, String pairId) {
        svc().teleportPlayerToDimension(player, pairId);
    }

    /** 将任意实体通过锚点传送到目标维度（仅玩家触发 PlayerTeleportEvent） */
    public static void teleportEntityToDimension(Entity entity, String pairId) {
        svc().teleportEntityToDimension(entity, pairId);
    }

    // ========== 工具 ==========

    /** 生成唯一 pairId */
    public static String generatePairId() {
        return svc().generatePairId();
    }

    /** 查找安全出生点 */
    public static BlockPos findSafeSpawnPos(ServerLevel level) {
        return svc().findSafeSpawnPos(level);
    }

    // ========== 工厂注册 ==========

    /** 注册自定义维度类型工厂（供附属 mod 扩展维度生成器） */
    public static void registerDimensionFactory(IDimensionFactory factory) {
        DimensionFactoryRegistry.register(factory);
    }

    /** 查找匹配给定维度数据的工厂（无则返回 null，走内置生成逻辑） */
    public static IDimensionFactory getDimensionFactory(DimensionData data) {
        return DimensionFactoryRegistry.getFactory(data);
    }
}
