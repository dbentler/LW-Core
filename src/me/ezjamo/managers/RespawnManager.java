package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import me.ezjamo.Lonewolves;

public class RespawnManager implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		boolean enabled = Lonewolves.plugin.getConfig().getBoolean("instant-respawn");
		if (enabled) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Lonewolves.plugin, () -> event.getEntity().spigot().respawn(), 1L);
		}
	}
	
	@EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        SpawnManager spawnCoords = SpawnManager.getManager();
        if (fPlayer.hasFaction() && fPlayer.getFaction().hasHome()) {
			World homeW = fPlayer.getFaction().getHome().getWorld();
			double homeX = fPlayer.getFaction().getHome().getX();
			double homeY = fPlayer.getFaction().getHome().getY();
			double homeZ = fPlayer.getFaction().getHome().getZ();
			float homeYaw = fPlayer.getFaction().getHome().getYaw();
			float homePitch = fPlayer.getFaction().getHome().getPitch();
			Location home = new Location(homeW, homeX, homeY, homeZ, homeYaw, homePitch);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lonewolves.plugin, () -> player.teleport(home), 1L);
		}
        else if (spawnCoords.getConfig().getConfigurationSection("spawn") != null) {
			World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("spawn.world"));
			double x = spawnCoords.getConfig().getDouble("spawn.x");
			double y = spawnCoords.getConfig().getDouble("spawn.y");
			double z = spawnCoords.getConfig().getDouble("spawn.z");
			float yaw = (float)spawnCoords.getConfig().getDouble("spawn.yaw");
			float pitch = (float)spawnCoords.getConfig().getDouble("spawn.pitch");
			Location loc = new Location(w, x, y, z, yaw, pitch);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lonewolves.plugin, () -> player.teleport(loc), 1L);
		}
	}
}
