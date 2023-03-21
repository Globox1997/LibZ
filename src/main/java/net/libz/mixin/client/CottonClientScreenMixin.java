package net.libz.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.libz.LibzClient;
import net.libz.api.InventoryTab;
import net.libz.api.Tab;
import net.libz.init.ConfigInit;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Mixin(CottonClientScreen.class)
public abstract class CottonClientScreenMixin extends Screen {

    @Shadow
    protected GuiDescription description;
    @Shadow
    protected int left = 0;
    @Shadow
    protected int top = 0;

    public CottonClientScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void mouseClickedMixin(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> info) {
        if (this.client != null && ConfigInit.CONFIG.inventoryButton && this.description.getFocus() == null && this instanceof Tab) {
            int xPos = this.left;
            for (int i = 0; i < LibzClient.inventoryTabs.size(); i++) {
                InventoryTab inventoryTab = LibzClient.inventoryTabs.get(i);
                if (inventoryTab.shouldShow(client)) {
                    if (!inventoryTab.isSelectedScreen(this.getClass()) && this.isPointWithinBounds(xPos - this.left + 1, -20, 22, 19, (double) mouseX, (double) mouseY)) {
                        inventoryTab.onClick(client);
                    }
                    xPos += 25;
                }
            }
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderMixin(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, CallbackInfo info) {
        if (this.client != null && this.client.player != null && ConfigInit.CONFIG.inventoryButton && this instanceof Tab) {

            int xPos = this.left;
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
                    this.drawTexture(matrices, xPos, isSelectedTab ? this.top - 27 : this.top - 21, textureX, 0, 24, isSelectedTab ? 27 : 21);

                    RenderSystem.setShaderTexture(0, inventoryTab.getTexture());
                    DrawableHelper.drawTexture(matrices, xPos + 5, this.top - 16, 0, 0, 14, 14, 14, 14);

                    if (!isSelectedTab && this.isPointWithinBounds(xPos - this.left + 1, -20, 22, 19, (double) mouseX, (double) mouseY)) {
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

    private boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
        int i = this.left;
        int j = this.top;
        return (pointX -= (double) i) >= (double) (x - 1) && pointX < (double) (x + width + 1) && (pointY -= (double) j) >= (double) (y - 1) && pointY < (double) (y + height + 1);
    }
}
