package me.ezjamo.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;
import me.ezjamo.managers.WarpManager;

public class WarpCommand extends Utils implements CommandExecutor {
	public static Map<Player, BukkitTask> tasks = new HashMap<>();
	private Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		WarpManager warps = WarpManager.getManager();
		if (cmd.getName().equalsIgnoreCase("warp")) {
			if (!player.hasPermission("lw.warp")) {
				message(player, Messages.prefix + Messages.noPermission);
				return true;
			}
			if (args.length == 0) {
				if (warps.get().getConfigurationSection("Warps") == null || warps.get().getConfigurationSection("Warps").getKeys(false).isEmpty()) {
					message(player, Messages.prefix + "&cThere are no warps available.");
					return true;
				}
				Set<String> warpList = warps.get().getConfigurationSection("Warps").getKeys(false);
				message(player, "&cWarps: &f" + warpList.toString().replace("[", "").replace("]", ""));
				return true;
			}
			if (args.length == 1) {
				if (warps.get().getConfigurationSection("Warps." + args[0].toLowerCase()) == null) {
					message(player, "&cWarp &f" + args[0].toLowerCase() + " &cdoes not exist.");
					return true;
				}
				World w = Bukkit.getWorld(warps.get().getString("Warps." + args[0].toLowerCase() + ".world"));
				double x = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".x");
				double y = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".y");
				double z = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".z");
				float yaw = (float)warps.get().getDouble("Warps." + args[0].toLowerCase() + ".yaw");
				float pitch = (float)warps.get().getDouble("Warps." + args[0].toLowerCase() + ".pitch");
				Location warpLoc = new Location(w, x, y, z, yaw, pitch);
				if (Lonewolves.plugin.getConfig().getBoolean("per-warp-permissions")) {
					if (player.hasPermission("lw.warp." + args[0])) {
						if (player.hasPermission("lw.bypass.teleportdelay")) {
							if (ess != null) {
								User user = ess.getUser(player);
								user.setLastLocation();
							}
							player.teleport(warpLoc);
							message(player, Messages.prefix + "&fTeleportation Successful!");
							return true;
						}
						message(player, Messages.prefix + "&fYou will be teleported in &c" + Lonewolves.plugin.getConfig().getInt("warp-delay") + " &fseconds.");
						if (!tasks.containsKey(player)) {
							tasks.put(player, new BukkitRunnable() {
								public void run() {
									if (ess != null) {
										User user = ess.getUser(player);
										user.setLastLocation();
									}
									player.teleport(warpLoc);
									message(player, Messages.prefix + "&fTeleportation Successful!");
									tasks.remove(player);
								}
							}.runTaskLater(Lonewolves.plugin, 20L * Lonewolves.plugin.getConfig().getInt("warp-delay")));
							return true;
						}
					}
					else {
						message(player, Messages.prefix + Messages.noPermission);
						return true;
					}
				}
				if (!Lonewolves.plugin.getConfig().getBoolean("per-warp-permissions")) {
					if (player.hasPermission("lw.bypass.teleportdelay")) {
						if (ess != null) {
							User user = ess.getUser(player);
							user.setLastLocation();
						}
						player.teleport(warpLoc);
						message(player, Messages.prefix + "&fTeleportation Successful!");
						return true;
					}
					message(player, Messages.prefix + "&fYou will be teleported in &c" + Lonewolves.plugin.getConfig().getInt("warp-delay") + " &fseconds.");
					if (!tasks.containsKey(player)) {
						tasks.put(player, new BukkitRunnable() {
							public void run() {
								if (ess != null) {
									User user = ess.getUser(player);
									user.setLastLocation();
								}
								player.teleport(warpLoc);
								message(player, Messages.prefix + "&fTeleportation Successful!");
								tasks.remove(player);
							}
						}.runTaskLater(Lonewolves.plugin, 20L * Lonewolves.plugin.getConfig().getInt("warp-delay")));
						return true;
					}
				}
			}
			if (args.length == 2) {
				if (!player.hasPermission("lw.warp.others")) {
					message(player, Messages.prefix + Messages.noPermission);
					return true;
				}
				if (warps.get().getConfigurationSection("Warps." + args[0].toLowerCase()) == null) {
					message(player, "&cWarp &f" + args[0].toLowerCase() + " &cdoes not exist.");
					return true;
				}
				World w = Bukkit.getWorld(warps.get().getString("Warps." + args[0].toLowerCase() + ".world"));
				double x = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".x");
				double y = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".y");
				double z = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".z");
				float yaw = (float)warps.get().getDouble("Warps." + args[0].toLowerCase() + ".yaw");
				float pitch = (float)warps.get().getDouble("Warps." + args[0].toLowerCase() + ".pitch");
				Location warpLoc = new Location(w, x, y, z, yaw, pitch);
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					message(player, "&cPlayer not found.");
					return true;
				}
				if (ess != null) {
					User user2 = ess.getUser(target);
					user2.setLastLocation();
				}
				target.teleport(warpLoc);
				message(player, Messages.prefix + "&fTeleportation Successful!");
				message(player, Messages.prefix + "&fTeleportation Successful!");
			}
		}
		return true;
	}
}
