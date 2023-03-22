package net.libz.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.util.DrawTabHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
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
        DrawTabHelper.onTabButtonClick(client, this, this.left, this.top, mouseX, mouseY, this.description.getFocus() != null);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderMixin(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, CallbackInfo info) {
        DrawTabHelper.drawTab(client, matrices, this, this.left, this.top, mouseX, mouseY);
    }

}
