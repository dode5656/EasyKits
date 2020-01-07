package io.github.dode5656.easykits.utilities;

import io.github.dode5656.easykits.EasyKits;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.Set;

public class KitsGUI implements InventoryHolder, Listener {
    private Inventory inv;
    private EasyKits plugin;

    public KitsGUI(EasyKits plugin, Player player) {
        this.plugin = plugin;
        MessageManager messageManager = new MessageManager(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        ConfigurationSection kits = plugin.getKits().read().getConfigurationSection("kits");
        ConfigurationSection kit;
        Set<String> keys = plugin.getKits().read().getConfigurationSection("kits").getKeys(false);
        inv = Bukkit.createInventory(this, 54, plugin.getConfig().getString("gui.title"));
        int size = keys.size();
        for (int i = 0; i < inv.getSize(); i++) {
            if (i+1 <= size) {
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                ItemMeta meta = item.getItemMeta();
                kit = kits.getConfigurationSection(keys.toArray()[i].toString());
                meta.setDisplayName(kit.getName());
                if (player.hasPermission("easykits.edit")) {
                    meta.setLore(Arrays.asList(messageManager.color("&eRight Click to edit"), messageManager.color("&eLeft Click to claim")));
                }
                item.setItemMeta(meta);
                inv.setItem(i, item);
            } else {
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
                inv.setItem(i, item);
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void open(Player p) {
        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        if (inv.getHolder() != this) return;
        e.setCancelled(true);
        if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE && e.getCurrentItem().getDurability() == (short) 5) {
            if (e.getWhoClicked().hasPermission("easykits.edit") && e.getClick().isRightClick()) {
                KitGUI kitGUI = new KitGUI(plugin, e.getCurrentItem().getItemMeta().getDisplayName());
                e.getWhoClicked().closeInventory();
                kitGUI.open((Player) e.getWhoClicked());
            } else {
                plugin.getServer().dispatchCommand(e.getWhoClicked(), "/kit " + e.getCurrentItem().getItemMeta().getDisplayName());
            }
        }
    }

}
