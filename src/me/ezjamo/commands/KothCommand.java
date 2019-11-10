package me.ezjamo.commands;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import subside.plugins.koth.KothPlugin;
import subside.plugins.koth.areas.Koth;

public class KothCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("lw.koth")) {
			message(player, Messages.prefix + Messages.noPermission);
			return true;
		}
		koth(player);
		return true;
	}

	public void koth(Player p) {
		Plugin plugin = Lonewolves.plugin.getServer().getPluginManager().getPlugin("KoTH");
		KothPlugin kothPlugin = (KothPlugin) plugin;
		Utils utils = new Utils();
		int slot = 0;
		Inventory inv = Bukkit.getServer().createInventory(null, 27, utils.color(Messages.prefix.replace("[", "").replace("]", "") + "&fKoths"));
		for (Koth koths : kothPlugin.getKothHandler().getAvailableKoths()) {
			String koth = koths.getName();
			ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(color("&d" + koth));
			item.setItemMeta(meta);
			++slot;
			inv.setItem(slot - 1, item);
		}
		p.openInventory(inv);
	}
}
