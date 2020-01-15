package io.github.dode5656.easykits.storage;

import io.github.dode5656.easykits.EasyKits;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FileStorage {
    private File file;
    private YamlConfiguration fileStorage;

    public FileStorage(String name, File location) {
        this.file = new File(location, name);
    }

    public void save(EasyKits main) {
        Logger logger = main.getLogger();
        try {
            fileStorage.save(file);
        } catch (IOException e) {
            logger.severe("Could not save " + file.getName() + " file!");
            logger.severe(e.toString());
        }
    }

    public FileConfiguration read() {
        return fileStorage;
    }

    public void reload() {
        this.fileStorage = YamlConfiguration.loadConfiguration(this.file);
    }

    public void saveDefaults(EasyKits main) {
        if (this.file.exists()) {
            reload();
            return;
        }
        main.saveResource(this.file.getName(), false);
        reload();
    }

}