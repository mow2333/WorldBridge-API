package com.mow.mod.worldbridge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

/**
 * 跨维漏斗（物流）系统公开 API 门面。
 * 附属 mod 通过本类查询跨维漏斗状态；
 * 传输/配对/断连生命周期通过 {@link HopperEvents} 监听。
 * 服务未注册时调用抛 IllegalStateException。
 */
public final class HopperAPI {
    private HopperAPI() {}

    private static volatile HopperService service;

    /** 注册服务实现（由主 mod 启动时调用） */
    public static void registerService(HopperService impl) {
        service = impl;
    }

    /** 服务是否已注册 */
    public static boolean isServiceAvailable() {
        return service != null;
    }

    private static HopperService svc() {
        if (service == null) {
            throw new IllegalStateException("WorldBridge Hopper service not registered - is WorldBridge mod loaded?");
        }
        return service;
    }

    /** 查询某位置跨维漏斗是否已配对 */
    public static boolean isPaired(ServerLevel level, BlockPos pos) {
        return svc().isPaired(level, pos);
    }

    /** 查询某位置跨维漏斗的配对 ID（未配对返回 null） */
    @Nullable
    public static String getPairId(ServerLevel level, BlockPos pos) {
        return svc().getPairId(level, pos);
    }

    /** 查询某位置跨维漏斗的通道码（可能为 null） */
    @Nullable
    public static String getChannelCode(ServerLevel level, BlockPos pos) {
        return svc().getChannelCode(level, pos);
    }

    /** 查询某位置跨维漏斗当前模式："INPUT" / "OUTPUT"（非漏斗返回 null） */
    @Nullable
    public static String getMode(ServerLevel level, BlockPos pos) {
        return svc().getMode(level, pos);
    }

    /** 查询某位置跨维漏斗的配对端位置（未配对返回 null） */
    @Nullable
    public static BlockPos getPairedPos(ServerLevel level, BlockPos pos) {
        return svc().getPairedPos(level, pos);
    }

    /** 查询某位置跨维漏斗累计传输数量 */
    public static int getTransferCount(ServerLevel level, BlockPos pos) {
        return svc().getTransferCount(level, pos);
    }

    /** 查询某位置跨维漏斗的吞吐率（物品/分钟） */
    public static double getThroughputRate(ServerLevel level, BlockPos pos) {
        return svc().getThroughputRate(level, pos);
    }

    /** 查看某位置跨维漏斗缓冲区第 slot 格 */
    public static ItemStack getBufferSlot(ServerLevel level, BlockPos pos, int slot) {
        return svc().getBufferSlot(level, pos, slot);
    }
}
