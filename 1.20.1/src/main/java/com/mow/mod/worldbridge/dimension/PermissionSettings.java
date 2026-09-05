package com.mow.mod.worldbridge.dimension;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PermissionSettings {
    private List<UUID> whitelist = new ArrayList<>();
    private boolean allowBlockBreaking = true;

    public List<UUID> getWhitelist() { return whitelist; }
    public void setWhitelist(List<UUID> whitelist) { this.whitelist = whitelist; }

    public boolean isAllowBlockBreaking() { return allowBlockBreaking; }
    public void setAllowBlockBreaking(boolean allowBlockBreaking) { this.allowBlockBreaking = allowBlockBreaking; }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();
        for (UUID uuid : whitelist) {
            list.add(StringTag.valueOf(uuid.toString()));
        }
        tag.put("Whitelist", list);
        tag.putBoolean("AllowBlockBreaking", allowBlockBreaking);
        return tag;
    }

    public static PermissionSettings fromNbt(CompoundTag tag) {
        PermissionSettings settings = new PermissionSettings();
        settings.whitelist = new ArrayList<>();
        ListTag list = tag.getList("Whitelist", Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++) {
            try {
                settings.whitelist.add(UUID.fromString(list.getString(i)));
            } catch (IllegalArgumentException ignored) {}
        }
        settings.allowBlockBreaking = tag.getBoolean("AllowBlockBreaking");
        return settings;
    }
}
