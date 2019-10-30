package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.managers.WarpManager;

public class SetWarpCommand extends Utils implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        WarpManager warps = WarpManager.getManager();
        if (cmd.getName().equalsIgnoreCase("setwarp")) {
            if (!player.hasPermission("lw.setwarp")) {
            	message(player, Messages.prefix + "&cYou do not have permission to do this.");
                return true;
            }
            if (args.length == 0) {
                message(player, "&cUsage: &7/setwarp <name>");
                return true;
            }
            if (args.length == 1) {
                warps.get().set("Warps." + args[0].toLowerCase() + ".world", player.getLocation().getWorld().getName());
                warps.get().set("Warps." + args[0].toLowerCase() + ".x", player.getLocation().getX());
                warps.get().set("Warps." + args[0].toLowerCase() + ".y", player.getLocation().getY());
                warps.get().set("Warps." + args[0].toLowerCase() + ".z", player.getLocation().getZ());
                warps.get().set("Warps." + args[0].toLowerCase() + ".yaw", player.getLocation().getYaw());
                warps.get().set("Warps." + args[0].toLowerCase() + ".pitch", player.getLocation().getPitch());
                warps.save();
                message(player, Messages.prefix + "&aWarp &f" + args[0].toLowerCase() + " &aset.");
            }
        }
        return true;
    }
}
