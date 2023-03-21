package net.libz;

import net.fabricmc.api.ModInitializer;
import net.libz.init.*;

public class LibzMain implements ModInitializer {

    @Override
    public void onInitialize() {
        ConfigInit.init();
    }

}
