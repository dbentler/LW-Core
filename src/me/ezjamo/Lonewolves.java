package me.ezjamo;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import me.ezjamo.commands.SpawnCommand;
import me.ezjamo.commands.StaffChat;
import me.ezjamo.commands.StaffOnOff;
import me.ezjamo.commands.StatsCommand;
import me.ezjamo.commands.SwitchInventoryCommand;
import me.ezjamo.managers.ChatManager;
import me.ezjamo.managers.CustomCmdsManager;
import me.ezjamo.managers.DepthStriderManager;
import me.ezjamo.managers.DispenerArmorListener;
import me.ezjamo.managers.FFLYManager;
import me.ezjamo.managers.FreezeManager;
import me.ezjamo.managers.KitsManager;
import me.ezjamo.managers.KothManager;
import me.ezjamo.managers.LWManagers;
import me.ezjamo.managers.ModModeManager;
import me.ezjamo.managers.PreviewManager;
import me.ezjamo.managers.RespawnManager;
import me.ezjamo.managers.SpawnManager;
import me.ezjamo.managers.SpongeManager;
import me.ezjamo.managers.WeatherManager;
import me.ezjamo.managers.WildToolsFixManager;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Lonewolves extends JavaPlugin implements Listener, PluginMessageListener {
    public static Lonewolves plugin;
    public static String NO_PERMS = ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fYou do not have permission to do this.");
    
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
    	this.getServer().getConsoleSender().sendMessage("");
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage(Utils.chat("&fLone&4Wolves-&fCore v2.8"));
    	this.getServer().getConsoleSender().sendMessage(Utils.chat("&aEnabled"));
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage("");
		plugin = this;
		Assemble assemble = new Assemble(this, new ScoreboardAdapter());
		assemble.setTicks(16);
		assemble.setAssembleStyle(AssembleStyle.LONEWOLVES);
    	this.saveDefaultConfig();
    	SpawnManager.getManager().setupFiles();
    	SpawnManager.getManager().reloadConfig();
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
    	this.getCommand("scoreboard").setExecutor(new ScoreboardCommand());
    	new KothManager(this);
    	new KitsManager(this);
    	new PreviewManager(this);
    	if(!(setupEconomy())) {
            getLogger().severe("LW-Core requires vault.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
	
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("");
		this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage(Utils.chat("&fLone&4Wolves-&fCore v2.8"));
    	this.getServer().getConsoleSender().sendMessage(Utils.chat("&cDisabled"));
    	this.getServer().getConsoleSender().sendMessage("----------------------------------------");
    	this.getServer().getConsoleSender().sendMessage("");
    	for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
    		staff.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    		staff.setGameMode(GameMode.SURVIVAL);
    	}
    	Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
    	Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
	}
	    	
	public String getMessage(String path, Object... additionalPlayerInfo) {
        String message = this.getConfig().getString("Messages." + path);
        if (message == null) {
            message = this.getConfig().getString("Messages." + path);
        }
        return message;
    }
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("lw")) {
    		if (!(sender instanceof Player) && args[0].equalsIgnoreCase("reload")) {
    			reloadConfig();
    			sender.sendMessage(Utils.chat("&8[&f&lLone&4&lWolves&8] &aConfig Reloaded."));
    			return true;
    		}
    		Player player = (Player) sender;
    		if (player.hasPermission("lw.reload")) {
    			if (args.length == 1) {
    				if (args[0].equalsIgnoreCase("reload")) {
    					reloadConfig();
    					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &aConfig Reloaded."));
    					this.getServer().getConsoleSender().sendMessage(Utils.chat("&8[&f&lLone&4&lWolves&8] &aConfig Reloaded."));
    				}
    				if (!args[0].equalsIgnoreCase("reload")) {
    					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fInvalid command."));
    				}
    			}
    			if (args.length == 0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &f" + plugin.getDescription().getFullName()));
				}
    		}
    		if (!player.hasPermission("lw.reload")) {
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &f" + plugin.getDescription().getFullName()));
    		}
    	}
		return true;
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] bytemessage) {
        if (!channel.equals("BungeeCord"))
            return;
    }
}
