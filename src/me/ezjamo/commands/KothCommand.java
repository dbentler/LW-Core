package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.ezjamo.Messages;
import me.ezjamo.Utils;

public class KothCommand extends Utils implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("startkoth")) {
			if (player.hasPermission("lw.koth"))
				KothCommand.koth(player);
			if(!player.hasPermission("lw.koth"))
				message(player, Messages.prefix + Messages.noPermission);
		}
		return true;
	}

	public static void koth(Player p) {
		Inventory koth = Bukkit.getServer().createInventory(null, 27, color("&fLone&4Wolves &fKoths"));
		Utils.createItem(koth, 399, 1, 0, "&dCrow Koth", "&fStart &dCrow &fkoth for 5 minutes.");
		Utils.createItem(koth, 399, 1, 1, "&dGoliath Koth", "&fStart &dGoliath &fkoth for 5 minutes.");
		Utils.createItem(koth, 399, 1, 2, "&dTriumph Koth", "&fStart &dTriumph &fkoth for 5 minutes.");
		Utils.createItem(koth, 399, 1, 3, "&dEnd Koth", "&fStart &dEnd &fkoth for 5 minutes.");
		Utils.createItem(koth, 399, 1, 4, "&dMines Koth", "&fStart &dMines &fkoth for 5 minutes.");
		p.openInventory(koth);
	}
}
