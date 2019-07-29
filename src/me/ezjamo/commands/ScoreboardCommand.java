package me.ezjamo.commands;


import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import me.ezjamo.Lonewolves;
import me.ezjamo.ScoreboardAdapter;

public class ScoreboardCommand implements CommandExecutor {
	public static ArrayList<String> scoreboard;
	
	static {
        ScoreboardCommand.scoreboard = new ArrayList<String>();
        
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("scoreboard")) {
			if (args.length < 1) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: &7/sb toggle"));
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("toggle")) {
					if (!ScoreboardCommand.scoreboard.contains(player.getName())) {
						ScoreboardCommand.scoreboard.add(player.getName());
						player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fScoreboard has been turned &coff&f."));
						return true;
				}
					if (ScoreboardCommand.scoreboard.contains(player.getName())) {
						ScoreboardCommand.scoreboard.remove(player.getName());
						for (Player all : Bukkit.getServer().getOnlinePlayers()) {
							all.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			    					}
						Assemble assemble = new Assemble(Lonewolves.plugin, new ScoreboardAdapter());
						assemble.setTicks(16);
						assemble.setAssembleStyle(AssembleStyle.LONEWOLVES);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fScoreboard has been turned &aon&f."));
				}
				}
				if (!args[0].equalsIgnoreCase("toggle")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: &7/sb toggle"));
				}
			}
		}
		return false;
	}
}