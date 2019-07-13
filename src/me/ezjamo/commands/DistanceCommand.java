package me.ezjamo.commands;
 
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
 
public class DistanceCommand implements CommandExecutor {
 
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if  (!(sender instanceof Player)) {
        sender.sendMessage("Only players may check their distance from spawn!");
        return true;
    }
   
    Player p = (Player) sender;
   
    Location pLocation = p.getLocation();
    int x2 = pLocation.getBlockX();
    int z2 = pLocation.getBlockZ();
   
    int spawn_x = -0;
    int spawn_y = 0;
   
    int dist = (int) Math.sqrt((Math.pow(x2 - spawn_x, 2)) + (Math.pow(z2 - spawn_y, 2)));
   
    p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD + "Lone" +  ChatColor.DARK_RED + ChatColor.BOLD + "Wolves" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Your distance from spawn is " + ChatColor.DARK_RED + dist + ChatColor.WHITE + " block(s).");
    return false;
}
}