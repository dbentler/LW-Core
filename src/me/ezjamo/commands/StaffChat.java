package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.managers.ChatManager;

public class StaffChat implements CommandExecutor {
    
    public StaffChat() {
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players!");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("sc")) {
        	if (player.hasPermission("lw.staffchat")) {
        		if (ChatManager.getStaffChat().contains(player.getUniqueId())) {
        			ChatManager.getStaffChat().remove(player.getUniqueId());
        			player.sendMessage(Messages.staffChatDisabled);
        		}
        		else {
        			ChatManager.getStaffChat().add(player.getUniqueId());
        			player.sendMessage(Messages.staffChatEnabled);
        		}
        	}
        	else {
        		player.sendMessage(Messages.prefix + Messages.noPermission);
        	}
        }
		return true;
    }
}
