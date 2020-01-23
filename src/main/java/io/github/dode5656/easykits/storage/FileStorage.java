package io.github.dode5656.easykits.storage;

import io.github.dode5656.easykits.EasyKits;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStorage {
    private File file;
    private YamlConfiguration fileStorage;

    public FileStorage(String name, File location) {
        this.file = new File(location, name);
    }

    public final void save(EasyKits main) {
        Logger logger = main.getLogger();
        try {
            fileStorage.save(file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not save " + file.getName() + " file!", e);
        }
    }

    public final FileConfiguration read() {
        return fileStorage;
    }

    public final void reload() {
        this.fileStorage = YamlConfiguration.loadConfiguration(this.file);
    }

    public final void saveDefaults(EasyKits main) {
        if (this.file.exists()) {
            reload();
            return;
        }
        main.saveResource(this.file.getName(), false);
        reload();
    }

}