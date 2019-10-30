package me.ezjamo.managers;

import me.ezjamo.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StaffManager extends Utils implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        String title = event.getInventory().getTitle();
        if (title.equals(color("&f&lLone&4&lWolves &f&lStaff"))) {
            event.setCancelled(true);
        }
    }
}
