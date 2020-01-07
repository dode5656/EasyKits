package io.github.dode5656.easykits;

import io.github.dode5656.easykits.commands.Kits;
import io.github.dode5656.easykits.storage.FileStorage;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class EasyKits extends JavaPlugin {
    final Logger logger = getLogger();
    final PluginManager pluginManager = getServer().getPluginManager();
    FileStorage kits;

    @Override
    public void onEnable() {
        logger.info("----------------------");
        logger.info(" EasyKits is enabled");
        logger.info("----------------------");
        getCommand("kits").setExecutor(new Kits(this));
        saveDefaultConfig();
        kits = new FileStorage("kits.yml", new File(getDataFolder().getPath()));
        kits.saveDefaults(this);
    }

    @Override
    public void onDisable() {
        logger.info("----------------------");
        logger.info(" EasyKits is disabled");
        logger.info("----------------------");
    }

    public FileStorage getKits() {
        return kits;
    }
}
