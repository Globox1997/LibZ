package net.libz.network;

import java.util.Iterator;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.api.SyntaxError;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.ConfigManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.libz.access.MouseAccessor;
import net.libz.api.ConfigSync;
import net.libz.mixin.config.AutoConfigAccess;
import net.libz.network.packet.ConfigPacket;
import net.libz.network.packet.MousePacket;
import net.libz.util.ConfigHelper;

@SuppressWarnings("unchecked")
@Environment(EnvType.CLIENT)
public class LibzClientPacket {

    @SuppressWarnings("resource")
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ConfigPacket.PACKET_ID, (payload, context) -> {
            String configName = payload.configName();
            boolean gson = payload.gson();
            byte[] jsonBytes = payload.bytes();

            context.client().execute(() -> {
                JsonObject oldJsonNode = ConfigHelper.getConfigNode(configName, gson, false);
                JsonObject newJsonNode = ConfigHelper.readJsonTree(jsonBytes);

                if (oldJsonNode != null && newJsonNode != null) {
                    for (String key : newJsonNode.keySet()) {
                        JsonElement newValue = newJsonNode.get(key);
                        oldJsonNode.put(key, newValue);
                    }

                    Jankson jankson = Jankson.builder().build();

                    for (ConfigHolder<?> holder : AutoConfigAccess.getHolders().values()) {
                        if (((ConfigManager<?>) holder).getDefinition().name().equals(configName)) {
                            try {
                                String mergedJson = oldJsonNode.toJson();
                                ConfigData data = (ConfigData) jankson.fromJson(jankson.load(mergedJson), holder.getConfigClass());

                                ((ConfigManager<ConfigData>) holder).setConfig(data);

                                if (holder.getConfig() instanceof ConfigSync) {
                                    ((ConfigSync) holder.getConfig()).updateConfig(data);
                                }

                            } catch (SyntaxError e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(MousePacket.PACKET_ID, (payload, context) -> {
            int mouseX = payload.mouseX();
            int mouseY = payload.mouseY();
            context.client().execute(() -> {
                ((MouseAccessor) context.client().mouse).setMousePosition(mouseX, mouseY);
            });
        });
    }
}
