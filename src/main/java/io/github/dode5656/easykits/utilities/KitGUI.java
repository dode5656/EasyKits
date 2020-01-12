package io.github.dode5656.easykits.utilities;

import io.github.dode5656.easykits.EasyKits;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KitGUI implements InventoryHolder, Listener {
    private Inventory inv;
    private EasyKits plugin;
    private MessageManager messageManager;
    private String name;
    private FileConfiguration kits;

    public KitGUI(EasyKits plugin, String kit) {
        this.messageManager = new MessageManager(plugin);
        this.plugin = plugin;
        this.name = kit;
        this.kits = plugin.getKits().read();
        Set<String> items = kits.getConfigurationSection("kits." + kit + ".items").getKeys(false);
        inv = Bukkit.createInventory(this, 36, kit + " Kit Editor");
        String itemConfig = "kits." + kit + ".items.";
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
            inv.setItem(counter, itemStack);
            counter++;
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
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        if (inv.getHolder() != this) return;
        int counter = 1;

        ItemStack[] items = inv.getContents();
        for (ItemStack item : items) {
            //TODO: complete
            ItemMeta meta = item.getItemMeta();
            List<String> enchants = new ArrayList<>();
            meta.getEnchants().forEach(((enchantment, integer) -> enchants.add(enchantment.getName() + ":" + integer)));
            String kitItems = "kits." + name + ".items."; 
            if (kits.get( + counter + "name") != null) {

                kits.set(kitItems + counter + "name", meta.getDisplayName());

            } else if (kits.get(kitItems + counter + "material") != null) {

                kits.set(kitItems + counter + "material", item.getType().toString());

            } else if (kits.get(kitItems + counter + "lore") != null) {

                kits.set(kitItems + counter + "lore", meta.getLore());

            } else if (kits.get(kitItems + counter + "amount") != null) {

                kits.set(kitItems + counter + "amount", item.getAmount());

            } else if (kits.get(kitItems + counter + "damage") != null) {

                kits.set(kitItems + counter + "damage", item.getDurability());

            } else if (kits.get(kitItems + counter + "enchants") != null) {

                kits.set(kitItems + counter + "enchants", enchants);
                
            }
            counter++;
        }

        try {
            plugin.getKits().reload();
            e.getPlayer().sendMessage(messageManager.format(Message.KITSAVEDSUCCESSFULLY).replace("{kit}", name));
        } catch (Exception ex){
            plugin.getLogger().severe(ex.toString());
        }

    }

}
