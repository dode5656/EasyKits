package io.github.dode5656.easykits.commands;

import io.github.dode5656.easykits.EasyKits;
import io.github.dode5656.easykits.utilities.KitsGUI;
import io.github.dode5656.easykits.utilities.Message;
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
        MessageManager messageManager = plugin.getMessageManager();

        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.format(Message.PLAYERONLY));
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("easykits.use")) {
            player.sendMessage(messageManager.format(Message.NOPERMCMD));
        }

        KitsGUI gui = new KitsGUI(plugin, player);
        gui.open(player);

        return true;
    }

}
