package co.purevanilla.mcplugins.chatcolors.api;

import org.bukkit.Material;

public class DisplaySettings {

    public Material hasPermission;
    public Material noPermission;

    public DisplaySettings(Material hasPermission, Material noPermission){
        this.hasPermission=hasPermission;
        this.noPermission=noPermission;
    }

}
