package com.mow.mod.worldbridge.api;

import com.mow.mod.worldbridge.globe.GlobeData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

/**
 * 维度地球仪系统服务接口（由 WorldBridge 主 mod 实现并注册）。
 * 附属 mod 通过 {@link GlobeAPI} 门面读取维度网络数据。
 */
public interface GlobeService {

    /**
     * 采集维度网络快照（节点 + 边）。
     * 仅供服务端调用；客户端侧请通过 GlobeAPI 的客户端包通道获取。
     */
    GlobeData collectNetwork(MinecraftServer server, ServerPlayer viewer);
}
