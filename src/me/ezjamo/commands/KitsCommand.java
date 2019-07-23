package me.ezjamo.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;
 
public class KitsCommand implements CommandExecutor {
   
	
    public static void kits(Player player) {
        Inventory kits = Bukkit.createInventory(null, 45, Utils.chat("&fLone&4Wolves &fKits"));
        if (player.hasPermission("essentials.kits.vip")) {
            String unsetVIP = "%essentials_kit_time_until_available_vip%";
            String setVIP = PlaceholderAPI.setPlaceholders(player, unsetVIP);
            if (!unsetVIP.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 11, "&eVIP", "&7Available In: &a" + setVIP);
            }
            if (setVIP.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 4, 1, 11, "&eVIP", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.vip")) {
            Utils.createItemByte(kits, 160, 15, 1, 11, "&4VIP", "&7Status: &4Unavailable", "&7Right click to preview.");
        }
        
        if (player.hasPermission("essentials.kits.vip+")) {
            String unsetVIPPlus = "%essentials_kit_time_until_available_vip+%";
            String setVIPPlus = PlaceholderAPI.setPlaceholders(player, unsetVIPPlus);
            if (!unsetVIPPlus.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 13, "&bVIP+", "&7Available In: &a" + setVIPPlus);
            }
            if (setVIPPlus.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 3, 1, 13, "&bVIP+", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.vip+")) {
            Utils.createItemByte(kits, 160, 15, 1, 13, "&4VIP+", "&7Status: &4Unavailable", "&7Right click to preview.");
        }
        
        if (player.hasPermission("essentials.kits.mvp")) {
            String unsetMVP = "%essentials_kit_time_until_available_mvp%";
            String setMVP = PlaceholderAPI.setPlaceholders(player, unsetMVP);
            if (!unsetMVP.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 15, "&cMVP", "&7Available In: &a" + setMVP);
            }
            if (setMVP.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 14, 1, 15, "&cMVP", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.mvp")) {
            Utils.createItemByte(kits, 160, 15, 1, 15, "&4MVP", "&7Status: &4Unavailable", "&7Right click to preview.");
        }
        
        if (player.hasPermission("essentials.kits.noble")) {
            String unsetNoble = "%essentials_kit_time_until_available_noble%";
            String setNoble = PlaceholderAPI.setPlaceholders(player, unsetNoble);
            if (!unsetNoble.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 29, "&3Noble", "&7Available In: &a" + setNoble);
            }
            if (setNoble.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 9, 1, 29, "&3Noble", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.noble")) {
            Utils.createItemByte(kits, 160, 15, 1, 29, "&4Noble", "&7Status: &4Unavailable", "&7Right click to preview.");
        }
        
        if (player.hasPermission("essentials.kits.mystic")) {
            String unsetMystic = "%essentials_kit_time_until_available_mystic%";
            String setMystic = PlaceholderAPI.setPlaceholders(player, unsetMystic);
            if (!unsetMystic.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 31, "&5Mystic", "&7Available In: &a" + setMystic);
            }
            if (setMystic.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 10, 1, 31, "&5Mystic", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.mystic")) {
            Utils.createItemByte(kits, 160, 15, 1, 31, "&4Mystic", "&7Status: &4Unavailable", "&7Right click to preview.");
        }
        
        if (player.hasPermission("essentials.kits.kingpin")) {
            String unsetKingpin = "%essentials_kit_time_until_available_kingpin%";
            String setKingpin = PlaceholderAPI.setPlaceholders(player, unsetKingpin);
            if (!unsetKingpin.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 33, "&2Kingpin", "&7Available In: &a" + setKingpin);
            }
            if (setKingpin.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 13, 1, 33, "&2Kingpin", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.kingpin")) {
            Utils.createItemByte(kits, 160, 15, 1, 33, "&4Kingpin", "&7Status: &4Unavailable", "&7Right click to preview.");
        }
        
        if (player.hasPermission("essentials.kits.member")) {
            String unsetMember = "%essentials_kit_time_until_available_member%";
            String setMember = PlaceholderAPI.setPlaceholders(player, unsetMember);
            if (!unsetMember.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 0, "&7Member", "&7Available In: &a" + setMember);
            }
            if (setMember.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 8, 1, 0, "&7Member", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.member")) {
        	Utils.createItemByte(kits, 160, 15, 1, 0, "&4Member", "&7Status: &4Unavailable", "&7Right click to preview.");
        }
        
        if (player.hasPermission("essentials.kits.beta")) {
            String unsetBeta = "%essentials_kit_time_until_available_beta%";
            String setBeta = PlaceholderAPI.setPlaceholders(player, unsetBeta);
            if (!unsetBeta.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 8, "&6Beta", "&7Available In: &a" + setBeta);
            }
            if (setBeta.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 1, 1, 8, "&6Beta", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.beta")) {
        	Utils.createItemByte(kits, 160, 15, 1, 8, "&4Beta", "&7Status: &4Unavailable", "&7Right click to preview.");
        }
        
        if (player.hasPermission("essentials.kits.tournament")) {
            String unsetTournament = "%essentials_kit_time_until_available_tournament%";
            String setTournament = PlaceholderAPI.setPlaceholders(player, unsetTournament);
            if (!unsetTournament.equalsIgnoreCase("0")) {
            	Utils.createItemByte(kits, 160, 1, 1, 44, "&aTournament", "&7Available In: &a" + setTournament);
            }
            if (setTournament.equalsIgnoreCase("0")) {
            Utils.createItemByte(kits, 160, 6, 1, 44, "&aTournament", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.tournament")) {
        }
        
        if (player.hasPermission("essentials.kits.youtuber")) {
            String unsetYoutuber = "%essentials_kit_time_until_available_youtuber%";
            String setYoutuber = PlaceholderAPI.setPlaceholders(player, unsetYoutuber);
            if (!unsetYoutuber.equalsIgnoreCase("0")) {
            	Utils.createItem(kits, 160, 1, 36, "&fYou&4Tuber", "&7Available In: &a" + setYoutuber);
            }
            if (setYoutuber.equalsIgnoreCase("0")) {
            Utils.createItem(kits, 160, 1, 36, "&fYou&4Tuber", "&7Status: &aAvailable");
            }
        }
        if (!player.hasPermission("essentials.kits.beta")) {
        }
        player.openInventory(kits);
    }
   
 
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("kits")) {
        if (player.hasPermission("lw.kits"))
            KitsCommand.kits(player);
        }
        if (label.equalsIgnoreCase("kit")) {
            if (player.hasPermission("lw.kits"))
                KitsCommand.kits(player);
            }
        if(!player.hasPermission("lw.kits"))
            player.sendMessage(Lonewolves.NO_PERMS);
        return false;
    }
}