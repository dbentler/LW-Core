package me.ezjamo.managers;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class SpongeManager implements Listener {
	
	private int radius;
	private int radiusupdate;
	
	public SpongeManager() {
		this.radius = 3;
		this.radiusupdate = 2;
	}
	
    @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
    private void onInteract(final PlayerInteractEvent evt) {
        if (!evt.isCancelled() && !evt.getPlayer().getGameMode().equals((Object)GameMode.CREATIVE) && evt.getAction().equals((Object)Action.LEFT_CLICK_BLOCK)) {
            final Block block = evt.getClickedBlock();
            final FPlayer fplayer = FPlayers.getInstance().getByPlayer(evt.getPlayer());
            if ((fplayer.isInOwnTerritory() || Board.getInstance().getFactionAt(new FLocation(block)).isNone()) && block != null && block.getType().equals((Object)Material.SPONGE)) {
                evt.getClickedBlock().breakNaturally();
                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, 19);
            }
        }
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
    private void onPlace(final BlockPlaceEvent evt) {
        if (!evt.isCancelled()) {
            final Block block = evt.getBlock();
            if (block.getType().equals((Object)Material.SPONGE)) {
                final FPlayer fplayer = FPlayers.getInstance().getByPlayer(evt.getPlayer());
                if (fplayer.isInOwnTerritory() || Board.getInstance().getFactionAt(new FLocation(block)).isNone()) {
                    final int bx = block.getX();
                    final int by = block.getY();
                    final int bz = block.getZ();
                    for (int fx = -this.radiusupdate; fx <= this.radiusupdate; ++fx) {
                        for (int fy = -this.radiusupdate; fy <= this.radiusupdate; ++fy) {
                            for (int fz = -this.radiusupdate; fz <= this.radiusupdate; ++fz) {
                                final Block b = evt.getBlock().getWorld().getBlockAt(bx + fx, by + fy, bz + fz);
                                if (b.getType().equals((Object)Material.LAVA) || b.getType().equals((Object)Material.STATIONARY_LAVA) || b.getType().equals((Object)Material.STATIONARY_WATER) || b.getType().equals((Object)Material.WATER)) {
                                    b.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onFlow(final BlockFromToEvent evt) {
        final Block block = evt.getBlock();
        if (block.getType().equals(Material.WATER) || block.getType().equals(Material.STATIONARY_WATER)) {
            final int bx = block.getX();
            final int by = block.getY();
            final int bz = block.getZ();
            for (int fx = -this.radius; fx <= this.radius; ++fx) {
                for (int fy = -this.radius; fy <= this.radius; ++fy) {
                    for (int fz = -this.radius; fz <= this.radius; ++fz) {
                        if (evt.getBlock().getWorld().getBlockAt(bx + fx, by + fy, bz + fz).getType().equals((Object)Material.SPONGE)) {
                            evt.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
