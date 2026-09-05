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
 * WorldBridge 核心服务接口（由主 mod 实现并注册）。
 * <p>
 * API 模块只包含接口与数据类，所有实际逻辑由 WorldBridge 主 mod
 * 在启动时通过 {@link WorldBridgeAPI#registerService} 注册的实现提供。
 * 附属 mod 不应直接引用主 mod 内部实现类，只依赖本接口与 {@link WorldBridgeAPI} 门面。
 */
public interface IWorldBridgeService {

    // ========== 数据访问 ==========

    /** 按 pairId 获取维度数据 */
    DimensionData getDimensionData(ServerLevel level, String pairId);

    /** 获取全部维度数据 */
    Collection<DimensionData> getAllDimensions(ServerLevel level);

    /** 判断维度数据是否存在 */
    boolean hasDimension(ServerLevel level, String pairId);

    // ========== 维度操作 ==========

    /** 注册维度数据（触发 DimensionCreateEvent，可被事件取消） */
    void createDimension(ServerLevel level, DimensionData data);

    /** 删除维度数据（触发 DimensionRemoveEvent，可被事件取消） */
    void removeDimension(ServerLevel level, String pairId);

    /** 更新维度数据（触发 DimensionUpdateEvent） */
    void updateDimension(ServerLevel level, DimensionData data);

    /** 创建动态维度并放置锚点（维度已存在时直接返回现有维度） */
    ServerLevel createDynamicDimension(MinecraftServer server, String pairId, DimensionData data);

    /** 清理维度（移除 CREATED_PAIRS 与生成器记录） */
    void cleanupDimension(String pairId);

    /** 由 pairId 生成维度 ResourceKey */
    ResourceKey<Level> getDimensionKey(String pairId);

    /** 生成维度 LevelStem（会优先调用附属注册的 IDimensionFactory） */
    LevelStem createLevelStem(MinecraftServer server, DimensionData data);

    // ========== 传送 ==========

    /** 将玩家传送到指定 pairId 维度（触发 PlayerTeleportEvent，可被事件取消） */
    void teleportPlayerToDimension(ServerPlayer player, String pairId);

    /** 将任意实体通过锚点传送到目标维度（仅玩家触发 PlayerTeleportEvent） */
    void teleportEntityToDimension(Entity entity, String pairId);

    // ========== 工具 ==========

    /** 生成唯一 pairId */
    String generatePairId();

    /** 查找安全出生点 */
    BlockPos findSafeSpawnPos(ServerLevel level);
}
