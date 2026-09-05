package com.mow.mod.worldbridge.globe;

import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 维度链路地球仪 — 数据模型（v0.3）。
 * <p>
 * 节点 = 维度，边 = 传送连接。全部字段可 FriendlyByteBuf 序列化。
 * 服务端由 {@link GlobeDataProvider} 收集，经 GlobeDataPacket(S2C) 发往客户端。
 */
public class GlobeData {

    /** 维度类型（决定节点颜色/形状）。 */
    public enum DimType {
        OVERWORLD, NETHER, END, CUSTOM, COMMAND, EXTERNAL;

        public static DimType byId(int i) { return values()[Math.max(0, Math.min(values().length - 1, i))]; }
    }

    /** 边类型（决定边颜色/样式）。 */
    public enum EdgeType {
        MANUAL, WORLD_ANCHOR, TRANSHOPPER, COMMAND;

        public static EdgeType byId(int i) { return values()[Math.max(0, Math.min(values().length - 1, i))]; }
    }

    /** 节点：一个维度。 */
    public static class Node {
        public final String id;          // 维度 ID（如 "minecraft:overworld" / "world_bridge:abc123"）
        public final String label;       // 显示名
        public final DimType type;
        public final boolean loaded;     // 是否已加载
        public final int playerCount;
        public double x, y, z;           // 力导向布局坐标（客户端求解器写入）

        public Node(String id, String label, DimType type, boolean loaded, int playerCount) {
            this.id = id;
            this.label = label;
            this.type = type;
            this.loaded = loaded;
            this.playerCount = playerCount;
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeUtf(id, 256);
            buf.writeUtf(label, 256);
            buf.writeVarInt(type.ordinal());
            buf.writeBoolean(loaded);
            buf.writeVarInt(playerCount);
            buf.writeFloat((float) x);
            buf.writeFloat((float) y);
            buf.writeFloat((float) z);
        }

        public static Node decode(FriendlyByteBuf buf) {
            Node n = new Node(buf.readUtf(256), buf.readUtf(256), DimType.byId(buf.readVarInt()),
                    buf.readBoolean(), buf.readVarInt());
            n.x = buf.readFloat();
            n.y = buf.readFloat();
            n.z = buf.readFloat();
            return n;
        }
    }

    /** 边：一条传送连接。 */
    public static class Edge {
        public final String source;
        public final String target;
        public final EdgeType type;

        public Edge(String source, String target, EdgeType type) {
            this.source = source;
            this.target = target;
            this.type = type;
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeUtf(source, 256);
            buf.writeUtf(target, 256);
            buf.writeVarInt(type.ordinal());
        }

        public static Edge decode(FriendlyByteBuf buf) {
            return new Edge(buf.readUtf(256), buf.readUtf(256), EdgeType.byId(buf.readVarInt()));
        }
    }

    public final List<Node> nodes;
    public final List<Edge> edges;
    public final long timestamp;

    public GlobeData(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.timestamp = System.currentTimeMillis();
    }

    private GlobeData(List<Node> nodes, List<Edge> edges, long timestamp) {
        this.nodes = nodes;
        this.edges = edges;
        this.timestamp = timestamp;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(nodes.size());
        for (Node n : nodes) n.encode(buf);
        buf.writeVarInt(edges.size());
        for (Edge e : edges) e.encode(buf);
        buf.writeLong(timestamp);
    }

    public static GlobeData decode(FriendlyByteBuf buf) {
        int nn = buf.readVarInt();
        List<Node> nodes = new ArrayList<>(nn);
        for (int i = 0; i < nn; i++) nodes.add(Node.decode(buf));
        int ne = buf.readVarInt();
        List<Edge> edges = new ArrayList<>(ne);
        for (int i = 0; i < ne; i++) edges.add(Edge.decode(buf));
        return new GlobeData(nodes, edges, buf.readLong());
    }

    /** 按 id 查节点。 */
    public Node nodeById(String id) {
        for (Node n : nodes) if (n.id.equals(id)) return n;
        return null;
    }
}
