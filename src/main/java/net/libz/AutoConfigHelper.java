package net.libz;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;

import java.lang.reflect.Field;
import java.util.Map;

public class AutoConfigHelper {
    private static Field holdersField;

    @SuppressWarnings("unchecked")
    public static Map<Class<?>, ConfigHolder<?>> getHolders() {
        try {
            if (holdersField == null) {
                holdersField = AutoConfig.class.getDeclaredField("holders");
                holdersField.setAccessible(true);
            }
            return (Map<Class<?>, ConfigHolder<?>>) holdersField.get(null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to access AutoConfig.holders", e);
        }
    }
}
