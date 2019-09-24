package me.ezjamo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import me.ezjamo.armorequipevent.ArmorListener;
import me.ezjamo.commands.AdminChat;
import me.ezjamo.commands.CmdsCommand;
import me.ezjamo.commands.DelWarpCommand;
import me.ezjamo.commands.DemoteCommand;
import me.ezjamo.commands.DistanceCommand;
import me.ezjamo.commands.FTopWorthCommand;
import me.ezjamo.commands.FreezeCommand;
import me.ezjamo.commands.HelpCommand;
import me.ezjamo.commands.Helpop;
import me.ezjamo.commands.HubCommand;
import me.ezjamo.commands.KitsCommand;
import me.ezjamo.commands.KothCommand;
import me.ezjamo.commands.LinksCommand;
import me.ezjamo.commands.Modmode;
import me.ezjamo.commands.NightVisionCommand;
import me.ezjamo.commands.PingCommand;
import me.ezjamo.commands.PlaytimeCommand;
import me.ezjamo.commands.PreviewCommand;
import me.ezjamo.commands.RemoveModModeCommand;
import me.ezjamo.commands.RulesCommand;
import me.ezjamo.commands.ScoreboardCommand;
import me.ezjamo.commands.SetSlotsCommand;
import me.ezjamo.commands.SetSpawnCommand;
import me.ezjamo.commands.SetWarpCommand;
import me.ezjamo.commands.SpawnCommand;
import me.ezjamo.commands.StaffChat;
import me.ezjamo.commands.StaffOnOff;
import me.ezjamo.commands.StatsCommand;
import me.ezjamo.commands.SwitchInventoryCommand;
import me.ezjamo.commands.WarpCommand;
import me.ezjamo.managers.AnnouncerManager;
import me.ezjamo.managers.BlockedWordsManager;
import me.ezjamo.managers.ChatManager;
import me.ezjamo.managers.CustomCmdsManager;
import me.ezjamo.managers.DepthStriderManager;
import me.ezjamo.managers.DispenerArmorListener;
import me.ezjamo.managers.FFLYManager;
import me.ezjamo.managers.FileManager;
import me.ezjamo.managers.FreezeManager;
import me.ezjamo.managers.KitsManager;
import me.ezjamo.managers.KothManager;
import me.ezjamo.managers.LWManagers;
import me.ezjamo.managers.ModModeManager;
import me.ezjamo.managers.PlayerdataManager;
import me.ezjamo.managers.PreviewManager;
import me.ezjamo.managers.RespawnManager;
import me.ezjamo.managers.SpawnManager;
import me.ezjamo.managers.SpongeManager;
import me.ezjamo.managers.WarpManager;
import me.ezjamo.managers.WeatherManager;
import me.ezjamo.managers.WildToolsFixManager;
import net.milkbowl.vault.economy.Economy;

public class Lonewolves extends JavaPlugin implements Listener, PluginMessageListener {
    public static Lonewolves plugin;
    public FileManager manager;
    public static int task = 1;
    public static int size;
    
    public HashMap<UUID, ItemStack[]> contents;
    public HashMap<UUID, ItemStack[]> armorContents;
    
    public Lonewolves() {
        this.contents = new HashMap<UUID, ItemStack[]>();
        this.armorContents = new HashMap<UUID, ItemStack[]>();
    }
    
    public static Economy econ = null;

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    @Override
	public void onEnable() {
    	plugin = this;
    	this.getServer().getConsoleSender().sendMessage("");
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage(Utils.color("&fLone&4Wolves-&fCore " + this.getDescription().getVersion()));
    	this.getServer().getConsoleSender().sendMessage(Utils.color("&aEnabled"));
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage("");
		manager = new FileManager(this);
    	this.saveDefaultConfig();
    	Messages.load();
    	SpawnManager.getManager().setupFiles();
    	SpawnManager.getManager().reloadConfig();
    	BlockedWordsManager.getManager().setupFiles();
    	BlockedWordsManager.getManager().reloadConfig();
    	AnnouncerManager.getManager().load();
    	PlayerdataManager.getManager().load();
    	WarpManager.getManager().load();
    	Assemble assemble = new Assemble(this, new ScoreboardAdapter());
		assemble.setTicks(16);
		assemble.setAssembleStyle(AssembleStyle.LONEWOLVES);
		getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
        Bukkit.getPluginManager().registerEvents(this, (this));
        Bukkit.getPluginManager().registerEvents(new ModModeManager(), this);
        Bukkit.getPluginManager().registerEvents(new ChatManager(), this);
        Bukkit.getPluginManager().registerEvents(new FreezeManager(), this);
        Bukkit.getPluginManager().registerEvents(new FreezeCommand(), this);
        Bukkit.getPluginManager().registerEvents(new LWManagers(), this);
        Bukkit.getPluginManager().registerEvents(new DepthStriderManager(), this);
        Bukkit.getPluginManager().registerEvents(new DispenerArmorListener(), this);
        Bukkit.getPluginManager().registerEvents(new FFLYManager(), this);
        Bukkit.getPluginManager().registerEvents(new WeatherManager(), this);
        Bukkit.getPluginManager().registerEvents(new WildToolsFixManager(), this);
        Bukkit.getPluginManager().registerEvents(new SpongeManager(), this);
        Bukkit.getPluginManager().registerEvents(new RespawnManager(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnManager(), this);
        Bukkit.getPluginManager().registerEvents(new CustomCmdsManager(), this);
        Bukkit.getPluginManager().registerEvents(new BlockedWordsManager(), this);
        Bukkit.getPluginManager().registerEvents(new AnnouncerManager(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerdataManager(), this);
        Bukkit.getPluginManager().registerEvents(new WarpManager(), this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    	this.getCommand("request").setExecutor(new Helpop());
    	this.getCommand("mod").setExecutor(new Modmode());
    	this.getCommand("sc").setExecutor(new StaffChat());
    	this.getCommand("acc").setExecutor(new AdminChat());
    	this.getCommand("distance").setExecutor(new DistanceCommand());
    	this.getCommand("help").setExecutor(new HelpCommand());
    	this.getCommand("links").setExecutor(new LinksCommand());
    	this.getCommand("rules").setExecutor(new RulesCommand());
    	this.getCommand("setslots").setExecutor(new SetSlotsCommand());
    	this.getCommand("nv").setExecutor(new NightVisionCommand());
    	this.getCommand("freeze").setExecutor(new FreezeCommand());
    	this.getCommand("admin").setExecutor(new StaffOnOff());
    	this.getCommand("inv").setExecutor(new SwitchInventoryCommand());
    	this.getCommand("ping").setExecutor(new PingCommand());
    	this.getCommand("hub").setExecutor(new HubCommand());
    	this.getCommand("setspawn").setExecutor(new SetSpawnCommand());
    	this.getCommand("spawn").setExecutor(new SpawnCommand());
    	this.getCommand("startkoth").setExecutor(new KothCommand());
    	this.getCommand("kits").setExecutor(new KitsCommand());
    	this.getCommand("preview").setExecutor(new PreviewCommand());
    	this.getCommand("stats").setExecutor(new StatsCommand());
    	this.getCommand("demote").setExecutor(new DemoteCommand());
    	this.getCommand("removemm").setExecutor(new RemoveModModeCommand());
    	this.getCommand("playtime").setExecutor(new PlaytimeCommand());
    	this.getCommand("worth").setExecutor(new FTopWorthCommand());
    	this.getCommand("commands").setExecutor(new CmdsCommand());
    	this.getCommand("scoreboard").setExecutor(new ScoreboardCommand(assemble));
    	this.getCommand("ma").setExecutor(new AnnouncerManager());
    	this.getCommand("setwarp").setExecutor(new SetWarpCommand());
    	this.getCommand("delwarp").setExecutor(new DelWarpCommand());
    	this.getCommand("warp").setExecutor(new WarpCommand());
    	new KothManager(this);
    	new KitsManager(this);
    	new PreviewManager(this);
    	if(!(setupEconomy())) {
            getLogger().severe("LW-Core requires vault.");
            getServer().getPluginManager().disablePlugin(this);
        }
    	if (AnnouncerManager.getManager().get().getBoolean("announcer-enabled")) {
    		task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
    			size++;
                Set<String> configMessages = AnnouncerManager.getManager().get().getConfigurationSection("Messages").getKeys(false);
                if (size > configMessages.size()) {
                    size = 1;
                }
                for (String message : AnnouncerManager.getManager().get().getStringList("Messages." + size)) {
                	for (Player player : Bukkit.getOnlinePlayers()) {
                    	player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 0.5f, 1.0f);
                    }
                    Bukkit.getServer().broadcastMessage(Utils.color(message));
                }
            }, 5 * 20, AnnouncerManager.getManager().get().getInt("announcer-interval") * 20);
    	}
    }
	
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("");
		this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage(Utils.color("&fLone&4Wolves-&fCore " + this.getDescription().getVersion()));
    	this.getServer().getConsoleSender().sendMessage(Utils.color("&cDisabled"));
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage("");
    	Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
    	Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
    	for (Player staff : Bukkit.getOnlinePlayers()) {
    		PlayerdataManager data = PlayerdataManager.getManager();
    		staff.setGameMode(GameMode.SURVIVAL);
    		staff.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    		data.get().set("players", null);
    		try {
				data.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}
	    	
	public String getMessage(String path) {
        String message = this.getConfig().getString("Messages." + path);
        if (message == null) {
            message = this.getConfig().getString("Messages." + path);
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
        			sender.sendMessage(Messages.prefix + Messages.reloadConfig);
        			return true;
    			}
    		}
    		Player player = (Player) sender;
    		if (player.hasPermission("lw.reload")) {
    			if (args.length == 1) {
    				if (args[0].equalsIgnoreCase("reload")) {
    					reloadConfig();
    					Messages.load();
    					BlockedWordsManager.getManager().reloadConfig();
    					player.sendMessage(Messages.prefix + Messages.reloadConfig);
    					getServer().getConsoleSender().sendMessage(Messages.prefix + Messages.reloadConfig);
    					return true;
    				}
    				if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver")) {
    					Utils.message(player, Messages.prefix + "&f" + getDescription().getFullName());
    					return true;
    				}
    			}
    			if (args.length == 2) {
    				if (args[0].equalsIgnoreCase("help") && args[1].equals("1")) {
    					player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(Utils.color("&fLone&4Wolves&8-&fCore Help &8- &f(1/3)"));
    					player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(Utils.color("&c/request &8- &fSends a message to all online staff."));
    					player.sendMessage(Utils.color("&c/mod &8- &fToggles mod mode."));
    					player.sendMessage(Utils.color("&c/sc &8- &fToggles staff chat."));
    					player.sendMessage(Utils.color("&c/acc &8- &fToggles admin chat."));
    					player.sendMessage(Utils.color("&c/distance &8- &fShows your distance from /spawn"));
    					player.sendMessage(Utils.color("&c/help &8- &fShows help pages that are set in config."));
    					player.sendMessage(Utils.color("&c/links &8- &fShows links that are set in config."));
    					player.sendMessage(Utils.color("&c/rules &8- &fShows rules that are set in config."));
    					player.sendMessage(Utils.color("&c/setslots &8- &fSets the maximum players allowed on the server."));
    					player.sendMessage(Utils.color("&c/nv &8- &fToggles night vision effect."));
    					player.sendMessage(Utils.color("&8&m--------------------&f/help 2&8&m--------------------------"));
    				}
    				if (args[0].equalsIgnoreCase("help") && args[1].equals("2")) {
        				player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(Utils.color("&fLone&4Wolves&8-&fCore Help &8- &f(2/3)"));
    					player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(Utils.color("&c/freeze &8- &fFreezes and unfreezes a player."));
    					player.sendMessage(Utils.color("&c/admin &8- &fGives and removes * permission and OP."));
    					player.sendMessage(Utils.color("&c/inv &8- &fSwitch between a new and your previous inventory."));
    					player.sendMessage(Utils.color("&c/ping &8- &fShows your ping to the server."));
    					player.sendMessage(Utils.color("&c/hub &8- &fTakes you to your network hub defined in the config."));
    					player.sendMessage(Utils.color("&c/setspawn &8- &fSets the server spawnpoint."));
    					player.sendMessage(Utils.color("&c/spawn &8- &fTakes you to the set spawnpoint."));
    					player.sendMessage(Utils.color("&c/startkoth &8- &fOpens a GUI that lets you start a koth."));
    					player.sendMessage(Utils.color("&c/kits &8- &fOpens a GUI that lets you select a kit."));
    					player.sendMessage(Utils.color("&c/preview &8- &fOpens a GUI that lets you preview the defined kit."));
    					player.sendMessage(Utils.color("&8&m--------------------&f/help 3&8&m--------------------------"));
        			}
        			if (args[0].equalsIgnoreCase("help") && args[1].equals("3")) {
        				player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(Utils.color("&fLone&4Wolves&8-&fCore Help &8- &f(3/3)"));
    					player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
    					player.sendMessage(Utils.color("&c/stats &8- &fShows the stats of a player that are set in the config."));
    					player.sendMessage(Utils.color("&c/demote &8- &fDemotes the specified player."));
    					player.sendMessage(Utils.color("&c/removemm &8- &fRemoves mod mode from the specified player."));
    					player.sendMessage(Utils.color("&c/playtime &8- &fShows the playtime of the specified player."));
    					player.sendMessage(Utils.color("&c/worth &8- &fShows the worth of blocks for FTop set in config."));
    					player.sendMessage(Utils.color("&c/commands &8- &fShows the commands other ranks have access to."));
    					player.sendMessage(Utils.color("&c/scoreboard &8- &fToggles the scoreboard on and off."));
    					player.sendMessage(Utils.color("&c/lw reload &8- &fReloads all configuration files for this plugin."));
    					player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
        			}
        			return true;
    			}
    			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
    				player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
					player.sendMessage(Utils.color("&fLone&4Wolves&8-&fCore Help &8- &f(1/3)"));
					player.sendMessage(Utils.color("&8&m----------------------------------------------------"));
					player.sendMessage(Utils.color("&c/request &8- &fSends a message to all online staff."));
					player.sendMessage(Utils.color("&c/mod &8- &fToggles mod mode."));
					player.sendMessage(Utils.color("&c/sc &8- &fToggles staff chat."));
					player.sendMessage(Utils.color("&c/acc &8- &fToggles admin chat."));
					player.sendMessage(Utils.color("&c/distance &8- &fShows your distance from /spawn"));
					player.sendMessage(Utils.color("&c/help &8- &fShows help pages that are set in config."));
					player.sendMessage(Utils.color("&c/links &8- &fShows links that are set in config."));
					player.sendMessage(Utils.color("&c/rules &8- &fShows rules that are set in config."));
					player.sendMessage(Utils.color("&c/setslots &8- &fSets the maximum players allowed on the server."));
					player.sendMessage(Utils.color("&c/nv &8- &fToggles night vision effect."));
					player.sendMessage(Utils.color("&8&m--------------------&f/help 2&8&m--------------------------"));
					return true;
				}
    			else Utils.message(player, Messages.prefix + "&fInvalid command.");
    		}
    		else Utils.message(player, Messages.prefix + "&f" + getDescription().getFullName());
    	}
		return true;
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] bytemessage) {
        if (!channel.equals("BungeeCord"))
            return;
    }
}
