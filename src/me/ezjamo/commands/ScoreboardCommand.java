package me.ezjamo.commands;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleBoard;
import io.github.thatkawaiisam.assemble.events.AssembleBoardCreateEvent;
import io.github.thatkawaiisam.assemble.events.AssembleBoardDestroyEvent;
import lombok.Getter;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.managers.PlayerdataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScoreboardCommand extends Utils implements CommandExecutor, TabCompleter {
	private Assemble assemble;

	public ScoreboardCommand(Assemble assemble) {
		this.assemble = assemble;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		PlayerdataManager data = new PlayerdataManager(player.getUniqueId());
		if (cmd.getName().equalsIgnoreCase("scoreboard")) {
			if (args.length < 1) {
				message(player, "&cUsage: &7/sb toggle");
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("toggle")) {
					if (data.loadUser().getString(player.getUniqueId().toString() + ".scoreboard").equals("enabled")) {
						data.loadUser().set(player.getUniqueId().toString() + ".scoreboard", "disabled");
						data.saveUser();
						AssembleBoardDestroyEvent destroyEvent = new AssembleBoardDestroyEvent(player);

						Bukkit.getPluginManager().callEvent(destroyEvent);
						if (destroyEvent.isCancelled()) {
							return true;
						}

						getAssemble().getBoards().remove(player.getUniqueId());
						player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
						message(player, Messages.prefix + Messages.scoreboardDisabled);
						return true;
					}
					if (data.loadUser().getString(player.getUniqueId().toString() + ".scoreboard").equals("disabled")) {
						data.loadUser().set(player.getUniqueId().toString() + ".scoreboard", "enabled");
						data.saveUser();
						AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);

						Bukkit.getPluginManager().callEvent(createEvent);
						if (createEvent.isCancelled()) {
							return true;
						}

						getAssemble().getBoards().put(player.getUniqueId(), new AssembleBoard(player, getAssemble()));
						message(player, Messages.prefix + Messages.scoreboardEnabled);
					}
				}
				if (!args[0].equalsIgnoreCase("toggle")) {
					message(player, "&cUsage: &7/sb toggle");
				}
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> scoreboard = new ArrayList<>();
		scoreboard.add("toggle");
		List<String> empty = new ArrayList<>();
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], scoreboard, completions);
			return completions;
		}
		return empty;
	}

	public void removeScoreboard(Player player) {
		PlayerdataManager data = new PlayerdataManager(player.getUniqueId());
		if (data.loadUser() == null) return;
		if (data.loadUser().getString(player.getUniqueId().toString() + ".scoreboard") == null) return;
		if (data.loadUser().getString(player.getUniqueId().toString() + ".scoreboard").equals("enabled")) return;
		AssembleBoardDestroyEvent destroyEvent = new AssembleBoardDestroyEvent(player);
		Bukkit.getPluginManager().callEvent(destroyEvent);
		if (destroyEvent.isCancelled()) {
			return;
		}
		getAssemble().getBoards().remove(player.getUniqueId());
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
}
