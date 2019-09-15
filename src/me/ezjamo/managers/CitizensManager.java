package me.ezjamo.managers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class CitizensManager implements Listener {
	
	@EventHandler
	public void onShopClick1(NPCRightClickEvent event){
		NPC npc = event.getNPC();
		Player p = (Player) event.getClicker();
		if (npc.getName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Shop")) {
			p.performCommand("shop");
		}
	}


	@EventHandler
	public void onShopClick2(NPCLeftClickEvent event){
		NPC npc = event.getNPC();
		Player p = (Player) event.getClicker();
		if (npc.getName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Shop")) {
			p.performCommand("shop");
		}
	}
}
