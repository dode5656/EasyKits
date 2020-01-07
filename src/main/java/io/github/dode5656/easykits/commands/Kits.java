package io.github.dode5656.easykits.commands;

import io.github.dode5656.easykits.EasyKits;
import io.github.dode5656.easykits.utilities.KitsGUI;
import io.github.dode5656.easykits.utilities.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kits implements CommandExecutor {
    private EasyKits plugin;

    public Kits(EasyKits plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageManager messageManager = new MessageManager(plugin);

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("easykits.kits")) {

                KitsGUI gui = new KitsGUI(plugin, player);
                gui.open(player);

            } else {
                player.sendMessage(messageManager.format("You don't have permission to use that command."));
            }
        } else {
            sender.sendMessage("This command is only for Players.");
        }
        return true;
    }

}
