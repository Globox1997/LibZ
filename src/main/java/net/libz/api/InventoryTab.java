package net.libz.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@SuppressWarnings("rawtypes")
@Environment(EnvType.CLIENT)
public class InventoryTab {

    private final Class screenClass;
    private final Text title;
    private final Identifier texture;
    private final Identifier packetId;
    private final int preferedPos;

    public InventoryTab(Class screenClass, Text title, Identifier texture, Identifier packetId, int preferedPos) {
        this.screenClass = screenClass;
        this.title = title;
        this.texture = texture;
        this.packetId = packetId;
        this.preferedPos = preferedPos;
    }

    public Class getScreen() {
        return this.screenClass;
    }

    public Text getTitle() {
        return this.title;
    }

    public Identifier getTexture() {
        return this.texture;
    }

    public Identifier getPacketId() {
        return this.packetId;
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
        return this.screenClass.equals(screenClass);
    }

}
