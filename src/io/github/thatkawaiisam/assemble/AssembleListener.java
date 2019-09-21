package io.github.thatkawaiisam.assemble;

import io.github.thatkawaiisam.assemble.events.AssembleBoardCreateEvent;
import io.github.thatkawaiisam.assemble.events.AssembleBoardDestroyEvent;
import lombok.Getter;
import me.ezjamo.managers.PlayerdataManager;

import org.bukkit.Bukkit;
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
		PlayerdataManager data = PlayerdataManager.getManager();
		if (data.get().get("players." + event.getPlayer().getUniqueId().toString() + ".scoreboard") == null) {
			AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(event.getPlayer());

			Bukkit.getPluginManager().callEvent(createEvent);
			if (createEvent.isCancelled()) {
				return;
			}

			getAssemble().getBoards().put(event.getPlayer().getUniqueId(), new AssembleBoard(event.getPlayer(), getAssemble()));
			return;
		}
		if (data.get().get("players." + event.getPlayer().getUniqueId().toString() + ".scoreboard").equals("disabled")) {
			return;
		}
		else {
			AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(event.getPlayer());

			Bukkit.getPluginManager().callEvent(createEvent);
			if (createEvent.isCancelled()) {
				return;
			}

			getAssemble().getBoards().put(event.getPlayer().getUniqueId(), new AssembleBoard(event.getPlayer(), getAssemble()));
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		PlayerdataManager data = PlayerdataManager.getManager();
		if (data.get().get("players." + event.getPlayer().getUniqueId().toString() + ".scoreboard") == null) {
			AssembleBoardDestroyEvent destroyEvent = new AssembleBoardDestroyEvent(event.getPlayer());

			Bukkit.getPluginManager().callEvent(destroyEvent);
			if (destroyEvent.isCancelled()) {
				return;
			}

			getAssemble().getBoards().remove(event.getPlayer().getUniqueId());
			event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
			return;
		}
		if (data.get().get("players." + event.getPlayer().getUniqueId().toString() + ".scoreboard").equals("disabled")) {
			return;
		}
		else {
			AssembleBoardDestroyEvent destroyEvent = new AssembleBoardDestroyEvent(event.getPlayer());

			Bukkit.getPluginManager().callEvent(destroyEvent);
			if (destroyEvent.isCancelled()) {
				return;
			}

			getAssemble().getBoards().remove(event.getPlayer().getUniqueId());
			event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		}
	}

	//TODO see how we can make this better
//	@EventHandler
//	public void onPluginDisable(PluginDisableEvent event) {
//		getAssemble().disable();
//	}

}