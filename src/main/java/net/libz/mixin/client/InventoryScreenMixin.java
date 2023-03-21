package net.libz.mixin.client;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.LibzClient;
import net.libz.api.InventoryTab;
import net.libz.api.Tab;
import net.libz.init.ConfigInit;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;

// Could inject into Screen class but imo uneccessary
@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements Tab {

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void mouseClickedMixin(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> info) {
        if (this.client != null && ConfigInit.CONFIG.inventoryButton && this.focusedSlot == null && this instanceof Tab) {
            int xPos = this.x;
            for (int i = 0; i < LibzClient.inventoryTabs.size(); i++) {
                InventoryTab inventoryTab = LibzClient.inventoryTabs.get(i);
                if (inventoryTab.shouldShow(client)) {
                    if (!inventoryTab.isSelectedScreen(this.getClass()) && this.isPointWithinBounds(xPos - this.x + 1, -20, 22, 19, (double) mouseX, (double) mouseY)) {
                        inventoryTab.onClick(client);
                    }
                    xPos += 25;
                }
            }
        }
    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    protected void drawBackgroundMixin(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo info) {
        if (this.client != null && this.client.player != null && ConfigInit.CONFIG.inventoryButton && this instanceof Tab) {

            int xPos = this.x;
            Text shownTooltip = null;
            for (int i = 0; i < LibzClient.inventoryTabs.size(); i++) {
                InventoryTab inventoryTab = LibzClient.inventoryTabs.get(i);
                if (inventoryTab.shouldShow(client)) {

                    boolean isFirstTab = i == 0;
                    boolean isSelectedTab = inventoryTab.isSelectedScreen(this.getClass());

                    int textureX = isFirstTab ? 24 : 72;
                    if (isSelectedTab) {
                        textureX -= 24;
                    }

                    RenderSystem.setShaderTexture(0, LibzClient.inventoryTabTexture);
                    this.drawTexture(matrices, xPos, isSelectedTab ? this.y - 27 : this.y - 21, textureX, 0, 24, isSelectedTab ? 27 : 21);

                    RenderSystem.setShaderTexture(0, inventoryTab.getTexture());
                    DrawableHelper.drawTexture(matrices, xPos + 5, this.y - 16, 0, 0, 14, 14, 14, 14);

                    if (!isSelectedTab && this.isPointWithinBounds(xPos - this.x + 1, -20, 22, 19, (double) mouseX, (double) mouseY)) {
                        shownTooltip = inventoryTab.getTitle();
                    }
                    xPos += 25;
                }
            }
            if (shownTooltip != null) {
                this.renderTooltip(matrices, shownTooltip, mouseX, mouseY);
            }
        }
    }
}
