package me.ezjamo.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
import net.minecraft.server.v1_8_R3.EntityPlayer;

 
public class PingCommand implements CommandExecutor {

	
	public int getPing(Player p) { CraftPlayer cp = (CraftPlayer) p; EntityPlayer ep = cp.getHandle(); return ep.ping; }
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (args.length < 1)
			for (final String message : Lonewolves.plugin.getConfig().getStringList("Ping")) {
	        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("%ping%", Integer.toString(getPing(p)))));
			}
            else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found"));
                }
                else
                	if (target != null) {
                		for (final String message : Lonewolves.plugin.getConfig().getStringList("Ping Others")) {
                			p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("%target%", target.getPlayer().getName()).replaceAll("%ping%", Integer.toString(getPing(p)))));
                			}
                	}
            }			
		}
		return true;
	}
}
