package me.ezjamo.commands;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SwitchInventoryCommand extends Utils implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player p = (Player) sender;
		this.checkOrder();

		if (cmd.getName().equalsIgnoreCase("inv")) {
			if (args.length == 0)
				if (!p.hasPermission("lw.switchinv")) {
					message(p, Messages.prefix + Messages.noPermission);
					return true;
				}
			if (args.length != 1) {
				message(p, "&cUsage: &7Use /inv new to switch to a blank inventory.");
				message(p, "&cUsage: &7Use /inv prev to switch to your previous inventory");
				return true;
			}
		}

		ArrayList<ItemStack> list = new ArrayList<>();
		String playername = p.getName();
		File file = new File(Lonewolves.plugin.getDataFolder() + File.separator + "Inventories" + File.separator + playername + ".yml");

		if (label.equalsIgnoreCase("inv")) {
			if (args.length == 1)
				if (args[0].equalsIgnoreCase("new"))
					if (p.hasPermission("lw.switchinv"))
						if(!file.exists()) {
							try {
								file.createNewFile();
							} catch (IOException e) {
								message(p, "&cThe specified path could not be found.");
							}
							YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
							ItemStack[] contents = p.getInventory().getContents();
							for(int i = 0; i < contents.length; i++) {
								ItemStack item = contents[i];
								if(!(item == null)) {
									list.add(item);
								}
							}
							inv.set("Inventory", list);
							try {
								inv.save(file);
							} catch (IOException e) {
								message(p, "&cThe specified path could not be found.");
							}
							p.getInventory().clear();
							message(p, Messages.prefix + "&fYou have switched to a blank inventory.");
							return true;
						}
			if (!file.exists()) {
				if (args.length == 1 && args[0].equalsIgnoreCase("prev")) {
					message(p, Messages.prefix + "&cYou already have your previous inventory.");
					return true;
				}
			}
			if (file.exists()) {
				if (args.length == 1 && args[0].equalsIgnoreCase("new")) {
					message(p, Messages.prefix + "&cYou already have a blank inventory.");
					return true;
				}
			}
		}
		if (label.equalsIgnoreCase("inv")) {
			if (args.length == 1)
				if (args[0].equalsIgnoreCase("prev"))
					if (p.hasPermission("lw.switchinv"))
						if(file.exists()) {
							YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
							p.getInventory().clear();
							ItemStack[] contents = p.getInventory().getContents();
							List<?> list1 = inv.getList("Inventory");

							for (int i = 0; i  < list1.size(); i++) {
								contents[i] = (ItemStack) list1.get(i);
							}
							p.getInventory().setContents(contents);
							message(p, Messages.prefix + "&fYou have switched to your previous inventory.");
							file.delete();
						}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> inv = new ArrayList<>();
		inv.add("new");
		inv.add("prev");
		List<String> empty = new ArrayList<>();
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], inv, completions);
			return completions;
		}
		return empty;
	}

	public void checkOrder() {
		File file = new File(Lonewolves.plugin.getDataFolder() + File.separator + "Inventories");
		if(!file.exists()) {
			file.mkdir();
		}
	}
}