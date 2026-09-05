package com.mow.mod.worldbridge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

/**
 * 红链系统服务接口（由 WorldBridge 主 mod 实现并注册）。
 * 附属 mod 不应直接实现本接口，只通过 {@link RedlinkAPI} 门面调用。
 */
public interface RedlinkService {

    /** 查询某位置红链方块当前信号强度（非红链方块返回 0） */
    int getSignalStrength(ServerLevel level, BlockPos pos);

    /** 查询某位置红链方块的配对 ID（未配对返回 -1） */
    int getPairId(ServerLevel level, BlockPos pos);

    /** 查询某位置红链方块的配对端位置（未配对返回 null） */
    @Nullable BlockPos getPairedPos(ServerLevel level, BlockPos pos);

    /** 查询某位置红链方块的配对端所在维度（未配对返回 null） */
    @Nullable ResourceKey<Level> getPairedDimension(ServerLevel level, BlockPos pos);

    /** 断开某位置红链方块的配对（未配对则无操作） */
    void disconnectPair(ServerLevel level, BlockPos pos);
}
