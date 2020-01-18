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
    private MessageManager messageManager;

    public Kits(EasyKits plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("easykits.use")) {

                KitsGUI gui = new KitsGUI(plugin, player);
                gui.open(player);

            } else {
                player.sendMessage(messageManager.format(Message.NOPERMCMD));
            }
        } else {
            sender.sendMessage(messageManager.format(Message.PLAYERONLY));
        }
        return true;
    }

}
