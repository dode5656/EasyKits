package io.github.dode5656.easykits;

import io.github.dode5656.easykits.commands.Kit;
import io.github.dode5656.easykits.commands.Kits;
import io.github.dode5656.easykits.storage.FileStorage;
import io.github.dode5656.easykits.utilities.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class EasyKits extends JavaPlugin {
    private final Logger logger = getLogger();
    private FileStorage kits;
    private FileStorage messages;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        logger.info("----------------------");
        logger.info(" EasyKits is enabled");
        logger.info("----------------------");
        getCommand("kits").setExecutor(new Kits(this));
        getCommand("kit").setExecutor(new Kit(this));
        saveDefaultConfig();
        kits = new FileStorage("kits.yml", new File(getDataFolder().getPath()));
        messages = new FileStorage("messages.yml", new File(getDataFolder().getPath()));
        messages.saveDefaults(this);
        kits.saveDefaults(this);
        this.messageManager = new MessageManager(this);
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
    public FileStorage getMessages() { return messages; }
    public MessageManager getMessageManager() { return messageManager; }

}
