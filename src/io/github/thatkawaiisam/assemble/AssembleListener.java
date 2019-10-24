package io.github.thatkawaiisam.assemble;

import io.github.thatkawaiisam.assemble.events.AssembleBoardCreateEvent;
import io.github.thatkawaiisam.assemble.events.AssembleBoardDestroyEvent;
import lombok.Getter;
import me.ezjamo.managers.PlayerdataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Getter
public class AssembleListener implements Listener {

	private Assemble assemble;

	public AssembleListener(Assemble assemble) {
		this.assemble = assemble;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerdataManager data = new PlayerdataManager(player.getUniqueId());
		if (data.loadUser().getString(player.getUniqueId().toString() + ".scoreboard") == null) {
			AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);
			Bukkit.getPluginManager().callEvent(createEvent);
			if (createEvent.isCancelled()) {
				return;
			}
			getAssemble().getBoards().put(player.getUniqueId(), new AssembleBoard(player, getAssemble()));
			return;
		}
		if (data.loadUser().getString(player.getUniqueId().toString() + ".scoreboard").equals("disabled")) return;
		AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);
		Bukkit.getPluginManager().callEvent(createEvent);
		if (createEvent.isCancelled()) {
			return;
		}
		getAssemble().getBoards().put(player.getUniqueId(), new AssembleBoard(player, getAssemble()));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		AssembleBoardDestroyEvent destroyEvent = new AssembleBoardDestroyEvent(player);
		Bukkit.getPluginManager().callEvent(destroyEvent);
		if (destroyEvent.isCancelled()) {
			return;
		}
		getAssemble().getBoards().remove(player.getUniqueId());
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}

	//TODO see how we can make this better
//	@EventHandler
//	public void onPluginDisable(PluginDisableEvent event) {
//		getAssemble().disable();
//	}

}