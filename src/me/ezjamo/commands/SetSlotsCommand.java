package me.ezjamo.commands;


import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.ezjamo.Lonewolves;


public class SetSlotsCommand implements CommandExecutor
{
	
    public boolean onCommand(CommandSender sender,Command command,String alias,String[] args) {
        if (!sender.hasPermission("lw.slots")) {
            sender.sendMessage(Lonewolves.NO_PERMS);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments.");
            return true;
        }
        try {
            this.changeSlots(Integer.parseInt(args[0]));
            sender.sendMessage(ChatColor.GREEN + "Slots updated.");
        }
        catch (NumberFormatException numbererr) {
            sender.sendMessage(ChatColor.RED + "You must provide a valid number.");
        }
        catch (ReflectiveOperationException errorerr) {
            sender.sendMessage(ChatColor.RED + "An unknown error has occurred");
        }
        return true;
    }

    
    public void changeSlots(int slots) throws ReflectiveOperationException {
       Object playerList = Bukkit.getServer().getClass().getDeclaredMethod("getHandle", (Class<?>[])new Class[0]).invoke(Bukkit.getServer(), new Object[0]);
       Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");
        maxPlayersField.setAccessible(true);
        maxPlayersField.set(playerList, slots);
    }
}
