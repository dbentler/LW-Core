package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.Utils;
import me.ezjamo.managers.WarpManager;

public class DelWarpCommand extends Utils implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        WarpManager warps = WarpManager.getManager();
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
}
