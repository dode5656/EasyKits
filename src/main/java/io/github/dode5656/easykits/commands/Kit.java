package io.github.dode5656.easykits.commands;

import io.github.dode5656.easykits.EasyKits;
import io.github.dode5656.easykits.utilities.Message;
import io.github.dode5656.easykits.utilities.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Set;

public class Kit implements CommandExecutor {
    private EasyKits plugin;

    public Kit(EasyKits plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageManager messageManager = new MessageManager(plugin);
        if (sender.hasPermission("easykits.kit")) {
            if (args.length == 2) {
                if (sender.hasPermission("easykits.kit." + args[0]) && sender.hasPermission("easykits.kit.others")) {
                    if (!Bukkit.getPlayer(args[1]).isOnline()) {
                        sender.sendMessage(messageManager.format(Message.PLAYEROFFLINE).replace("{player}", args[1]));
                        return true;
                    }

                    FileConfiguration kits = plugin.getKits().read();
                    if (kits.get("kits." + args[0]) == null) {
                        sender.sendMessage(messageManager.format(Message.KITINVALID).replace("{kit}", args[0]));
                    }

                    Set<String> items = kits.getConfigurationSection("kits." + args[0] + ".items").getKeys(false);

                    String itemConfig = "kits." + args[0] + ".items.";
                    ItemStack[] itemStacks = new ItemStack[items.size()];
                    int counter = 0;
                    for (String item : items) {
                        ItemStack itemStack = new ItemStack(Material.valueOf(kits.getString(itemConfig + item + ".material")),
                                kits.getInt(itemConfig + item + ".amount"),
                                (short) kits.getInt(itemConfig + item + ".damage"));
                        ItemMeta meta = itemStack.getItemMeta();
                        meta.setDisplayName(messageManager.color(kits.getString(itemConfig + item + ".name")));
                        if (!kits.getStringList(itemConfig + item + ".lore").isEmpty()) {
                            List<String> lores = kits.getStringList(itemConfig + item + ".lore");
                            lores.replaceAll(messageManager::color);
                            meta.setLore(lores);
                        }
                        if (!kits.getStringList(itemConfig + item + ".enchants").isEmpty()) {
                            for (String s : kits.getStringList(itemConfig + item + ".enchants")) {
                                String[] enchantMeta = s.split(":");
                                meta.addEnchant(Enchantment.getByName(enchantMeta[0]),
                                        Integer.parseInt(enchantMeta[1] == null ? "1" : enchantMeta[1]), false);
                            }
                        }
                        itemStack.setItemMeta(meta);
                        itemStacks[counter] = itemStack;
                        counter++;
                    }
                    try {
                        Bukkit.getPlayer(args[1]).getInventory().addItem(itemStacks);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(messageManager.format(Message.NOENOUGHSPACE));
                    }

                } else {
                    sender.sendMessage(messageManager.format(Message.KITOTHERSNOPERM));
                }
                return true;
            }
            if (args.length == 1) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("easykits.kit." + args[0])) {
                        FileConfiguration kits = plugin.getKits().read();
                        if (kits.get("kits." + args[0]) == null) {
                            sender.sendMessage(messageManager.format(args[0] + " kit is invalid!"));
                        }

                        Set<String> items = kits.getConfigurationSection("kits." + args[0] + ".items").getKeys(false);

                        String itemConfig = "kits." + args[0] + ".items.";
                        ItemStack[] itemStacks = new ItemStack[items.size()];
                        int counter = 0;
                        for (String item : items) {
                            ItemStack itemStack = new ItemStack(Material.valueOf(kits.getString(itemConfig + item + ".material")),
                                    kits.getInt(itemConfig + item + ".amount"),
                                    (short) kits.getInt(itemConfig + item + ".damage"));
                            ItemMeta meta = itemStack.getItemMeta();
                            meta.setDisplayName(messageManager.color(kits.getString(itemConfig + item + ".name")));
                            if (!kits.getStringList(itemConfig + item + ".lore").isEmpty()) {
                                List<String> lores = kits.getStringList(itemConfig + item + ".lore");
                                lores.replaceAll(messageManager::color);
                                meta.setLore(lores);
                            }
                            if (!kits.getStringList(itemConfig + item + ".enchants").isEmpty()) {
                                for (String s : kits.getStringList(itemConfig + item + ".enchants")) {
                                    String[] enchantMeta = s.split(":");
                                    meta.addEnchant(Enchantment.getByName(enchantMeta[0]),
                                            Integer.parseInt(enchantMeta[1] == null ? "1" : enchantMeta[1]), false);
                                }
                            }
                            itemStack.setItemMeta(meta);
                            itemStacks[counter] = itemStack;
                            counter++;
                        }
                        try {
                            ((Player) sender).getInventory().addItem(itemStacks);
                        } catch (IllegalArgumentException e) {
                            sender.sendMessage(messageManager.format(Message.NOENOUGHSPACE));
                        }
                    } else {
                        sender.sendMessage(messageManager.format(Message.NOPERMKIT).replace("{kit}", args[0]));
                    }
                } else {
                    sender.sendMessage(messageManager.format(Message.PLAYERONLY));
                }
            }
        } else {
            sender.sendMessage(messageManager.format(Message.NOPERMCMD));
        }

        if (args.length == 0) {
            sender.sendMessage(messageManager.usage(command));
        }

        return true;
    }

}
