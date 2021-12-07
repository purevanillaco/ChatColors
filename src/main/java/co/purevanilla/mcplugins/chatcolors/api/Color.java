package co.purevanilla.mcplugins.chatcolors.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class Color {

    public String name;
    public String hex;
    public String permission;
    public Component noPermission;

    public Color(String name, String hex, String permission, String noPermission){
        this.name=name;
        this.hex=hex;
        this.permission=permission;
        if(noPermission.length()>0){
            this.noPermission=GsonComponentSerializer.gson().deserialize(noPermission);
        }else {
            this.noPermission=Component.text("");
        }
    }

}
