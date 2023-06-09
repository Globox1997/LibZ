package net.libz.util;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.LibzClient;
import net.libz.api.InventoryTab;
import net.libz.api.Tab;
import net.libz.init.ConfigInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class DrawTabHelper {

    public static void drawTab(MinecraftClient client, DrawContext context, Screen screenClass, int x, int y, int mouseX, int mouseY) {
        if (client != null && client.player != null && ConfigInit.CONFIG.inventoryButton && (Object) screenClass instanceof Tab) {

            int xPos = x;
            Text shownTooltip = null;

            List<InventoryTab> list = null;
            if (((Tab) screenClass).getParentScreenClass() != null) {
                if (LibzClient.otherTabs.isEmpty() || !LibzClient.otherTabs.containsKey(((Tab) screenClass).getParentScreenClass())) {
                    return;
                }
                list = LibzClient.otherTabs.get(((Tab) screenClass).getParentScreenClass());
            } else {
                list = LibzClient.inventoryTabs;
            }
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    InventoryTab inventoryTab = list.get(i);
                    if (inventoryTab.shouldShow(client)) {

                        boolean isFirstTab = i == 0;
                        boolean isSelectedTab = inventoryTab.isSelectedScreen(screenClass.getClass());

                        int textureX = isFirstTab ? 24 : 72;
                        if (isSelectedTab) {
                            textureX -= 24;
                        }

                        context.drawTexture(LibzClient.tabTexture, xPos, isSelectedTab ? y - 23 : y - 21, textureX, 0, 24, isSelectedTab ? 27 : isFirstTab ? 25 : 21);
                        if (inventoryTab.getTexture() != null) {
                            context.drawTexture(inventoryTab.getTexture(), xPos + 5, y - 16, 0, 0, 14, 14, 14, 14);
                        } else if (inventoryTab.getItemStack(client) != null) {
                            context.drawItem(inventoryTab.getItemStack(client), xPos + 4, y - 17);
                        }

                        if (!isSelectedTab && isPointWithinBounds(x, y, xPos - x + 1, -20, 22, 19, (double) mouseX, (double) mouseY)) {
                            shownTooltip = inventoryTab.getTitle();
                        }
                        xPos += 25;
                    }
                }
            }
            if (shownTooltip != null) {
                context.drawTooltip(client.textRenderer, shownTooltip, mouseX, mouseY);
            }
        }
    }

    public static void onTabButtonClick(MinecraftClient client, Screen screenClass, int x, int y, double mouseX, double mouseY, boolean focused) {
        if (client != null && ConfigInit.CONFIG.inventoryButton && !focused && screenClass instanceof Tab) {
            int xPos = x;

            List<InventoryTab> list = null;
            if (((Tab) screenClass).getParentScreenClass() != null) {
                if (LibzClient.otherTabs.isEmpty() || !LibzClient.otherTabs.containsKey(((Tab) screenClass).getParentScreenClass())) {
                    return;
                }
                list = LibzClient.otherTabs.get(((Tab) screenClass).getParentScreenClass());
            } else {
                list = LibzClient.inventoryTabs;
            }
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    InventoryTab inventoryTab = list.get(i);
                    if (inventoryTab.shouldShow(client)) {
                        boolean isSelectedTab = inventoryTab.isSelectedScreen(screenClass.getClass());
                        if (inventoryTab.canClick(screenClass.getClass(), client)
                                && isPointWithinBounds(x, y, xPos - x + 1, isSelectedTab ? -24 : -20, 22, isSelectedTab ? 23 : 19, (double) mouseX, (double) mouseY)) {
                            inventoryTab.onClick(client);
                        }
                        xPos += 25;
                    }
                }
            }
        }
    }

    private static boolean isPointWithinBounds(int xPos, int yPos, int x, int y, int width, int height, double pointX, double pointY) {
        return (pointX -= (double) xPos) >= (double) (x - 1) && pointX < (double) (x + width + 1) && (pointY -= (double) yPos) >= (double) (y - 1) && pointY < (double) (y + height + 1);
    }
}
