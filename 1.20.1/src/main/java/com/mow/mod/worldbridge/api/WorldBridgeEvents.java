package com.mow.mod.worldbridge.api;

import com.mow.mod.worldbridge.dimension.DimensionData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

/**
 * WorldBridge 生命周期事件。
 * 附属 mod 通过 {@code MinecraftForge.EVENT_BUS.register(...)} 监听：
 * <pre>
 * {@code @SubscribeEvent
 *  public void onCreate(DimensionCreateEvent event) { ... }}
 * </pre>
 * 注意：所有事件均在服务器线程（server thread）发布，请勿在监听器中执行耗时操作。
 */
public final class WorldBridgeEvents {
    private WorldBridgeEvents() {}

    /**
     * 维度数据创建前触发，可取消。
     * 取消后维度数据不会写入 DimensionDataManager。
     */
    public static class DimensionCreateEvent extends Event {
        private final MinecraftServer server;
        private final DimensionData data;

        public DimensionCreateEvent(MinecraftServer server, DimensionData data) {
            this.server = server;
            this.data = data;
        }

        public MinecraftServer getServer() { return server; }
        public DimensionData getData() { return data; }
        public String getPairId() { return data != null ? data.pairId : null; }

        @Override
        public boolean isCancelable() { return true; }
    }

    /**
     * 维度数据删除前触发，可取消。
     * 取消后维度数据不会被移除。
     */
    public static class DimensionRemoveEvent extends Event {
        private final MinecraftServer server;
        private final String pairId;
        private final DimensionData data;

        public DimensionRemoveEvent(MinecraftServer server, String pairId, DimensionData data) {
            this.server = server;
            this.pairId = pairId;
            this.data = data;
        }

        public MinecraftServer getServer() { return server; }
        public String getPairId() { return pairId; }
        public DimensionData getData() { return data; }

        @Override
        public boolean isCancelable() { return true; }
    }

    /**
     * 维度数据更新完成后触发（不可取消）。
     */
    public static class DimensionUpdateEvent extends Event {
        private final MinecraftServer server;
        private final DimensionData data;

        public DimensionUpdateEvent(MinecraftServer server, DimensionData data) {
            this.server = server;
            this.data = data;
        }

        public MinecraftServer getServer() { return server; }
        public DimensionData getData() { return data; }
        public String getPairId() { return data != null ? data.pairId : null; }
    }

    /**
     * 玩家传送到 WorldBridge 维度前触发，可取消。
     * 取消后传送被中止，玩家收到提示消息。
     */
    public static class PlayerTeleportEvent extends Event {
        private final ServerPlayer player;
        private final String pairId;
        private final ResourceKey<Level> targetDimension;

        public PlayerTeleportEvent(ServerPlayer player, String pairId, ResourceKey<Level> targetDimension) {
            this.player = player;
            this.pairId = pairId;
            this.targetDimension = targetDimension;
        }

        public ServerPlayer getPlayer() { return player; }
        public String getPairId() { return pairId; }
        public ResourceKey<Level> getTargetDimension() { return targetDimension; }

        @Override
        public boolean isCancelable() { return true; }
    }

    /** 发布事件到 Forge 总线，返回是否被取消 */
    public static boolean post(Event event) {
        return MinecraftForge.EVENT_BUS.post(event);
    }
}
