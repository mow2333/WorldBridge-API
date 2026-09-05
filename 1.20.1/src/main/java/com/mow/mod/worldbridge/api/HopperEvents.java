package com.mow.mod.worldbridge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

/**
 * 跨维漏斗（物流）系统生命周期事件（在服务器线程发布）。
 * 附属 mod 通过 {@code MinecraftForge.EVENT_BUS.register(...)} 监听。
 */
public final class HopperEvents {
    private HopperEvents() {}

    /**
     * 两个跨维漏斗配对建立后触发（不可取消）。
     * A 端为 INPUT（发送方），B 端为 OUTPUT（接收方）。
     */
    public static class HopperPairEvent extends Event {
        private final String pairId;
        private final BlockPos posA;
        private final ResourceKey<Level> dimA;
        private final BlockPos posB;
        private final ResourceKey<Level> dimB;

        public HopperPairEvent(String pairId,
                               BlockPos posA, ResourceKey<Level> dimA,
                               BlockPos posB, ResourceKey<Level> dimB) {
            this.pairId = pairId;
            this.posA = posA;
            this.dimA = dimA;
            this.posB = posB;
            this.dimB = dimB;
        }

        public String getPairId() { return pairId; }
        /** INPUT 端位置 */
        public BlockPos getInputPos() { return posA; }
        public ResourceKey<Level> getInputDimension() { return dimA; }
        /** OUTPUT 端位置 */
        public BlockPos getOutputPos() { return posB; }
        public ResourceKey<Level> getOutputDimension() { return dimB; }
    }

    /**
     * 跨维漏斗配对断开后触发（不可取消）。
     */
    public static class HopperUnpairEvent extends Event {
        private final String pairId;
        private final BlockPos pos;
        private final ResourceKey<Level> dim;

        public HopperUnpairEvent(String pairId, BlockPos pos, ResourceKey<Level> dim) {
            this.pairId = pairId;
            this.pos = pos;
            this.dim = dim;
        }

        public String getPairId() { return pairId; }
        public BlockPos getPos() { return pos; }
        public ResourceKey<Level> getDimension() { return dim; }
    }

    /**
     * 物品跨维传输成功后触发（不可取消）。
     * 携带物品信息与传输数量。
     */
    public static class HopperTransferEvent extends Event {
        private final ServerLevel sourceLevel;
        private final BlockPos sourcePos;
        private final ServerLevel targetLevel;
        private final BlockPos targetPos;
        private final ItemStack stack;
        private final int count;

        public HopperTransferEvent(ServerLevel sourceLevel, BlockPos sourcePos,
                                   ServerLevel targetLevel, BlockPos targetPos,
                                   ItemStack stack, int count) {
            this.sourceLevel = sourceLevel;
            this.sourcePos = sourcePos;
            this.targetLevel = targetLevel;
            this.targetPos = targetPos;
            this.stack = stack;
            this.count = count;
        }

        public ServerLevel getSourceLevel() { return sourceLevel; }
        public BlockPos getSourcePos() { return sourcePos; }
        public ServerLevel getTargetLevel() { return targetLevel; }
        public BlockPos getTargetPos() { return targetPos; }
        /** 传输的物品原型（count 可能大于实际单次传输量，实际量见 {@link #getCount()}） */
        public ItemStack getStack() { return stack; }
        /** 本次实际传输数量 */
        public int getCount() { return count; }
    }

    /** 发布事件到 Forge 总线，返回是否被取消 */
    public static boolean post(Event event) {
        return MinecraftForge.EVENT_BUS.post(event);
    }
}
