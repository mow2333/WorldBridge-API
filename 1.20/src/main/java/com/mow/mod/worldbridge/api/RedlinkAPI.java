package com.mow.mod.worldbridge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

/**
 * 红链系统公开 API 门面。
 * 附属 mod 通过本类查询红链状态或触发断连；
 * 生命周期变化（配对/断连/信号）通过 {@link RedlinkEvents} 监听。
 * 所有依赖主 mod 实现的方法在服务未注册时抛出 IllegalStateException。
 */
public final class RedlinkAPI {
    private RedlinkAPI() {}

    private static volatile RedlinkService service;

    /** 注册服务实现（由主 mod 启动时调用） */
    public static void registerService(RedlinkService impl) {
        service = impl;
    }

    /** 服务是否已注册 */
    public static boolean isServiceAvailable() {
        return service != null;
    }

    private static RedlinkService svc() {
        if (service == null) {
            throw new IllegalStateException("WorldBridge Redlink service not registered - is WorldBridge mod loaded?");
        }
        return service;
    }

    /** 查询某位置红链方块当前信号强度（非红链方块返回 0） */
    public static int getSignalStrength(ServerLevel level, BlockPos pos) {
        return svc().getSignalStrength(level, pos);
    }

    /** 查询某位置红链方块的配对 ID（未配对返回 -1） */
    public static int getPairId(ServerLevel level, BlockPos pos) {
        return svc().getPairId(level, pos);
    }

    /** 查询某位置红链方块的配对端位置（未配对返回 null） */
    @Nullable
    public static BlockPos getPairedPos(ServerLevel level, BlockPos pos) {
        return svc().getPairedPos(level, pos);
    }

    /** 查询某位置红链方块的配对端所在维度（未配对返回 null） */
    @Nullable
    public static ResourceKey<Level> getPairedDimension(ServerLevel level, BlockPos pos) {
        return svc().getPairedDimension(level, pos);
    }

    /** 断开某位置红链方块的配对（未配对则无操作） */
    public static void disconnectPair(ServerLevel level, BlockPos pos) {
        svc().disconnectPair(level, pos);
    }
}
