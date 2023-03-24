package net.libz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.libz.api.*;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LibzClient implements ClientModInitializer {

    public static final List<InventoryTab> inventoryTabs = new ArrayList<InventoryTab>();
    public static final HashMap<Class<?>, List<InventoryTab>> blockTabs = new HashMap<Class<?>, List<InventoryTab>>();

    public static final Identifier tabTexture = new Identifier("libz:textures/gui/icons.png");

    public static final boolean isLibGuiLoaded = FabricLoader.getInstance().isModLoaded("libgui");

    @Override
    public void onInitializeClient() {
    }

}
