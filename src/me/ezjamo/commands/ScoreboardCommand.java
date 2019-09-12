package me.ezjamo.commands;


import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleBoard;
import io.github.thatkawaiisam.assemble.events.AssembleBoardCreateEvent;
import io.github.thatkawaiisam.assemble.events.AssembleBoardDestroyEvent;
import lombok.Getter;
import me.ezjamo.Messages;

@Getter
public class ScoreboardCommand implements CommandExecutor {
	public static ArrayList<String> scoreboard;
	
	static {
        ScoreboardCommand.scoreboard = new ArrayList<String>();
    }
	
	private Assemble assemble;

	public ScoreboardCommand(Assemble assemble) {
		this.assemble = assemble;
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
					if (getAssemble().getBoards().containsKey(player.getUniqueId())) {
						AssembleBoardDestroyEvent destroyEvent = new AssembleBoardDestroyEvent(player);

						Bukkit.getPluginManager().callEvent(destroyEvent);
						if (destroyEvent.isCancelled()) {
							return true;
						}

						getAssemble().getBoards().remove(player.getUniqueId());
						player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
						player.sendMessage(Messages.prefix + Messages.scoreboardDisabled);
						return true;
				}
					if (!getAssemble().getBoards().containsKey(player.getUniqueId())) {
						AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);

						Bukkit.getPluginManager().callEvent(createEvent);
						if (createEvent.isCancelled()) {
							return true;
						}

						getAssemble().getBoards().put(player.getUniqueId(), new AssembleBoard(player, getAssemble()));
						player.sendMessage(Messages.prefix + Messages.scoreboardEnabled);
				}
				}
				if (!args[0].equalsIgnoreCase("toggle")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: &7/sb toggle"));
				}
			}
		}
		return true;
	}
}
