package me.ezjamo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.clip.placeholderapi.PlaceholderAPI;
import subside.plugins.koth.KothPlugin;

public class ScoreboardAdapter implements AssembleAdapter {


	private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
	static {
		suffixes.put(1_000L, "k");
		suffixes.put(1_000_000L, "M");
		suffixes.put(1_000_000_000L, "B");
		suffixes.put(1_000_000_000_000L, "t");
		suffixes.put(1_000_000_000_000_000L, "qu");
		suffixes.put(1_000_000_000_000_000_000L, "qi");
	}

	public static String formatMoney(long value) {
		if (value == Long.MIN_VALUE) return formatMoney(Long.MIN_VALUE + 1);
		if (value < 0) return "-" + formatMoney(-value);
		if (value < 1000) return Long.toString(value);

		Map.Entry<Long, String> e = suffixes.floorEntry(value);
		Long divideBy = e.getKey();
		String suffix = e.getValue();

		long truncated = value / (divideBy / 10);
		double divider1 = truncated / 10d;
		int divider2 = (int)truncated / 10;
		boolean hasDecimal = truncated < 100 && divider1 != divider2;
		return hasDecimal ? divider1 + suffix : divider2 + suffix;
	}

	@Override
	public String getTitle(Player player) {
		String title = Lonewolves.plugin.getConfig().getString("Scoreboard.Title");
		return ChatColor.translateAlternateColorCodes('&', title);
	}

	@Override

	public List<String> getLines(Player player) {
		final List<String> toReturn = new ArrayList<>();
		Plugin koth = Lonewolves.plugin.getServer().getPluginManager().getPlugin("KoTH");
		if (koth == null) {
			Lonewolves.plugin.getLogger().log(Level.SEVERE, "KoTH by Subside is needed for LW-Core.");
		}
		KothPlugin kothPlugin = (KothPlugin) koth;
		FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
		for (String message : Lonewolves.plugin.getConfig().getStringList("Scoreboard.Default")) {
			String placeholders = PlaceholderAPI.setPlaceholders(player, message);
			toReturn.add(ChatColor.translateAlternateColorCodes('&', placeholders.replaceAll("%faction%", fPlayer.getFaction().getTag().toString())));
		}
		if (!kothPlugin.getKothHandler().getRunningKoths().isEmpty()) {
			for (String message : Lonewolves.plugin.getConfig().getStringList("Scoreboard.Koth")) {
				toReturn.add(ChatColor.translateAlternateColorCodes('&', message.replaceAll("%koth%", kothPlugin.getKothHandler().getRunningKoth().getKoth().getName())
						.replaceAll("%time%", kothPlugin.getKothHandler().getRunningKoth().getTimeObject().getTimeLeftFormatted())
						.replaceAll("%x%", Integer.toString(kothPlugin.getKothHandler().getRunningKoth().getKoth().getMiddle().getBlockX()))
						.replaceAll("%z%", Integer.toString(kothPlugin.getKothHandler().getRunningKoth().getKoth().getMiddle().getBlockZ()))));
			}
		}
		toReturn.add(ChatColor.translateAlternateColorCodes('&',"&7&m-----------------"));
		return toReturn;
	}
}
