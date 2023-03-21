package net.libz;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.api.InventoryTab;
import net.libz.util.SortList;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LibzClient implements ClientModInitializer {

    public static final List<InventoryTab> inventoryTabs = new ArrayList<InventoryTab>();

    public static final Identifier inventoryTabTexture = new Identifier("libz:textures/gui/icons.png");

    @Override
    public void onInitializeClient() {
        // TEST
        // registerInventoryTab(new InventoryTab(InventoryScreen.class, Text.of("TEST"), new Identifier("libz:textures/gui/test.png"), new Identifier("test:test"), 0));
        // registerInventoryTab(new InventoryTab(InventoryScreen.class, Text.of("TEST3"), new Identifier("libz:textures/gui/test.png"), new Identifier("test:test"), 7));
        // registerInventoryTab(new InventoryTab(InventoryScreen.class, Text.of("TEST2"), new Identifier("libz:textures/gui/test.png"), new Identifier("test:test"), 3));
        // registerInventoryTab(new InventoryTab(InventoryScreen.class, Text.of("TEST1"), new Identifier("libz:textures/gui/test.png"), new Identifier("test:test"), 1));
    }

    public static void registerInventoryTab(InventoryTab tab) {
        LibzClient.inventoryTabs.add(tab);
        // Sort prefered pos
        List<Integer> priorityList = new ArrayList<Integer>();
        for (int i = 0; i < inventoryTabs.size(); i++) {
            int preferedPos = inventoryTabs.get(i).getPreferedPos();
            if (preferedPos == -1) {
                preferedPos = 99;
            }
            priorityList.add(preferedPos);
        }
        SortList.concurrentSort(priorityList, inventoryTabs);
    }

}
