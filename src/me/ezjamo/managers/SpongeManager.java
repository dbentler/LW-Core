package me.ezjamo.managers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpongeManager implements Listener {
	
	private int radius;
	private int radiusupdate;
	
	public SpongeManager() {
		this.radius = 3;
		this.radiusupdate = 2;
	}
	
    
	@EventHandler(priority = EventPriority.HIGHEST)
    private void onPlace(BlockPlaceEvent evt) {
        if (!evt.isCancelled()) {
            final Block block = evt.getBlock();
            if (block.getType().equals(Material.SPONGE)) {
                     int bx = block.getX();
                     int by = block.getY();
                     int bz = block.getZ();
                    for (int fx = -this.radiusupdate; fx <= this.radiusupdate; ++fx) {
                        for (int fy = -this.radiusupdate; fy <= this.radiusupdate; ++fy) {
                            for (int fz = -this.radiusupdate; fz <= this.radiusupdate; ++fz) {
                                final Block b = evt.getBlock().getWorld().getBlockAt(bx + fx, by + fy, bz + fz);
                                if (b.getType().equals(Material.STATIONARY_WATER) || b.getType().equals(Material.WATER)) {
                                    b.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }
        }
    
    
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onFlow(BlockFromToEvent evt) {
        Block block = evt.getBlock();
        if (block.getType().equals(Material.WATER) || block.getType().equals(Material.STATIONARY_WATER)) {
             int bx = block.getX();
             int by = block.getY();
             int bz = block.getZ();
            for (int fx = -this.radius; fx <= this.radius; ++fx) {
                for (int fy = -this.radius; fy <= this.radius; ++fy) {
                    for (int fz = -this.radius; fz <= this.radius; ++fz) {
                        if (evt.getBlock().getWorld().getBlockAt(bx + fx, by + fy, bz + fz).getType().equals(Material.SPONGE)) {
                            evt.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
