package co.purevanilla.mcplugins.chatcolors.listener;

import co.purevanilla.mcplugins.chatcolors.ChatColors;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        ChatColors.getSettings().deleteCache(event.getPlayer().getUniqueId());
    }

}
