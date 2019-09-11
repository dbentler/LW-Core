package me.ezjamo.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.ezjamo.Messages;

public class SwitchInventoryCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		this.checkOrder();
		
		if (cmd.getName().equalsIgnoreCase("inv")) {
			if (args.length == 0)
	        if (!p.hasPermission("lw.switchinv")) {
	            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + Messages.noPermission));
	            return true;
	        }
	        if (!(p instanceof Player)) {
	            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer only command."));
	            return true;
	        }
	        if (args.length == 0) {
	            p.sendMessage(ChatColor.RED + "Usage: Use /inv new to switch to a blank inventory.");
	            p.sendMessage(ChatColor.RED + "Usage: Use /inv prev to switch to your previous inventory");
	            return true;
	        }
		}
		
		ArrayList<ItemStack> list = new ArrayList<>();
		String playername = p.getName();
		File file = new File("plugins//LW-Core//Inventories//" + playername + ".yml");
		
		if (label.equalsIgnoreCase("inv")) {
			if (args.length == 1)
			if (args[0].equalsIgnoreCase("new"))
				if (p.hasPermission("lw.switchinv"))
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				p.sendMessage("The specified path could not be found.");
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
				p.sendMessage("The specified path could not be found.");
			}
			p.getInventory().clear();
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + "&fYou have switched to a blank inventory."));
					
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
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + "&fYou have switched to your previous inventory."));
			file.delete();
		}
		}
		return true;
}

	public void checkOrder() {
		File file = new File("plugins//LW-Core//Inventories");
		if(!file.exists()) {
			file.mkdir();
		}
	}
}