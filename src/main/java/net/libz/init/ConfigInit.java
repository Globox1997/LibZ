package net.libz.init;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.libz.config.LibzConfig;

public class ConfigInit {

    public static LibzConfig CONFIG = new LibzConfig();

    public static void init() {
        AutoConfig.register(LibzConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(LibzConfig.class).getConfig();
    }

}