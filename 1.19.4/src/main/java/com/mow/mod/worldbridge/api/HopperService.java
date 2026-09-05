package com.mow.mod.worldbridge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

/**
 * 跨维漏斗（物流）系统服务接口（由 WorldBridge 主 mod 实现并注册）。
 * 附属 mod 通过 {@link HopperAPI} 门面调用。
 */
public interface HopperService {

    /** 查询某位置跨维漏斗是否已配对 */
    boolean isPaired(ServerLevel level, BlockPos pos);

    /** 查询某位置跨维漏斗的配对 ID（未配对返回 null） */
    @Nullable String getPairId(ServerLevel level, BlockPos pos);

    /** 查询某位置跨维漏斗的通道码（可能为 null） */
    @Nullable String getChannelCode(ServerLevel level, BlockPos pos);

    /** 查询某位置跨维漏斗当前模式："INPUT" / "OUTPUT"（非漏斗返回 null） */
    @Nullable String getMode(ServerLevel level, BlockPos pos);

    /** 查询某位置跨维漏斗的配对端位置（未配对返回 null） */
    @Nullable BlockPos getPairedPos(ServerLevel level, BlockPos pos);

    /** 查询某位置跨维漏斗累计传输数量 */
    int getTransferCount(ServerLevel level, BlockPos pos);

    /** 查询某位置跨维漏斗的吞吐率（物品/分钟，最近 10 秒滑动窗口） */
    double getThroughputRate(ServerLevel level, BlockPos pos);

    /** 查看某位置跨维漏斗缓冲区第 slot 格（越界或非漏斗返回空栈） */
    ItemStack getBufferSlot(ServerLevel level, BlockPos pos, int slot);
}
