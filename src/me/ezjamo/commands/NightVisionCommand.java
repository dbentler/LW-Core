package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import net.md_5.bungee.api.ChatColor;

public class NightVisionCommand implements CommandExecutor {


public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     Player p = (Player) sender;
    if (cmd.getName().equalsIgnoreCase("nv") && sender instanceof Player) {
        if (!p.hasPermission("lw.nv")) {
            p.sendMessage(Messages.prefix + Messages.noPermission);
            return true;
        }
        if (args.length == 0) {
            if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            	for (String message : Lonewolves.plugin.getConfig().getStringList("NightVision Off")) {
            	p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                return true;
            }
            }
            for (String message : Lonewolves.plugin.getConfig().getStringList("NightVision On")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9000000, 2));
            return true;
        }
        }
    	}
    return true;
	}
}