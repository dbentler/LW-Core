package me.ezjamo;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import me.ezjamo.armorequipevent.ArmorListener;
import me.ezjamo.commands.*;
import me.ezjamo.managers.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lonewolves extends JavaPlugin implements Listener, PluginMessageListener {
    public static Lonewolves plugin;
    private Assemble assemble;
    FileManager manager;
	private Utils utils = new Utils();
    public static int task = 1;
    public static int size;

	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
		Economy econ = rsp.getProvider();
        return econ != null;
    }
    
    @Override
	public void onEnable() {
    	plugin = this;
    	this.getServer().getConsoleSender().sendMessage("");
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage(utils.color("&fLone&4Wolves-&fCore " + this.getDescription().getVersion()));
    	this.getServer().getConsoleSender().sendMessage(utils.color("&aEnabled"));
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage("");
		manager = new FileManager(this);
    	saveDefaultConfig();
    	Messages.load();
    	SpawnManager.getManager().setupFiles();
    	BlockedWordsManager.getManager().setupFiles();
    	AnnouncerManager.getManager().load();
    	PlayerdataManager.getManager().load();
    	WarpManager.getManager().load();
    	assemble = new Assemble(this, new ScoreboardAdapter());
		assemble.setTicks(16);
		assemble.setAssembleStyle(AssembleStyle.LONEWOLVES);
		getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
		PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, (this));
        pm.registerEvents(new ModModeManager(), this);
        pm.registerEvents(new ChatManager(), this);
        pm.registerEvents(new FreezeManager(), this);
        pm.registerEvents(new FreezeCommand(), this);
        pm.registerEvents(new LWManagers(), this);
        pm.registerEvents(new DepthStriderManager(), this);
        pm.registerEvents(new DispenerArmorListener(), this);
        pm.registerEvents(new FFLYManager(), this);
        pm.registerEvents(new WeatherManager(), this);
        pm.registerEvents(new WildToolsFixManager(), this);
        pm.registerEvents(new SpongeManager(), this);
        pm.registerEvents(new RespawnManager(), this);
        pm.registerEvents(new SpawnManager(), this);
        pm.registerEvents(new CustomCmdsManager(), this);
        pm.registerEvents(new BlockedWordsManager(), this);
        pm.registerEvents(new AnnouncerManager(), this);
        pm.registerEvents(new PlayerdataManager(), this);
        pm.registerEvents(new WarpManager(), this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    	getCommand("request").setExecutor(new Helpop());
    	getCommand("mod").setExecutor(new Modmode());
    	getCommand("sc").setExecutor(new StaffChat());
    	getCommand("acc").setExecutor(new AdminChat());
    	getCommand("distance").setExecutor(new DistanceCommand());
    	getCommand("help").setExecutor(new HelpCommand());
    	getCommand("links").setExecutor(new LinksCommand());
    	getCommand("rules").setExecutor(new RulesCommand());
    	getCommand("setslots").setExecutor(new SetSlotsCommand());
    	getCommand("nv").setExecutor(new NightVisionCommand());
    	getCommand("freeze").setExecutor(new FreezeCommand());
    	getCommand("admin").setExecutor(new AdminCommand());
    	getCommand("inv").setExecutor(new SwitchInventoryCommand());
    	getCommand("ping").setExecutor(new PingCommand());
    	getCommand("hub").setExecutor(new HubCommand());
    	getCommand("setspawn").setExecutor(new SetSpawnCommand());
    	getCommand("spawn").setExecutor(new SpawnCommand());
    	getCommand("startkoth").setExecutor(new KothCommand());
    	getCommand("kits").setExecutor(new KitsCommand());
    	getCommand("preview").setExecutor(new PreviewCommand());
    	getCommand("stats").setExecutor(new StatsCommand());
    	getCommand("demote").setExecutor(new DemoteCommand());
    	getCommand("removemm").setExecutor(new RemoveModModeCommand());
    	getCommand("playtime").setExecutor(new PlaytimeCommand());
    	getCommand("worth").setExecutor(new WorthCommand());
    	getCommand("commands").setExecutor(new CmdsCommand());
    	getCommand("scoreboard").setExecutor(new ScoreboardCommand(assemble));
    	getCommand("ma").setExecutor(new AnnouncerManager());
    	getCommand("setwarp").setExecutor(new SetWarpCommand());
    	getCommand("delwarp").setExecutor(new DelWarpCommand());
    	getCommand("warp").setExecutor(new WarpCommand());
    	loadTabCompleters();
    	new KothManager(this);
    	new KitsManager(this);
    	new PreviewManager(this);
    	if(!(setupEconomy())) {
            getLogger().severe("LW-Core requires vault.");
            getServer().getPluginManager().disablePlugin(this);
        }
    	if (AnnouncerManager.getManager().get().getBoolean("announcer-settings.enabled")) {
    		task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
    			size++;
                Set<String> configMessages = AnnouncerManager.getManager().get().getConfigurationSection("Messages").getKeys(false);
                if (size > configMessages.size()) {
                    size = 1;
                }
                for (String message : AnnouncerManager.getManager().get().getStringList("Messages." + size)) {
                	for (Player player : Bukkit.getOnlinePlayers()) {
                    	player.playSound(player.getLocation(), Sound.valueOf(AnnouncerManager.getManager().get().getString("announcer-settings.sound")), 0.5f, 1.0f);
                    }
                	for (Player online : Bukkit.getOnlinePlayers()) {
                		online.sendMessage(utils.color(message));
					}
                }
            }, 5 * 20, AnnouncerManager.getManager().get().getInt("announcer-settings.interval") * 20);
    	}
    }
	
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("");
		this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage(utils.color("&fLone&4Wolves-&fCore " + this.getDescription().getVersion()));
    	this.getServer().getConsoleSender().sendMessage(utils.color("&cDisabled"));
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage("");
    	Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
    	Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
    	for (Player staff : Bukkit.getOnlinePlayers()) {
    		PlayerdataManager data = PlayerdataManager.getManager();
    		staff.setGameMode(GameMode.SURVIVAL);
    		staff.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    		data.get().set("players." + staff.getUniqueId() + ".scoreboard", null);
    		data.save();
    	}
	}

	private void loadTabCompleters() {
		getCommand("delwarp").setTabCompleter(new DelWarpCommand());
		getCommand("warp").setTabCompleter(new WarpCommand());
		getCommand("playtime").setTabCompleter(new PlaytimeCommand());
		getCommand("stats").setTabCompleter(new StatsCommand());
		getCommand("commands").setTabCompleter(new CmdsCommand());
		getCommand("worth").setTabCompleter(new WorthCommand());
		getCommand("help").setTabCompleter(new HelpCommand());
		getCommand("preview").setTabCompleter(new PreviewCommand());
		getCommand("scoreboard").setTabCompleter(new ScoreboardCommand(assemble));
		getCommand("admin").setTabCompleter(new AdminCommand());
		getCommand("inv").setTabCompleter(new SwitchInventoryCommand());
	}
	    	
	public String getMessage(String path) {
        String message = getConfig().getString("Messages." + path);
        if (message == null) {
            message = getConfig().getString("Messages." + path);
        }
        return message;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("lw")) {
    		if (!(sender instanceof Player)) {
    			if (args[0].equalsIgnoreCase("reload")) {
    				reloadConfig();
        			Messages.load();
        			BlockedWordsManager.getManager().reloadConfig();
        			SpawnManager.getManager().reloadConfig();
        			sender.sendMessage(Messages.prefix + Messages.reloadConfig);
        			return true;
    			}
    			else utils.message(sender, "&cUsage: &7/lw reload");
    			return true;
    		}
    		Player player = (Player) sender;
    		if (player.hasPermission("lw.reload")) {
    			if (args.length == 1) {
    				if (args[0].equalsIgnoreCase("reload")) {
    					reloadConfig();
    					Messages.load();
    					BlockedWordsManager.getManager().reloadConfig();
						SpawnManager.getManager().reloadConfig();
    					player.sendMessage(Messages.prefix + Messages.reloadConfig);
    					getServer().getConsoleSender().sendMessage(Messages.prefix + Messages.reloadConfig);
    					return true;
    				}
    				if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver")) {
    					utils.message(player, Messages.prefix + "&f" + getDescription().getFullName());
    					return true;
    				}
    			}
    			if (args.length == 2) {
    				if (args[0].equalsIgnoreCase("help") && args[1].equals("1")) {
    					player.sendMessage(utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(utils.color("&fLone&4Wolves&8-&fCore Help &8- &f(1/3)"));
    					player.sendMessage(utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(utils.color("&c/request &8- &fSends a message to all online staff."));
    					player.sendMessage(utils.color("&c/mod &8- &fToggles mod mode."));
    					player.sendMessage(utils.color("&c/sc &8- &fToggles staff chat."));
    					player.sendMessage(utils.color("&c/acc &8- &fToggles admin chat."));
    					player.sendMessage(utils.color("&c/distance &8- &fShows your distance from /spawn"));
    					player.sendMessage(utils.color("&c/help &8- &fShows help pages that are set in config."));
    					player.sendMessage(utils.color("&c/links &8- &fShows links that are set in config."));
    					player.sendMessage(utils.color("&c/rules &8- &fShows rules that are set in config."));
    					player.sendMessage(utils.color("&c/setslots &8- &fSets the maximum players allowed on the server."));
    					player.sendMessage(utils.color("&c/nv &8- &fToggles night vision effect."));
    					player.sendMessage(utils.color("&8&m--------------------&f/help 2&8&m--------------------------"));
    				}
    				if (args[0].equalsIgnoreCase("help") && args[1].equals("2")) {
        				player.sendMessage(utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(utils.color("&fLone&4Wolves&8-&fCore Help &8- &f(2/3)"));
    					player.sendMessage(utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(utils.color("&c/freeze &8- &fFreezes and unfreezes a player."));
    					player.sendMessage(utils.color("&c/admin &8- &fGives and removes * permission and OP."));
    					player.sendMessage(utils.color("&c/inv &8- &fSwitch between a new and your previous inventory."));
    					player.sendMessage(utils.color("&c/ping &8- &fShows your ping to the server."));
    					player.sendMessage(utils.color("&c/hub &8- &fTakes you to your network hub defined in the config."));
    					player.sendMessage(utils.color("&c/setspawn &8- &fSets the server spawnpoint."));
    					player.sendMessage(utils.color("&c/spawn &8- &fTakes you to the set spawnpoint."));
    					player.sendMessage(utils.color("&c/startkoth &8- &fOpens a GUI that lets you start a koth."));
    					player.sendMessage(utils.color("&c/kits &8- &fOpens a GUI that lets you select a kit."));
    					player.sendMessage(utils.color("&c/preview &8- &fOpens a GUI that lets you preview the defined kit."));
    					player.sendMessage(utils.color("&8&m--------------------&f/help 3&8&m--------------------------"));
        			}
        			if (args[0].equalsIgnoreCase("help") && args[1].equals("3")) {
        				player.sendMessage(utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(utils.color("&fLone&4Wolves&8-&fCore Help &8- &f(3/3)"));
    					player.sendMessage(utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(utils.color("&c/stats &8- &fShows the stats of a player that are set in the config."));
    					player.sendMessage(utils.color("&c/demote &8- &fDemotes the specified player."));
    					player.sendMessage(utils.color("&c/removemm &8- &fRemoves mod mode from the specified player."));
    					player.sendMessage(utils.color("&c/playtime &8- &fShows the playtime of the specified player."));
    					player.sendMessage(utils.color("&c/worth &8- &fShows the worth of blocks for FTop set in config."));
    					player.sendMessage(utils.color("&c/commands &8- &fShows the commands other ranks have access to."));
    					player.sendMessage(utils.color("&c/scoreboard &8- &fToggles the scoreboard on and off."));
    					player.sendMessage(utils.color("&c/lw reload &8- &fReloads all configuration files for this plugin."));
    					player.sendMessage(utils.color("&8&m----------------------------------------------------"));
        			}
        			return true;
    			}
    			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
    				player.sendMessage(utils.color("&8&m----------------------------------------------------"));
					player.sendMessage(utils.color("&fLone&4Wolves&8-&fCore Help &8- &f(1/3)"));
					player.sendMessage(utils.color("&8&m----------------------------------------------------"));
					player.sendMessage(utils.color("&c/request &8- &fSends a message to all online staff."));
					player.sendMessage(utils.color("&c/mod &8- &fToggles mod mode."));
					player.sendMessage(utils.color("&c/sc &8- &fToggles staff chat."));
					player.sendMessage(utils.color("&c/acc &8- &fToggles admin chat."));
					player.sendMessage(utils.color("&c/distance &8- &fShows your distance from /spawn"));
					player.sendMessage(utils.color("&c/help &8- &fShows help pages that are set in config."));
					player.sendMessage(utils.color("&c/links &8- &fShows links that are set in config."));
					player.sendMessage(utils.color("&c/rules &8- &fShows rules that are set in config."));
					player.sendMessage(utils.color("&c/setslots &8- &fSets the maximum players allowed on the server."));
					player.sendMessage(utils.color("&c/nv &8- &fToggles night vision effect."));
					player.sendMessage(utils.color("&8&m--------------------&f/help 2&8&m--------------------------"));
					return true;
				}
    			else utils.message(player, Messages.prefix + "&fInvalid command.");
    		}
    		else utils.message(player, Messages.prefix + "&f" + getDescription().getFullName());
    	}
		return true;
    }
    
    @Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> empty = new ArrayList<>();
		if (args.length == 1) {
			List<String> lw = new ArrayList<>();
			lw.add("help");
			lw.add("ver");
			lw.add("version");
			lw.add("reload");
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], lw, completions);
			return completions;
		}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("help")) {
				List<String> lw = new ArrayList<>();
				lw.add("1");
				lw.add("2");
				lw.add("3");
				List<String> completions = new ArrayList<>();
				StringUtil.copyPartialMatches(args[1], lw, completions);
				return completions;
			}
			return empty;
		}
		return empty;
	}

    public void onPluginMessageReceived(String channel, Player player, byte[] bytemessage) {
    }
}
