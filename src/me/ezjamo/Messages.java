package me.ezjamo;

public class Messages extends Utils {
	
	private static transient Messages instance = new Messages();
	
	public static String prefix = msg("&8[&f&lLone&4&lWolves&8] ");
	
	public static String noPermission = msg("&cYou do not have permission to do this.");
	
	public static String reloadConfig = msg("&7You have reloaded the configuration files!");
	
	public static void load() {
		Lonewolves.plugin.manager.loadOrSaveDefault(instance, Messages.class, "messages");
	}
	
	public static void save() {
		Lonewolves.plugin.manager.save(instance);
	}
}
