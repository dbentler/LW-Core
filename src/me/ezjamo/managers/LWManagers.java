package me.ezjamo.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class LWManagers implements Listener {
	private Utils utils = new Utils();

	 // TODO: beacon shit needs to be added
    @EventHandler
    public void onDrink(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
             ItemStack it = player.getItemInHand();
            if ((it.getType() == Material.POTION && it.getDurability() == 8233) || it.getDurability() == 16425) {
                 Potion potion = Potion.fromItemStack(it);
                 PotionEffectType effecttype = potion.getType().getEffectType();
                if (effecttype == PotionEffectType.INCREASE_DAMAGE) {
                	boolean enabled = Lonewolves.plugin.getConfig().getBoolean("disable-strength-2");
                	if (enabled) {
                		event.setCancelled(true);
                        player.sendMessage(Messages.prefix + Messages.strengthDisabledMessage);
                	}
                }
            }
        }
    }

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		SpawnManager spawnCoords = SpawnManager.getManager();
		if (p.getWorld().getName().equalsIgnoreCase("world_nether")) {
			if (!p.hasPermission("lw.bypass.nethertopplace")) {
				if (p.getLocation().getBlockY() >= 128) {
					boolean enabled = Lonewolves.plugin.getConfig().getBoolean("disable-top-of-nether-place");
					if (enabled) {
						e.setCancelled(true);
						if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
							p.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
						}
						if (spawnCoords.getConfig().getConfigurationSection("spawn") != null) {
							World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("spawn.world"));
							double x = spawnCoords.getConfig().getDouble("spawn.x");
							double y = spawnCoords.getConfig().getDouble("spawn.y");
							double z = spawnCoords.getConfig().getDouble("spawn.z");
							float yaw = (float)spawnCoords.getConfig().getDouble("spawn.yaw");
							float pitch = (float)spawnCoords.getConfig().getDouble("spawn.pitch");
							Location loc = new Location(w, x, y, z, yaw, pitch);
							p.teleport(loc);
						}
						p.sendMessage(Messages.prefix + Messages.aboveNetherBlockPlace);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onSpawnerPlace(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack i = e.getPlayer().getItemInHand();
		if (p.getWorld().getName().equalsIgnoreCase("world_nether")) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK && i.getType() == Material.MOB_SPAWNER && i.getItemMeta().hasDisplayName()) {
				if (i.getItemMeta().getDisplayName().contains("Skeleton")) {
					boolean enabled = Lonewolves.plugin.getConfig().getBoolean("disable-skeleton-spawners-nether");
					if (enabled) {
						e.setCancelled(true);
						p.sendMessage(Messages.prefix + Messages.netherSkeletonSpawnerPlace);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void SpawnerToggle(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().getName().equalsIgnoreCase("world_nether") || p.getWorld().getName().equalsIgnoreCase("world")) {
			boolean enabled = Lonewolves.plugin.getConfig().getBoolean("enable-spawner-placement");
			if (!enabled) {
				if (e.getBlockPlaced().getType() == Material.MOB_SPAWNER) {
					e.setCancelled(true);
					p.sendMessage(Messages.prefix + Messages.spawnerPlacementDisabled);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SpawnManager spawnCoords = SpawnManager.getManager();
		PlayerdataManager data = new PlayerdataManager(player.getUniqueId());
		data.createUser(player);
		if (!player.hasPlayedBefore()) {
			if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
				String firstKit = Lonewolves.plugin.getConfig().getString("first-join-kit");
	            String message = Lonewolves.plugin.getMessage("welcome-message");
	            String placeholders = PlaceholderAPI.setPlaceholders(player, message);
				event.setJoinMessage(utils.color(placeholders));
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "ekit " + firstKit + " " +  player.getName());
				player.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
				return;
			}
			if (spawnCoords.getConfig().getConfigurationSection("spawn") != null) {
				World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("spawn.world"));
				double x = spawnCoords.getConfig().getDouble("spawn.x");
				double y = spawnCoords.getConfig().getDouble("spawn.y");
				double z = spawnCoords.getConfig().getDouble("spawn.z");
				float yaw = (float)spawnCoords.getConfig().getDouble("spawn.yaw");
				float pitch = (float)spawnCoords.getConfig().getDouble("spawn.pitch");
				Location loc = new Location(w, x, y, z, yaw, pitch);
				String firstKit = Lonewolves.plugin.getConfig().getString("first-join-kit");
	            String message = Lonewolves.plugin.getMessage("welcome-message");
	            String placeholders = PlaceholderAPI.setPlaceholders(player, message);
				event.setJoinMessage(utils.color(placeholders));
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "ekit " + firstKit + " " + player.getName());
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lonewolves.plugin, () -> player.teleport(loc), 1L);
			}
		}
	}

	@EventHandler
	public void onSpawnerMove(InventoryClickEvent event) {
		Set<Material> blockedItems = new HashSet<>();
		for (String s : Lonewolves.plugin.getConfig().getStringList("chest-blocked-items.items")) {
			Material material = Material.matchMaterial(s);
			if (material != null) {
				blockedItems.add(material);
			}
		}
		boolean enabled = Lonewolves.plugin.getConfig().getBoolean("chest-blocked-items.enabled");
		if (enabled) {
			if (event.getInventory().getType() == InventoryType.ENDER_CHEST || event.getInventory().getTitle().contains("Faction Chest")) {
				if (event.getCurrentItem() != null && blockedItems.contains(event.getCurrentItem().getType())) {
					if (!event.getWhoClicked().hasPermission("lw.bypass.blockeditems")) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerKill(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Player killer = player.getKiller();
		if (killer != null) {
			if (killer.hasPermission("lw.lightning")) {
				Location loc = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1.0, player.getLocation().getZ());
				loc.getWorld().strikeLightningEffect(loc);
			}
		}
	}
}
