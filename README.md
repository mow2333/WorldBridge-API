# WorldBridge-API

WorldBridge（世界之桥）模组的公开 API 模块。本仓库仅包含**接口 / 事件 / 数据类**，不含任何实现逻辑——实现由 WorldBridge 主 mod 闭源提供。

## 模块结构

每个 Minecraft 版本一个独立工程目录，**十分支全覆盖**：

```
1.19/ 1.19.1/ 1.19.2/ 1.19.3/ 1.19.4/    ← Minecraft 1.19.x
1.20/ 1.20.1/ 1.20.2/ 1.20.3/ 1.20.4/    ← Minecraft 1.20.x
```

各目录为独立 ForgeGradle 库工程，按对应 MC/Forge 版本编译，产出的 jar 已发布到 Maven Central（坐标见下）。

## API 总览

| 系统 | 门面 | 事件 | 说明 |
|------|------|------|------|
| 维度 | `WorldBridgeAPI` | `WorldBridgeEvents` | 维度数据 CRUD / 传送 / LevelStem 生成 |
| 红链 | `RedlinkAPI` | `RedlinkEvents` | 信号查询 / 断连操作；配对、断连、信号变化事件 |
| 跨维漏斗 | `HopperAPI` | `HopperEvents` | 配对状态 / 吞吐查询；配对、断连、传输事件 |
| 维度地球仪 | `GlobeAPI` | — | 只读查询维度网络节点 / 边 |

服务端生命周期事件通过 Forge 事件总线发布，附属 mod 用 `@SubscribeEvent` 监听。

## 附属 mod 依赖方式

### 1. 从 Maven Central 拉取（推荐）

WorldBridge-API 已发布到 **Maven Central**（`io.github.mow2333`），附属 mod 无需配置自定义仓库，直接声明坐标即可：

```groovy
repositories {
    mavenCentral()
}

dependencies {
    // 选择与你的 Minecraft 版本对应的 artifact
    compileOnly 'io.github.mow2333:worldbridge-api-1.20.1:0.5.0'
    // 例如 1.19.4 → worldbridge-api-1.19.4；1.20.4 → worldbridge-api-1.20.4 ...
}
```

> artifact 后缀即 Minecraft 版本：`worldbridge-api-1.19` / `1.19.1` / … / `1.20.4`
> 运行期仍需要安装 WorldBridge 主 mod（实现闭源提供）。

### 2. 源码方式（可选）

将仓库中对应版本目录加入工程源码路径：

```groovy
// 以 1.20.1 为例
sourceSets.main.java.srcDirs += file('WorldBridge-API/1.20.1/src/main/java')
```

### 2. 调用约定

所有门面采用**服务定位**模式——调用前确认主 mod 已加载：

```java
if (WorldBridgeAPI.isServiceAvailable()) {
    // 安全调用
}
```

若服务未注册（WorldBridge 主 mod 未加载）而强行调用，门面会抛出 `IllegalStateException`。

### 3. 监听事件

```java
@SubscribeEvent
public void onRedlinkPair(RedlinkEvents.RedlinkPairEvent event) {
    // event.getPairId(), event.getPosA() ...
}
```

## 版本

- `mod_version` 跟随 WorldBridge 主 mod 同步推进（当前 0.5.0）
- 覆盖 Minecraft **1.19 ~ 1.20.4 共 10 个版本**（Forge 41.0.68+ ~ 49.1.0+）

## License

[MIT](LICENSE)

API 开源，WorldBridge 主 mod 实现闭源（All Rights Reserved）。
