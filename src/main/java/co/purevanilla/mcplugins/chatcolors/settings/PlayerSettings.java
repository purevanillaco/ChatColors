package co.purevanilla.mcplugins.chatcolors.settings;

import co.purevanilla.mcplugins.chatcolors.ChatColors;
import co.purevanilla.mcplugins.chatcolors.api.Color;
import co.purevanilla.mcplugins.chatcolors.api.PlayerColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PlayerSettings {

    ChatColors chatColors;
    Map<UUID, Color> cache;
    FileConfiguration data;
    File file;

    public PlayerSettings(ChatColors chatColors, FileConfiguration data, File file){
        this.cache = new HashMap<>();
        this.chatColors=chatColors;
        this.data=data;
        this.file=file;
    }

    public void setPlayer(UUID uuid, Color color) throws IOException {
        this.data.set("player."+uuid,color.name);
        this.data.save(this.file);
    }

    public void resetPlayer(UUID uuid) throws IOException {
        this.data.set("player."+uuid,null);
        this.deleteCache(uuid);
        this.data.save(this.file);
    }

    public void deleteCache(UUID uuid){
        this.cache.remove(uuid);
    }

    public PlayerColor getPlayer(UUID uuid){

        // return from cache/storage

        PlayerColor color;

        if(cache.containsKey(uuid)){
            color=new PlayerColor(cache.get(uuid), uuid);
        } else if(data.contains("player."+uuid)) {
            color=new PlayerColor(ChatColors.getColor(data.getString("player."+uuid)), uuid);
        } else {
            color=new PlayerColor(ChatColors.getColor("default"), uuid);
        }

        // update for next message in case the player no longer has the perm

        if(!color.color.name.equalsIgnoreCase("default")){
            Bukkit.getScheduler().runTaskAsynchronously(chatColors, new Runnable() {
                @Override
                public void run() {
                    if(!Objects.requireNonNull(Bukkit.getPlayer(color.player)).hasPermission(color.color.permission)){
                        try {
                            resetPlayer(color.player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        return color;
    }

}
