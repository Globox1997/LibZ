package net.libz;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.api.InventoryTab;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LibzClient implements ClientModInitializer {

    public static final List<InventoryTab> inventoryTabs = new ArrayList<InventoryTab>();

    public static final Identifier inventoryTabTexture = new Identifier("libz:textures/gui/icons.png");

    @Override
    public void onInitializeClient() {
    }

}
