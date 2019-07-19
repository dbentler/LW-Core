package me.ezjamo.commands;
 
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

 
public class SpawnCommand implements CommandExecutor {
     
    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("spawn")) {
        	p.performCommand("warp spawn");
        }
		return true;
    }
}