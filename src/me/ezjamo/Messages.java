package me.ezjamo;

public class Messages extends Utils {
	
	private static transient Messages instance = new Messages();
	
	public static String prefix = msg("&8[&f&lLone&4&lWolves&8] ");
	
	public static String helpopPrefix = msg("&8[&cLW-Helpop&8] ");
	
	public static String helpopStaffMessage = msg("&c%player_name%: &d&o");
	
	public static String helpopPlayerMessage = msg("&aYour help request has been sent to all online staff members");
	
	public static String noPermission = msg("&cYou do not have permission to do this.");
	
	public static String reloadConfig = msg("&7You have reloaded the configuration files!");
	
	public static String playerSaidBlockedWord = msg("&cYou have said a blacklisted word.");
	
	public static String distanceCommand = msg("&fYour distance from spawn is &4%distance% &fblock(s).");
	
	public static String modModeEnabled = msg("&fStaff mode &aenabled");
	
	public static String modModeDisabled = msg("&fStaff mode &cdisabled");
	
	public static String scoreboardEnabled = msg("&fScoreboard has been toggled &aon&f.");
	
	public static String scoreboardDisabled = msg("&fScoreboard has been toggled &coff&f.");
	
	public static String staffChatEnabled = msg("&aStaff chat enabled");
	
	public static String staffChatDisabled = msg("&cStaff chat disabled");
	
	public static String depthStriderDisabledMessage = msg("&fDepth Strider has been removed from your boots because it's currently &cdisabled.");
	
	public static String strengthDisabledMessage = msg("&cStrength II is currently disabled.");
	
	public static String aboveNetherBlockPlace = msg("&cYou cannot place blocks above the nether.");
	
	public static String netherSkeletonSpawnerPlace = msg("&cYou cannot spawn skeletons in the nether.");
	
	public static String spawnerPlacementDisabled = msg("&cSpawner placement is now disabled.");
	
	public static void load() {
		Lonewolves.plugin.manager.loadOrSaveDefault(instance, Messages.class, "messages");
	}
	
	public static void save() {
		Lonewolves.plugin.manager.save(instance);
	}
}
