package com.mow.mod.worldbridge.api;

import com.mow.mod.worldbridge.dimension.DimensionData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.dimension.LevelStem;

/**
 * 自定义维度类型工厂接口。
 * 附属 mod 实现本接口并调用 {@link DimensionFactoryRegistry#register} 注册，
 * 即可接管特定 dimType 维度的 LevelStem 生成（如自定义生成器、自定义维度类型）。
 */
public interface IDimensionFactory {

    /** 工厂唯一标识（附属 mod 命名空间 + 类型名，如 "myaddon:void_world"） */
    ResourceLocation getId();

    /** 判断该工厂是否负责处理此维度数据（一般按 dimType / worldType 匹配） */
    boolean matches(DimensionData data);

    /**
     * 生成 LevelStem。
     * 返回 null 表示工厂放弃接管，WorldBridge 将回退到内置生成逻辑。
     */
    LevelStem createStem(MinecraftServer server, DimensionData data);
}
