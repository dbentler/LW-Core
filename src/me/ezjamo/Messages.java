package me.ezjamo;

import org.bukkit.ChatColor;

public class Messages {
	private static String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	private static transient Messages instance = new Messages();
	
	public static String prefix = color("&8[&f&lLone&4&lWolves&8] ");
	
	public static String helpopPrefix = color("&8[&cLW-Helpop&8] ");
	
	public static String helpopStaffMessage = color("&c%player_name%: &d&o");
	
	public static String helpopPlayerMessage = color("&aYour help request has been sent to all online staff members");
	
	public static String noPermission = color("&cYou do not have permission to do this.");
	
	public static String reloadConfig = color("&7You have reloaded the configuration files!");
	
	public static String playerSaidBlockedWord = color("&cYou have said a blacklisted word.");
	
	public static String distanceCommand = color("&fYour distance from spawn is &4%distance% &fblock(s).");
	
	public static String modModeEnabled = color("&fStaff mode &aenabled");
	
	public static String modModeDisabled = color("&fStaff mode &cdisabled");
	
	public static String scoreboardEnabled = color("&fScoreboard has been toggled &aon&f.");
	
	public static String scoreboardDisabled = color("&fScoreboard has been toggled &coff&f.");
	
	public static String staffChatEnabled = color("&aStaff chat enabled");
	
	public static String staffChatDisabled = color("&cStaff chat disabled");
	
	public static String depthStriderDisabledMessage = color("&fDepth Strider has been removed from your boots because it's currently &cdisabled.");
	
	public static String strengthDisabledMessage = color("&cStrength II is currently disabled.");
	
	public static String aboveNetherBlockPlace = color("&cYou cannot place blocks above the nether.");
	
	public static String netherSkeletonSpawnerPlace = color("&cYou cannot spawn skeletons in the nether.");
	
	public static String spawnerPlacementDisabled = color("&cSpawner placement is now disabled.");
	
	public static void load() {
		Lonewolves.plugin.manager.loadOrSaveDefault(instance, Messages.class, "messages");
	}
	
	public static void save() {
		Lonewolves.plugin.manager.save(instance);
	}
}
