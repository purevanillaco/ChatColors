package co.purevanilla.mcplugins.ChatColorPicker;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {

    static API api;

    @Override
    public void onEnable() {
        super.onEnable();
        try {
            this.api = new API(this);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Handler(api), this);
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new Command(api));
    }
}
