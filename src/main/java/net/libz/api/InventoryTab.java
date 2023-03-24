package net.libz.api;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InventoryTab {

    private final Class<?>[] screenClasses;
    private final Text title;
    @Nullable
    private final Identifier texture;
    private final int preferedPos;

    public InventoryTab(Text title, @Nullable Identifier texture, int preferedPos, Class<?>... screenClasses) {
        this.screenClasses = screenClasses;
        this.title = title;
        this.texture = texture;
        this.preferedPos = preferedPos;
    }

    public Text getTitle() {
        return this.title;
    }

    @Nullable
    public Identifier getTexture() {
        return this.texture;
    }

    @Nullable
    public ItemStack getItemStack(MinecraftClient client) {
        return null;
    }

    public int getPreferedPos() {
        return this.preferedPos;
    }

    public boolean shouldShow(MinecraftClient client) {
        return true;
    }

    public void onClick(MinecraftClient client) {
    }

    public boolean canClick(Class<?> screenClass, MinecraftClient client) {
        return !isSelectedScreen(screenClass);
    }

    public boolean isSelectedScreen(Class<?> screenClass) {
        for (int i = 0; i < screenClasses.length; i++) {
            if (screenClasses[i].equals(screenClass)) {
                return true;
            }
        }
        return false;
    }

}
