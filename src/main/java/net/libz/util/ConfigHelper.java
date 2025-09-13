package net.libz.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jetbrains.annotations.Nullable;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;

import me.shedaniel.autoconfig.util.Utils;

public class ConfigHelper {

    private static final Jankson JANKSON = Jankson.builder().build();

    public static void copyConfig(String configName, boolean gson) {
        Path configPath = getConfigPath(configName, gson);
        Path singleplayerConfigPath = configPath.resolveSibling("singleplayer_" + configPath.getFileName());
        try {
            Files.copy(configPath, singleplayerConfigPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readConfigFile(String configName, boolean gson, boolean excludeClientOnly) {
        try {
            String string = Files.readString(getConfigPath(configName, gson));
            if (!gson && excludeClientOnly) {

                StringBuilder configString = new StringBuilder();
                String[] configStrings = string.split("\n");

                for (int i = 0; i < configStrings.length; i++) {
                    if (!configStrings[i].contains("//") || !configStrings[i].contains("client only")) {
                        configString.append(configStrings[i] + "\n");
                    } else {
                        i += 1;
                    }
                }
                return configString.toString().replaceAll(",\\s*\\}", "\n}");
            }
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static JsonObject getConfigNode(String configName, boolean gson, boolean excludeClientOnly) {
        try {
            String json = readConfigFile(configName, gson, excludeClientOnly);
            if (json == null) return null;
            return JANKSON.load(json);
        } catch (SyntaxError e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static byte[] getConfigBytes(String configName, boolean gson, boolean excludeClientOnly) {
        try {
            JsonObject obj = getConfigNode(configName, gson, excludeClientOnly);
            if (obj == null) return null;
            return obj.toJson().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static JsonObject readJsonTree(byte[] bytes) {
        try {
            String json = new String(bytes, StandardCharsets.UTF_8);
            return JANKSON.load(json);
        } catch (SyntaxError e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Path getConfigPath(String configName, boolean gson) {
        return Utils.getConfigFolder().resolve(configName + (gson ? ".json" : ".json5"));
    }

}
