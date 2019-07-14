package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class KothCommand implements CommandExecutor {
	
    public static void koth(Player p) {

    	Inventory koth = Bukkit.getServer().createInventory(null, 27, Utils.chat("&fLone&4Wolves &fKoths"));
    	Utils.createItem(koth, 399, 1, 0, "&dCrow Koth", "&fStart &dCrow &fkoth for 5 minutes.");
    	Utils.createItem(koth, 399, 1, 1, "&dGoliath Koth", "&fStart &dGoliath &fkoth for 5 minutes.");
        p.openInventory(koth);

   }

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	Player player = (Player) sender;
    	if (label.equalsIgnoreCase("startkoth")) {
    	if (player.hasPermission("lw.koth"))
        	KothCommand.koth(player);
    	}
    	if(!player.hasPermission("lw.koth"))
    		player.sendMessage(Lonewolves.NO_PERMS);
		return false;
    }
}
