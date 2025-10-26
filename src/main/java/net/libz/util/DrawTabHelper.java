package net.libz.util;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.LibzClient;
import net.libz.api.InventoryTab;
import net.libz.api.Tab;
import net.libz.init.ConfigInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DrawTabHelper {

    /**
     * Draw a tab on top of a screen.
     * 
     * <p>
     * Required to call on client only screens. Not required on handled screens.
     *
     * @param client      A MinecraftClient instance.
     * @param context     The DrawContext of the render method.
     * @param screenClass The screen class (not the parent).
     * @param x           The left position of the screen.
     * @param y           The top position of the screen.
     * @param mouseX      The x mouse position.
     * @param mouseY      The y mouse position.
     */
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

                        drawTexture(context,LibzClient.tabTexture, xPos, isSelectedTab ? y - 23 : y - 21, textureX, 0, 24, isSelectedTab ? 27 : isFirstTab ? 25 : 21);
                        if (inventoryTab.getTexture() != null) {
                            drawTexture(context,inventoryTab.getTexture(), xPos + 5, y - 16, 0, 0, 14, 14, 14, 14);
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

    private static void drawTexture(DrawContext context,Identifier texture,int x,int y,float u,float v,int width,int height,int textureWidth,int textureHeight) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, texture, x, y, u , v, width, height, textureWidth, textureHeight);
    }

    private static void drawTexture(DrawContext context,Identifier texture, int x, int y, int u, int v, int width, int height) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, texture, x, y, u , v, width, height, width, height);
    }

    /**
     * Tab button click method. Call it at mouseClicked method.
     * 
     * <p>
     * Required to call on client only screens. Not required on handled screens.
     *
     * @param client      A MinecraftClient instance.
     * @param screenClass The screen class (not the parent).
     * @param x           The left position of the screen.
     * @param y           The top position of the screen.
     * @param mouseX      The x mouse position.
     * @param mouseY      The y mouse position.
     * @param focused     If another child is focused.
     */
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
