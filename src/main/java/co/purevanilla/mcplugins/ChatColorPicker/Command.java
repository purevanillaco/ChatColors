package co.purevanilla.mcplugins.ChatColorPicker;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class Command extends BaseCommand {

    API api;

    public Command(API api){
        this.api=api;
    }

    @Default
    @CommandAlias("cc|chatcolor")
    @CommandPermission("chatcolorpicker.use")
    @CommandCompletion("pink-ish|green-ish|purple-ish|blue-ish|orange-ish|reset")
    public void onChange(Player player, String[] args) throws IOException {
        if(args.length==0){
            api.setColor(player,null);
            player.sendMessage(new ComponentBuilder().append("CC").color(ChatColor.GRAY).bold(true).append(" disabled").color(ChatColor.WHITE).bold(false).create().);
        } else {
            if(args[0].equalsIgnoreCase("reset")){
                api.setColor(player,null);
                player.sendMessage("disabled");
            } else {
                List<Color> colorList = this.api.getColors();
                for (int i = 0; i < colorList.size(); i++) {
                    Color color = colorList.get(i);
                    if(color.name.equalsIgnoreCase(args[0])){
                        api.setColor(player,color);
                        player.sendMessage("changed to "+color.name);
                        return;
                    }
                }
                player.sendMessage("unknown color");
            }
        }
    }
}
