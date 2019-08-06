package me.ezjamo.managers;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.ezjamo.commands.Modmode;
import me.ezjamo.commands.VanishCommand;

@SuppressWarnings("deprecation")
public class ModModeManager implements Listener {
    int clickcount;
    private static ArrayList<Player> players;
    private static ItemStack vanishv;
    private static ItemMeta vanishmv;
    private static ItemStack vanishnv;
    private static ItemMeta vanishmnv;
    
    static {
        ModModeManager.setPlayers(new ArrayList<Player>());
        ModModeManager.vanishv = new ItemStack(351, 1, (short)10);
        ModModeManager.vanishmv = ModModeManager.vanishv.getItemMeta();
        ModModeManager.vanishnv = new ItemStack(351, 1, (short)1);
        ModModeManager.vanishmnv = ModModeManager.vanishnv.getItemMeta();
    }
    
    public ModModeManager() {
        this.clickcount = 0;   
    }
    
    
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (Modmode.modmode.contains(((Player)e.getDamager()).getName())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void invTake(InventoryClickEvent e) {
        if (e.getInventory() == null) {
            return;
        }
        if (e.getWhoClicked() == null) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        if (Modmode.modmode.contains(e.getWhoClicked().getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta() != null && e.getInventory().getTitle().contains(ChatColor.translateAlternateColorCodes('&', "&cInventory of"))) {
                Player target = Bukkit.getPlayer(e.getInventory().getTitle().substring("&cInventory of &e".length()));
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&bFreeze Player"))) {
                    if (Modmode.modmode.contains(target.getName())) {
                        (e.getWhoClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat person is in staffmode, this action can not be completed at this time..."));
                        return;
                    }
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You have been frozen by a staff member. Please join our &cDiscord &7at www.lonewolves.net/discord"));
                    ((Player)e.getWhoClicked()).performCommand("freeze " + target.getName());
                    (e.getWhoClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&',  "&cYou have frozen " + target.getName() + "."));
                }
                else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cPerm Ban Player"))) {
                    if (Modmode.modmode.contains(target.getName())) {
                        (e.getWhoClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat person is in mod mode the action will not be performed."));
                        return;
                    }
                    ((Player)e.getWhoClicked()).performCommand("ban " + target.getName() + " Cheating or Breaking our Terms of Service.");
                    (e.getWhoClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Successfully banned player &e" + target.getName() + "&c..."));
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cPunish Player"))) {
                	((Player)e.getWhoClicked()).performCommand("punish " + target.getName());
                	return;
                }
                else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&eClear inventory"))) {
                    ++this.clickcount;
                    if (this.clickcount == 1) {
                        (e.getWhoClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cClick on the item &1&ltwo &cmore times to confirm the clearing of &e" + target.getName() + "&cinventory."));
                    }
                    if (this.clickcount == 2) {
                        (e.getWhoClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cClick on the item &7&lone &cmore times to confirm the clearing of &e" + target.getName() + "&cinventory."));
                    }
                    if (this.clickcount >= 3) {
                        if (Modmode.modmode.contains(target.getName())) {
                            (e.getWhoClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat person is in staffmode, this action can not be completed at this time..."));
                            return;
                        }
                        target.getInventory().clear();
                        (e.getWhoClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCleared &e" + target.getName() + "&c's inventory."));
                        this.clickcount = 0;
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
    	Player p = (Player) e.getPlayer();
    	if (Modmode.modmode.contains(e.getPlayer().getName())) {
    		Modmode.modmode.remove(e.getPlayer().getName());
    		ModModeManager.remove(p);
    		e.getPlayer().performCommand("inv prev");
    			}
    		}
   
    
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (Modmode.modmode.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (Modmode.modmode.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void placeBl(BlockPlaceEvent e) {
        if (e.getBlock() == null) {
            return;
        }
        if (Modmode.modmode.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void breakBl(BlockBreakEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        if (e.getBlock() == null) {
            return;
        }
        if (Modmode.modmode.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }
    
	@EventHandler
    public void rightClick(PlayerInteractEntityEvent e) {
        Inventory i = null;
        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }
        Player player = e.getPlayer();
        Player target = (Player)e.getRightClicked();
        if (target.getInventory() == null) {
            return;
        }
        if (Modmode.modmode.contains(player.getName()) && target instanceof Player && player instanceof Player && player.getItemInHand().getType() == Material.BOOK && player.getItemInHand().hasItemMeta()) {
            i = Bukkit.createInventory(target, 54, ChatColor.translateAlternateColorCodes('&', "&cInventory of &e" + target.getName()));
            if (target.getInventory().getHelmet() != null) {
                i.setItem(0, target.getInventory().getHelmet());
            }
            if (target.getInventory().getChestplate() != null) {
                i.setItem(1, target.getInventory().getChestplate());
            }
            if (target.getInventory().getLeggings() != null) {
                i.setItem(2, target.getInventory().getLeggings());
            }
            if (target.getInventory().getBoots() != null) {
                i.setItem(3, target.getInventory().getBoots());
            }
            if (target.getItemInHand() != null) {
                i.setItem(4, target.getItemInHand());
            }
            int a = 0;
            for (int ix = 9; ix < target.getInventory().getSize() + 9; ++ix) {
                i.setItem(ix, target.getInventory().getItem(ix - 9));
                a = ix;
            }
            ItemStack ist = new ItemStack(Material.BLAZE_POWDER);
            ItemMeta imt = ist.getItemMeta();
    		imt.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eClear Inventory"));
            ist.setItemMeta(imt);
            i.setItem(a + 1, ist);
            ItemStack is = new ItemStack(Material.PACKED_ICE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bFreeze Player"));
            is.setItemMeta(im);
            i.setItem(a + 2, is);
			ItemStack is2 = new ItemStack(351, 1, (short)1);
            ItemMeta im2 = is2.getItemMeta();
            im2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cPerm Ban Player"));
            is2.setItemMeta(im2);
            i.setItem(a + 9, is2);
			ItemStack is3 = new ItemStack(Material.ANVIL);
            ItemMeta im3 = is2.getItemMeta();
            im3.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cPunish Player"));
            is3.setItemMeta(im3);
            i.setItem(a + 8, is3);
            player.openInventory(i);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eOpening the inventory of &c" + target.getName() + "..."));
        }
    }
     
    @EventHandler
    public void items(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() == null) {
            return;
        }
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getClickedBlock() == null) {
            return;
        }
        if (e.getPlayer().getInventory().getItemInHand() == null) {
            return;
        }
        if (!Modmode.modmode.contains(e.getPlayer().getName()) || e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getItemMeta() == null || !e.getPlayer().getItemInHand().hasItemMeta()) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', "&aVanished"))) {
            e.getPlayer().getInventory().setItem(8, ModModeManager.vanishnv);
            e.getPlayer().performCommand("vanish");
            return;
        }
        if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', "&7&oVisible"))) {
            e.getPlayer().getInventory().setItem(8, ModModeManager.vanishv);
            e.getPlayer().performCommand("vanish");
            return;
        }
        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', "&9Online Miners"))) {
        	xrayers(e.getPlayer());
        }
        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', "&9Random Teleport"))) {
        	Random r = new Random();
            for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                players.add(online);
            }
            int index = r.nextInt(players.size());
            Player loc = (Player) players.get(index);
            e.getPlayer().teleport(loc);
        }
        else if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', "&9Online Staff Members"))) {
        	e.getPlayer().openInventory(this.setupStaffInv());
        }
    }
    
    private Inventory setupStaffInv() {
        int staffcount = 0;
        for (Player p4 : Bukkit.getOnlinePlayers()) {
            if (p4.isOp() || p4.hasPermission("lw.mod")) {
                ++staffcount;
            }
        }
        Inventory k = null;
        if (staffcount > 27) {
            k = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&9Online Staff Members"));
        }
        else {
            k = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&9Online Staff Members"));
        }
        int cout = 0;
        for (Player p5 : Bukkit.getOnlinePlayers()) {
            if (p5.isOp() || p5.hasPermission("lw.mod")) {
                ItemStack staff = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
                SkullMeta staffm = (SkullMeta)staff.getItemMeta();
                staffm.setOwner(p5.getName());
                staffm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9" + p5.getName()));
                staff.setItemMeta((ItemMeta)staffm);
                ++cout;
                k.setItem(cout - 1, staff);
            }
        }
        return k;
    }
    
    
    
	public void xrayers(Player p) {
		
		 Inventory inv = Bukkit.getServer().createInventory(null, 45, ChatColor.BLUE + "Online Miners");
		 int cout = 0;
		 for (Player miners : Bukkit.getOnlinePlayers()) {
			 if (miners.getLocation().getY() < 35) {
	                ItemStack xray = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
	                SkullMeta xraymeta = (SkullMeta)xray.getItemMeta();
	                xraymeta.setOwner(miners.getName());
	                xraymeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9" + miners.getName()));
	                xray.setItemMeta(xraymeta); 
	                ++cout;
	                inv.setItem(cout - 1, xray);
			 	}
		 	}
	       		 p.getPlayer().openInventory(inv);
		 
		}

   
    
    public static void put(Player p) {
        ModModeManager.vanishmv.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aVanished"));
        ModModeManager.vanishv.setItemMeta(ModModeManager.vanishmv);
        ModModeManager.vanishmnv.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&oVisible"));
        ModModeManager.vanishnv.setItemMeta(ModModeManager.vanishmnv);
        p.getInventory().clear();
        if (!p.getGameMode().equals(GameMode.CREATIVE) && p.hasPermission("lw.mod")) {
            p.setGameMode(GameMode.CREATIVE);
        }
        ItemStack miner = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta minerMeta = miner.getItemMeta();
        minerMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Online Miners"));
        miner.setItemMeta(minerMeta);
		ItemStack invinspect = new ItemStack(340);
        ItemMeta invinspectm = invinspect.getItemMeta();
        invinspectm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Inventory Inspector"));
        invinspect.setItemMeta(invinspectm);
        ItemStack toolrandom = new ItemStack(Material.NETHER_STAR);
        ItemMeta toolrandomm = toolrandom.getItemMeta();
        toolrandomm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Random Teleport"));
        toolrandom.setItemMeta(toolrandomm);
		ItemStack staff = new ItemStack(Material.ENDER_CHEST);
        ItemMeta staffrm = staff.getItemMeta();
        staffrm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Online Staff Members"));
        staff.setItemMeta(staffrm);
        p.getInventory().setItem(0, miner);
        p.getInventory().setItem(2, invinspect);
        p.getInventory().setItem(6, staff);
        p.getInventory().setItem(4, toolrandom);
        if (VanishCommand.vanish.contains(p)) {
        	p.getInventory().setItem(8, ModModeManager.vanishv);
        }
        if (!VanishCommand.vanish.contains(p)) {
        	p.getInventory().setItem(8, ModModeManager.vanishnv);
        }
    }

    public static void remove(Player p) {
        if (!p.getGameMode().equals(GameMode.SURVIVAL)) {
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
        }	
    }


	public static ArrayList<Player> getPlayers() {
		return players;
	}


	public static void setPlayers(ArrayList<Player> players) {
		ModModeManager.players = players;
	}	
}
