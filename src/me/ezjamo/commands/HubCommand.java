package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;


public class HubCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("hub")) {
			String hub = Lonewolves.plugin.getConfig().getString("hub-server");
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF(hub);
			p.sendPluginMessage(Lonewolves.plugin, "BungeeCord", out.toByteArray());
		}
		return false;
	}
}
