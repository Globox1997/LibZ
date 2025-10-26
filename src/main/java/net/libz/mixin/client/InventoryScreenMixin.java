package net.libz.mixin.client;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.api.Tab;
import net.libz.util.DrawTabHelper;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;

// Could inject into Screen class but imo uneccessary
@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends RecipeBookScreen<PlayerScreenHandler> implements Tab {

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, RecipeBookWidget<?> recipeBook, PlayerInventory playerInventory, Text text) {
        super(screenHandler, recipeBook,playerInventory, text);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        DrawTabHelper.onTabButtonClick(client, this, this.x, this.y, click.x(), click.y(), this.focusedSlot != null);
        return super.mouseClicked(click, doubled);
    }

    // @Inject(method = "mouseClicked", at = @At("HEAD"))
    // private void mouseClickedMixin(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> info) {
    //     DrawTabHelper.onTabButtonClick(client, this, this.x, this.y, mouseX, mouseY, this.focusedSlot != null);
    // }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    protected void drawBackgroundMixin(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo info) {
        DrawTabHelper.drawTab(client, context, this, x, y, mouseX, mouseY);
    }
}
