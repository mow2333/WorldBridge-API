package com.mow.mod.worldbridge.api;

import com.mow.mod.worldbridge.dimension.DimensionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 维度工厂注册表。
 * 附属 mod 在 commonSetup 阶段调用 {@link #register} 注册自定义维度类型工厂；
 * ModDimensions 创建维度时会按注册顺序查找第一个 matches 的工厂并优先使用。
 */
public final class DimensionFactoryRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger("world_bridge_api");
    private static final List<IDimensionFactory> FACTORIES = new CopyOnWriteArrayList<>();

    private DimensionFactoryRegistry() {}

    /** 注册自定义维度工厂（重复注册相同 ID 会被忽略） */
    public static void register(IDimensionFactory factory) {
        if (factory == null || factory.getId() == null) return;
        for (IDimensionFactory existing : FACTORIES) {
            if (existing.getId().equals(factory.getId())) {
                LOGGER.warn("DimensionFactory {} already registered, ignoring", factory.getId());
                return;
            }
        }
        FACTORIES.add(factory);
        LOGGER.info("Registered dimension factory: {}", factory.getId());
    }

    /** 查找第一个匹配给定维度数据的工厂（无则返回 null） */
    public static IDimensionFactory getFactory(DimensionData data) {
        for (IDimensionFactory factory : FACTORIES) {
            try {
                if (factory.matches(data)) return factory;
            } catch (Exception e) {
                LOGGER.error("DimensionFactory {} threw in matches(): {}", factory.getId(), e.getMessage());
            }
        }
        return null;
    }

    /** 返回全部已注册工厂（只读视图） */
    public static List<IDimensionFactory> getAll() {
        return Collections.unmodifiableList(FACTORIES);
    }
}
