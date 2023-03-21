package net.libz.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@SuppressWarnings("rawtypes")
@Environment(EnvType.CLIENT)
public class InventoryTab {

    private final Class[] screenClasses;
    private final Text title;
    private final Identifier texture;
    private final int preferedPos;

    public InventoryTab(Text title, Identifier texture, int preferedPos, Class... screenClasses) {
        this.screenClasses = screenClasses;
        this.title = title;
        this.texture = texture;
        this.preferedPos = preferedPos;
    }

    public Text getTitle() {
        return this.title;
    }

    public Identifier getTexture() {
        return this.texture;
    }

    public int getPreferedPos() {
        return this.preferedPos;
    }

    public boolean shouldShow(MinecraftClient client) {
        return true;
    }

    public void onClick(MinecraftClient client) {
    }

    public boolean isSelectedScreen(Class screenClass) {
        for (int i = 0; i < screenClasses.length; i++) {
            if (screenClasses[i].equals(screenClass)) {
                return true;
            }
        }
        return false;
    }

}
