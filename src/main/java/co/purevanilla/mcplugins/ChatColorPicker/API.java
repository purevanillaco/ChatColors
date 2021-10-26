package co.purevanilla.mcplugins.ChatColorPicker;

import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class API {

    private static File dataFile;
    private static Plugin plugin;
    public static FileConfiguration data;
    public static Map<UUID, Color> userCache = new HashMap<>();       // playerid, color
    public static Map<String, Color> colorData = new HashMap<>();    // name, color

    public API(Plugin plugin) throws IOException, InvalidConfigurationException {
        API.plugin=plugin;
        ConfigurationSection colorInfo = API.plugin.getConfig().getConfigurationSection("colors");
        assert colorInfo != null;
        @NotNull Map<String, Object> colorRel = colorInfo.getValues(false); // will result in <hex,name>
        for (Map.Entry<String, Object> entry : colorRel.entrySet()) {
            API.colorData.put((String) entry.getValue(),new Color((String) entry.getValue(),"#"+entry.getKey()));
        }

        API.dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!API.dataFile.exists()) {
            API.dataFile.getParentFile().mkdirs();
            API.plugin.saveResource("data.yml", false);
        }

        API.data= new YamlConfiguration();
        API.data.load(API.dataFile);

    }

    public void setColor(Player player, Color color) throws IOException {
        if(color==null||color.name==null){
            // remove from config
            API.userCache.put(player.getUniqueId(),new Color(null, TextColor.fromHexString("#fff")));
            data.set(player.getUniqueId().toString().replaceAll("-+",""),null);
        } else {
            // modify config
            API.userCache.put(player.getUniqueId(),color);
            data.set(player.getUniqueId().toString().replaceAll("-+",""),color.name);
        }
        data.save(dataFile);
    }

    public static void removeFromCache(@NotNull Player player){
        userCache.remove(player.getUniqueId());
    }

    public List<Color> getColors(){
        List<Color> colors = new ArrayList<>();
        for (Map.Entry<String, Color> entry : colorData.entrySet()) {
            colors.add(entry.getValue());
        }
        return colors;
    }

    public Color getColorFromName(String name){
        if(colorData.containsKey(name)){
            return colorData.get(name);
        } else {
            return new Color(null,TextColor.fromHexString("#fff"));
        }
    }

    public Color getColor(@NotNull Player player){
        if(!userCache.containsKey(player.getUniqueId())) {
            // cache
            String colorName = data.getString(player.getUniqueId().toString().replaceAll("-+",""));
            if(colorName!=null){
                // GET COLOR, RESET IF NULL
                userCache.put(player.getUniqueId(),getColorFromName(colorName));
            } else {
                // NO COLOR SET
                userCache.put(player.getUniqueId(),new Color(null, TextColor.fromHexString("#fff")));
            }
        }
        return API.userCache.get(player.getUniqueId());
    }

}
