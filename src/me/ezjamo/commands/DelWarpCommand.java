package me.ezjamo.commands;

import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.managers.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DelWarpCommand extends Utils implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	WarpManager warps = WarpManager.getManager();
    	if (!(sender instanceof Player)) {
    		if (args.length == 0) {
    			message(sender, "&cUsage: &7/delwarp <warp>");
    			return true;
    		}
    		if (args.length == 1) {
                if (warps.get().getConfigurationSection("Warps." + args[0].toLowerCase()) == null) {
                	message(sender, "&cWarp &f" + args[0].toLowerCase() + " &cdoes not exist.");
                    return true;
                }
                warps.get().set("Warps." + args[0], null);
                warps.save();
                message(sender, Messages.prefix + "&cRemoved warp &f" + args[0].toLowerCase());
            }
    		else {
    			message(sender, "&cUsage: &7/delwarp <warp>");
            }
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("delwarp")) {
            if (!player.hasPermission("lw.delwarp")) {
            	message(player, Messages.prefix + "&cYou do not have permission to do this.");
                return true;
            }
            if (args.length == 0) {
            	message(player, "&cUsage: &7/delwarp <name>");
                return true;
            }
            if (args.length == 1) {
                if (warps.get().getConfigurationSection("Warps." + args[0].toLowerCase()) == null) {
                	message(player, "&cWarp &f" + args[0].toLowerCase() + " &cdoes not exist.");
                    return true;
                }
                warps.get().set("Warps." + args[0], null);
                warps.save();
                message(player, Messages.prefix + "&cRemoved warp &f" + args[0].toLowerCase());
            }
        }
        return true;
    }
    
    @Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> warps = new ArrayList<>(WarpManager.getManager().get().getConfigurationSection("Warps").getKeys(false));
		List<String> empty = new ArrayList<>();
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], warps, completions);
			return completions;
		}
		return empty;
	}
}
