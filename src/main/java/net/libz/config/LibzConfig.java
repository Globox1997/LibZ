package net.libz.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "libz")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class LibzConfig implements ConfigData {

    @Comment("Show inventory tabs")
    public boolean inventoryButton = true;

}