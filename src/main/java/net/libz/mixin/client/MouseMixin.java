package net.libz.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.fabricmc.api.Environment;
import net.libz.access.MouseAccessor;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MouseMixin implements MouseAccessor {

    @Shadow
    private double x;
    @Shadow
    private double y;
    @Shadow
    private MinecraftClient client;

    @Override
    public void setMousePosition(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
        InputUtil.setCursorParameters(this.client.getWindow(), 212993, this.x, this.y);
    }
}
