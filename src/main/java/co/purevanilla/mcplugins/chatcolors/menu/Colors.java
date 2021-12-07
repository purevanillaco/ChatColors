package co.purevanilla.mcplugins.chatcolors.menu;

import co.purevanilla.mcplugins.chatcolors.ChatColors;
import co.purevanilla.mcplugins.chatcolors.api.Color;
import co.purevanilla.mcplugins.chatcolors.api.DisplaySettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Colors implements Listener {

    private final Inventory inv;
    private final Player player;

    public Colors(Player player){
        this.player=player;
        inv = Bukkit.createInventory(player, InventoryType.CHEST,Component.text("Chat Colors"));

        loadColors();
    }

    private void loadColors(){

        DisplaySettings display = ChatColors.getDisplay();

        for (int i = 0; i < ChatColors.getColors().size(); i++) {
            Color color = ChatColors.getColors().get(i);
            ItemStack colorItem = new ItemStack(player.hasPermission("chatcolor.color."+color.permission) ? display.hasPermission : display.noPermission);
            colorItem.setAmount(1);
            ItemMeta meta = colorItem.getItemMeta();
            meta.displayName(Component.text(color.name).color(TextColor.fromHexString(color.hex)));

            if(!player.hasPermission("chatcolor.color."+color.permission)){
                List<Component> componentList = new ArrayList<>();
                componentList.add(color.noPermission);
                meta.lore(componentList);
            }

            colorItem.setItemMeta(meta);

            inv.setItem(i, colorItem);
        }

    }

    public void open(){
        this.player.openInventory(this.inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) throws IOException {
        if(event.getInventory()!=this.inv) return;

        // prevent shift-clicking items
        event.setCancelled(true);

        if(event.getCursor() != null && event.getSlot()<ChatColors.getColors().size()){
            Color color = ChatColors.getColors().get(event.getSlot());
            if(color.name.equalsIgnoreCase("default")){
                ChatColors.getSettings().resetPlayer(this.player.getUniqueId());
            } else if(player.hasPermission("chatcolor.color"+color.permission)) {
                ChatColors.getSettings().setPlayer(this.player.getUniqueId(),color);
            } else {
                player.sendMessage(color.noPermission);
            }
            event.getInventory().close();
        }


    }


    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            // prevent moving items
            e.setCancelled(true);
        }
    }


}
