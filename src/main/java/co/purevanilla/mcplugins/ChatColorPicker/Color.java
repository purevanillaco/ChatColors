package co.purevanilla.mcplugins.ChatColorPicker;

import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;

public class Color {

    public String name;
    public TextColor color;

    public Color(String name, String code){
        this.name=name;
        this.color=TextColor.fromHexString(code);
    }

    public Color(String name, TextColor color){
        this.name=name;
        this.color=color;
    }

}
