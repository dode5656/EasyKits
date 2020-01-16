package io.github.dode5656.easykits.utilities;

import io.github.dode5656.easykits.EasyKits;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {
    public String prefix;
    private FileConfiguration messages;

    public MessageManager(EasyKits plugin) {
        messages = plugin.getMessages().read();
        prefix = plugin.getConfig().getString(Message.PREFIX.getMessage()) + " ";
    }

    public final String color(String message) { return ChatColor.translateAlternateColorCodes('&', message); }

    public final String usage(Command cmd) {
        return prefix + cmd.getUsage();
    }

    public final String format(String msg) {
        return prefix + color(msg);
    }

    public final String format(Message msg) {
        return prefix + color(this.messages.getString(msg.getMessage()));
    }

}
