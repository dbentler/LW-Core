package me.ezjamo.managers;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantmentManager implements Listener {

    @EventHandler
    public void onEnchant(InventoryOpenEvent event) {
        Inventory inv = event.getInventory();
        if (inv.getType() == InventoryType.ENCHANTING) {
            inv.setItem(1, new ItemStack(Material.INK_SACK, 64, (short) 4));
        }
    }

    @EventHandler
    public void onEnchantClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if (inv.getType() == InventoryType.ENCHANTING) {
            if (event.getRawSlot() == 1) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnchantClose(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        if (inv.getType() == InventoryType.ENCHANTING) {
            inv.setItem(1, new ItemStack(Material.AIR));
        }
    }
}
