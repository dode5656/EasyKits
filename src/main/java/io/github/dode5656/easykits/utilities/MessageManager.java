package io.github.dode5656.easykits.utilities;

import io.github.dode5656.easykits.EasyKits;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

public class MessageManager {
    Plugin plugin;
    public String prefix;

    public MessageManager(EasyKits plugin) {
        this.plugin = plugin;
        prefix = this.plugin.getConfig().getString(Message.PREFIX.getMessage()) + " ";
    }

    public final String color(String message) { return ChatColor.translateAlternateColorCodes('&', message); }

    public final String usage(Command cmd) {
        return prefix + cmd.getUsage();
    }

    public final String format(String msg) {
        return prefix + color(msg);
    }

    public final String format(Message msg) {
        System.out.println(this.plugin.getConfig().getString(msg.getMessage()));
        return prefix + color(this.plugin != null ? this.plugin.getConfig().getString(msg.getMessage()) : "");
    }

}
