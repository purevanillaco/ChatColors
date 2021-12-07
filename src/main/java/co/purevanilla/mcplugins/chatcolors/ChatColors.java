package co.purevanilla.mcplugins.chatcolors;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.PaperCommandManager;
import co.purevanilla.mcplugins.chatcolors.api.Color;
import co.purevanilla.mcplugins.chatcolors.api.DisplaySettings;
import co.purevanilla.mcplugins.chatcolors.menu.Colors;
import co.purevanilla.mcplugins.chatcolors.placeholder.ChatColorPlaceholder;
import co.purevanilla.mcplugins.chatcolors.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

public class ChatColors extends JavaPlugin {

    private static ChatColors instance;
    static List<Color> colors;
    static PlayerSettings settings;
    static DisplaySettings display;

    public static void registerListener(Colors colors){
        instance.getServer().getPluginManager().registerEvents(colors, instance);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.saveDefaultConfig();
        ChatColors.instance=this;

        // color loading
        Set<String> keys = Objects.requireNonNull(this.getConfig().getConfigurationSection("colors")).getKeys(false);
        ChatColors.colors = new ArrayList<>();
        for (String key:keys) {
            ChatColors.colors.add(new Color(key,this.getConfig().getString("colors."+key+".hex"),this.getConfig().getString("colors."+key+".permission"),this.getConfig().getString("colors."+key+".noPermission")));
        }
        File playerData = new File(getDataFolder(), "data.yml");
        if (!playerData.exists()) {
            playerData.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        FileConfiguration playerDataYML = new YamlConfiguration();
        try {
            playerDataYML.load(playerData);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            this.onDisable();
        }
        ChatColors.display = new DisplaySettings(Material.valueOf(this.getConfig().getString("gui.hasPermission")),Material.valueOf(this.getConfig().getString("gui.noPermission")));
        ChatColors.settings = new PlayerSettings(this,playerDataYML, playerData);

        // placeholder

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ChatColorPlaceholder().register();
        } else {
            getLogger().log(Level.WARNING,"Couldn't hook into placeholder api");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // cmd
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new CMD());
    }

    public static List<Color> getColors(){
        return colors;
    }

    public static Color getColor(String name){
        for (Color color : colors) {
            if (color.name.equalsIgnoreCase(name)) {
                return color;
            }
        }
        return null;
    }

    public static PlayerSettings getSettings(){
        return settings;
    }
    public static DisplaySettings getDisplay(){
        return display;
    }


}
