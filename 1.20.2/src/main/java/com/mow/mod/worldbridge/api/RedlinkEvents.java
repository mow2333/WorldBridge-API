package com.mow.mod.worldbridge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

/**
 * 红链系统生命周期事件（在服务器线程发布）。
 * 附属 mod 通过 {@code MinecraftForge.EVENT_BUS.register(...)} 监听：
 * <pre>
 * {@code @SubscribeEvent
 *  public void onPair(RedlinkPairEvent event) { ... }}
 * </pre>
 */
public final class RedlinkEvents {
    private RedlinkEvents() {}

    /**
     * 两个红链方块配对建立后触发（不可取消）。
     */
    public static class RedlinkPairEvent extends Event {
        private final int pairId;
        private final BlockPos posA;
        private final ResourceKey<Level> dimA;
        private final BlockPos posB;
        private final ResourceKey<Level> dimB;

        public RedlinkPairEvent(int pairId,
                                BlockPos posA, ResourceKey<Level> dimA,
                                BlockPos posB, ResourceKey<Level> dimB) {
            this.pairId = pairId;
            this.posA = posA;
            this.dimA = dimA;
            this.posB = posB;
            this.dimB = dimB;
        }

        /** 共享配对 ID */
        public int getPairId() { return pairId; }
        /** 第一端位置 */
        public BlockPos getPosA() { return posA; }
        public ResourceKey<Level> getDimensionA() { return dimA; }
        /** 第二端位置 */
        public BlockPos getPosB() { return posB; }
        public ResourceKey<Level> getDimensionB() { return dimB; }
    }

    /**
     * 红链配对断开后触发（不可取消）。
     */
    public static class RedlinkUnpairEvent extends Event {
        private final int pairId;
        private final BlockPos posA;
        private final ResourceKey<Level> dimA;
        private final BlockPos posB;
        private final ResourceKey<Level> dimB;

        public RedlinkUnpairEvent(int pairId,
                                  BlockPos posA, ResourceKey<Level> dimA,
                                  BlockPos posB, ResourceKey<Level> dimB) {
            this.pairId = pairId;
            this.posA = posA;
            this.dimA = dimA;
            this.posB = posB;
            this.dimB = dimB;
        }

        public int getPairId() { return pairId; }
        public BlockPos getPosA() { return posA; }
        public ResourceKey<Level> getDimensionA() { return dimA; }
        public BlockPos getPosB() { return posB; }
        public ResourceKey<Level> getDimensionB() { return dimB; }
    }

    /**
     * 某个红链方块的信号强度变化后触发（不可取消）。
     */
    public static class RedlinkSignalEvent extends Event {
        private final ServerLevel level;
        private final BlockPos pos;
        private final int oldStrength;
        private final int newStrength;

        public RedlinkSignalEvent(ServerLevel level, BlockPos pos, int oldStrength, int newStrength) {
            this.level = level;
            this.pos = pos;
            this.oldStrength = oldStrength;
            this.newStrength = newStrength;
        }

        public ServerLevel getLevel() { return level; }
        public BlockPos getPos() { return pos; }
        public int getOldStrength() { return oldStrength; }
        public int getNewStrength() { return newStrength; }
    }

    /** 发布事件到 Forge 总线，返回是否被取消 */
    public static boolean post(Event event) {
        return MinecraftForge.EVENT_BUS.post(event);
    }
}
