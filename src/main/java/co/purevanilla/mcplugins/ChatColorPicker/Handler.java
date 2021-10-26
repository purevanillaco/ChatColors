package co.purevanilla.mcplugins.ChatColorPicker;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class Handler implements Listener {

    API api;

    public Handler(API api){
        this.api=api;
    }

    @EventHandler
    public void onChat(AsyncChatEvent ev){
        Color color = api.getColor(ev.getPlayer());
        @NotNull Component component = ev.originalMessage();
        if(color!=null && color.name != null){
            ev.message(component.color(color.color));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent ev){
        API.removeFromCache(ev.getPlayer());
    }


}
