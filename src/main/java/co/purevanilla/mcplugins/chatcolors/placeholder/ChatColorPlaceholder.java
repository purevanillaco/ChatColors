package co.purevanilla.mcplugins.chatcolors.placeholder;

import co.purevanilla.mcplugins.chatcolors.ChatColors;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class ChatColorPlaceholder extends PlaceholderExpansion {

    public ChatColorPlaceholder() { }

    @Override
    public String getAuthor() {
        return "quiquelhappy";
    }

    @Override
    public String getIdentifier() {
        return "cc";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        return ChatColors.getSettings().getPlayer(player.getUniqueId()).color.hex;
    }
}