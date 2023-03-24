package net.libz.api;

import org.jetbrains.annotations.Nullable;

public interface Tab {

    default boolean isBlockTab() {
        return false;
    }

    @Nullable
    default Class<?> getParentScreenClass() {
        return null;
    }
}
