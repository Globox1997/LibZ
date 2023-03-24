package net.libz.api;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BlockTab extends InventoryTab {

    private final Class<?> parentClass;

    public BlockTab(Text title, @Nullable Identifier texture, @Nullable ItemStack itemStack, int preferedPos, Class<?> parentClass, Class<?> tabClass) {
        super(title, texture, itemStack, preferedPos, tabClass);
        this.parentClass = parentClass;
    }

    public Class<?> getParentClass() {
        return this.parentClass;
    }

}
