package com.mow.mod.worldbridge.api;

import com.mow.mod.worldbridge.globe.GlobeData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.Nullable;

/**
 * 维度地球仪公开 API 门面（只读查询）。
 * 附属 mod 通过本类读取维度网络的节点/边快照。
 * 服务未注册时调用抛 IllegalStateException。
 */
public final class GlobeAPI {
    private GlobeAPI() {}

    private static volatile GlobeService service;

    /** 注册服务实现（由主 mod 启动时调用） */
    public static void registerService(GlobeService impl) {
        service = impl;
    }

    /** 服务是否已注册 */
    public static boolean isServiceAvailable() {
        return service != null;
    }

    private static GlobeService svc() {
        if (service == null) {
            throw new IllegalStateException("WorldBridge Globe service not registered - is WorldBridge mod loaded?");
        }
        return service;
    }

    /**
     * 采集维度网络快照。
     * @param server 服务端实例
     * @param viewer 视角玩家（用于布局个性化，可为 null 走默认布局）
     * @return 维度网络数据（节点 + 边），服务端数据不可用时返回 null
     */
    @Nullable
    public static GlobeData getNetwork(MinecraftServer server, @Nullable ServerPlayer viewer) {
        if (server == null) return null;
        return svc().collectNetwork(server, viewer);
    }
}
