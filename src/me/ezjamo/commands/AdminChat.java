package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class AdminChat implements CommandExecutor
{
    
    public AdminChat() {
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("acc")) {
    		sender.sendMessage(ChatColor.RED + "The feature is currently under maintenance contact EzJamo for updates.");
    	}
		return true;
    }
}
 //   public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
 //       if (!(sender instanceof Player)) {
 //          sender.sendMessage("Only players !");
 //          return true;
 //      }
 //
 //   
 //  final Player player = (Player) sender;
 //      if (cmd.getName().equalsIgnoreCase("ac")) {
 //       		if (player.hasPermission("lw.adminchat")) {
 //           if (ChatManager.getAdminChat().contains(player.getUniqueId())) {
 //               ChatManager.getAdminChat().remove(player.getUniqueId());
 //               player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Admin &cchat disabled"));
 //           }
 //           else {
 //              ChatManager.getStaffChat().add(player.getUniqueId());
 //              player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAdmin &7chat enabled"));
 //           }
 //       }
 //       else {
 //           player.sendMessage(Lonewolves.NO_PERMS);
 //      }
 //       	}
 //		return true;
 //		}
 //}