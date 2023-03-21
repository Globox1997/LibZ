package net.libz.registry;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.LibzClient;
import net.libz.api.InventoryTab;
import net.libz.util.SortList;

@Environment(EnvType.CLIENT)
public class TabRegistry {

    public static void registerInventoryTab(InventoryTab tab) {
        LibzClient.inventoryTabs.add(tab);
        // Sort prefered pos
        List<Integer> priorityList = new ArrayList<Integer>();
        for (int i = 0; i < LibzClient.inventoryTabs.size(); i++) {
            int preferedPos = LibzClient.inventoryTabs.get(i).getPreferedPos();
            if (preferedPos == -1) {
                preferedPos = 99;
            }
            priorityList.add(preferedPos);
        }
        SortList.concurrentSort(priorityList, LibzClient.inventoryTabs);
    }
}
