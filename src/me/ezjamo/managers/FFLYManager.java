package me.ezjamo.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class FFLYManager implements Listener {
	
    @EventHandler
    public void onFly(PlayerMoveEvent e) {
         if (!e.getPlayer().isFlying() || e.getFrom().getBlockY() == e.getTo().getBlockY()) 
             return;
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(e.getPlayer());
        if (fPlayer.isInNeutralTerritory()) {
            fPlayer.setFlying(false);
        }
    }

}
