package net.libz.registry;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.LibzClient;
import net.libz.api.*;
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

    public static void registerOtherTab(InventoryTab tab, Class<?> parentClass) {
        if (LibzClient.otherTabs.get(parentClass) != null) {
            LibzClient.otherTabs.get(parentClass).add(tab);
            // Sort prefered pos
            List<Integer> priorityList = new ArrayList<Integer>();
            for (int i = 0; i < LibzClient.otherTabs.get(parentClass).size(); i++) {
                int preferedPos = LibzClient.otherTabs.get(parentClass).get(i).getPreferedPos();
                if (preferedPos == -1) {
                    preferedPos = 99;
                }
                priorityList.add(preferedPos);
            }
            SortList.concurrentSort(priorityList, LibzClient.otherTabs.get(parentClass));
        } else {
            List<InventoryTab> list = new ArrayList<InventoryTab>();
            list.add(tab);
            LibzClient.otherTabs.put(parentClass, list);
        }
    }
}
