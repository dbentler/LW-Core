package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;

public class NightVisionCommand extends Utils implements CommandExecutor {


public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     Player p = (Player) sender;
    if (cmd.getName().equalsIgnoreCase("nv")) {
        if (!p.hasPermission("lw.nv")) {
            p.sendMessage(Messages.prefix + Messages.noPermission);
            return true;
        }
        if (args.length < 1) {
        	if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
        		String disabled = Lonewolves.plugin.getConfig().getString("NightVision.Disabled");
        		message(p, Messages.prefix + disabled);
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                return true;
        	}
        	else {
        		String enabled = Lonewolves.plugin.getConfig().getString("NightVision.Enabled");
        		message(p, Messages.prefix + enabled);
    			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9000000, 2));
    			return true;
        	}
		}
    }
    return true;
	}
}
