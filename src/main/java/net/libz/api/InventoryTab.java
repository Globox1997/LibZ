package net.libz.api;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * InventoryTab class to be extended to create a new tab for a screen.
 * 
 * <p>
 * Register with the TabRegistry.
 *
 * @version 1.0
 */
@Environment(EnvType.CLIENT)
public class InventoryTab {

    private final Class<?>[] screenClasses;
    private final Text title;
    @Nullable
    private final Identifier texture;
    private final int preferedPos;

    /**
     * Constructor to create a new inventory tab.
     * 
     * @param title         Text to be rendered on tab hover.
     * @param texture       Identifier of the tab icon texture which has a size of 14x14.
     * @param preferedPos   Number of the prefered position. 0 = far left.
     * @param screenClasses Screen class list of the inventory tab screen.
     */
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
        client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
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
