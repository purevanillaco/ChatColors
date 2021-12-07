package co.purevanilla.mcplugins.chatcolors;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.purevanilla.mcplugins.chatcolors.menu.Colors;
import org.bukkit.entity.Player;

@CommandAlias("chatcolors|cc|chatcolor")
public class CMD extends BaseCommand {
    @Default
    @CommandPermission("chatcolor.menu")
    @Description("Shows you the list of available chat colors")
    public static void onList(Player player, String[] args) {
        Colors colorMenu = new Colors(player);
        ChatColors.registerListener(colorMenu);
        colorMenu.open();
    }
}
