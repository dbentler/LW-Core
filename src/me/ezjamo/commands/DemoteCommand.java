package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.Utils;

public class DemoteCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			message(sender, "&7(&c!&7) &cOnly console can execute this command!");
			return true;
		}
		if (!sender.hasPermission("lw.demote")) {
			message(sender, Messages.prefix + Messages.noPermission);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("demote")) {
			if (args.length == 0) {
				message(sender, "&cUsage: &7/demote <player>");
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + args[0] + " parent remove helper");
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + args[0] + " parent remove mod");
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + args[0] + " parent remove admin");
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + args[0] + " parent remove owner");
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + args[0] + " permission unset *");
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + args[0] + " permission unset admin.on");
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "deop " + args[0]);
					message(sender, "&cDemoted &f" + args[0] + "&c.");
					return true;
				}
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "sudo " + target.getName() + " inv prev");
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " parent remove helper");
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " parent remove mod");
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " parent remove admin");
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " parent remove owner");
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " permission unset *");
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " permission unset admin.on");
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "deop " + target.getName());
				Modmode.modmode.remove(target.getName());
				target.setGameMode(GameMode.SURVIVAL);
				message(sender, "&cDemoted &f" + target.getName() + "&c.");
			}
			else {
				message(sender, "&cInvalid usage.");
				return true;
			}
		}
		return true;
	}
}
