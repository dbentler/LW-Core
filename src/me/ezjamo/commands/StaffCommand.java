package me.ezjamo.commands;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;
import me.ezjamo.managers.PlayerdataManager;
import me.ezjamo.managers.TimeFormat;
import me.ezjamo.managers.UUIDFetcher;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.HeldPermission;
import me.lucko.luckperms.api.LuckPermsApi;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

public class StaffCommand extends Utils implements CommandExecutor {
    private static Utils utils = new Utils();
    private LuckPermsApi api = LuckPerms.getApi();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            message(sender, "&cOnly players may use this command!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 0) {
            message(player, "&cUsage: &7/staff");
            return true;
        }
        staffInv(player);
        return true;
    }

    private void staffInv(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Lonewolves.plugin, () -> {
            int staffCount = 0;
            for (HeldPermission<UUID> users : api.getUserManager().getWithPermission("rank.helper").join()) {
                if (users.getPermission().contains("rank.helper")) {
                    ++staffCount;
                }
            }
            Inventory inv;
            if (staffCount > 27) {
                inv = Bukkit.createInventory(null, 54, color("&f&lLone&4&lWolves &f&lStaff"));
            } else {
                inv = Bukkit.createInventory(null, 9*4, color("&f&lLone&4&lWolves &f&lStaff"));
            }
            int owner = 0;
            int admin = 9;
            int mod = 18;
            int helper = 27;
            for (HeldPermission<UUID> users : api.getUserManager().getWithPermission("rank.helper").join()) {
                if (users.getPermission().contains("rank.helper")) {
                    Player userOnline = Bukkit.getPlayer(users.getHolder());
                    OfflinePlayer user = Bukkit.getOfflinePlayer(users.getHolder());

                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    skullMeta.setOwner(user.getName());
                    skullMeta.setDisplayName(color("&f" + user.getName()));
                    List<String> lore = new ArrayList<>();

                    String group = Lonewolves.perms.getPrimaryGroup("world", user);

                    UUID target;
                    target = UUIDFetcher.getUUID(user.getName());

                    Date date = new Date(user.getLastPlayed());
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yy 'at' h:mm a z");
                    ZoneId id = ZoneId.of("America/New_York");
                    formatter.setTimeZone(TimeZone.getTimeZone(id));

                    if (group.equalsIgnoreCase("owner")) {
                        group = color("&4Owner");
                        lore.add(color("&7Rank: " + group));
                        if (userOnline != null) {
                            lore.add(color("&7Playtime: " + onlinePlaytime(userOnline)));
                        }
                        if (userOnline == null) {
                            lore.add(color("&7Playtime: " + offlinePlaytime(target)));
                            lore.add(color("&7Last Online: &c" + formatter.format(date)));
                        }
                        skullMeta.setLore(lore);
                        skull.setItemMeta(skullMeta);
                        ++owner;
                        inv.setItem(owner - 1, skull);
                    }

                    if (group.equalsIgnoreCase("admin")) {
                        group = color("&cAdmin");
                        lore.add(color("&7Rank: " + group));
                        if (userOnline != null) {
                            lore.add(color("&7Playtime: " + onlinePlaytime(userOnline)));
                        }
                        if (userOnline == null) {
                            lore.add(color("&7Playtime: " + offlinePlaytime(target)));
                            lore.add(color("&7Last Online: &c" + formatter.format(date)));
                        }
                        skullMeta.setLore(lore);
                        skull.setItemMeta(skullMeta);
                        ++admin;
                        inv.setItem(admin - 1, skull);
                    }

                    if (group.equalsIgnoreCase("mod")) {
                        group = color("&9Moderator");
                        lore.add(color("&7Rank: " + group));
                        if (userOnline != null) {
                            lore.add(color("&7Playtime: " + onlinePlaytime(userOnline)));
                        }
                        if (userOnline == null) {
                            lore.add(color("&7Playtime: " + offlinePlaytime(target)));
                            lore.add(color("&7Last Online: &c" + formatter.format(date)));
                        }
                        skullMeta.setLore(lore);
                        skull.setItemMeta(skullMeta);
                        ++mod;
                        inv.setItem(mod - 1, skull);
                    }

                    if (group.equalsIgnoreCase("helper")) {
                        group = color("&dHelper");
                        lore.add(color("&7Rank: " + group));
                        if (userOnline != null) {
                            lore.add(color("&7Playtime: " + onlinePlaytime(userOnline)));
                        }
                        if (userOnline == null) {
                            lore.add(color("&7Playtime: " + offlinePlaytime(target)));
                            lore.add(color("&7Last Online: &c" + formatter.format(date)));
                        }
                        skullMeta.setLore(lore);
                        skull.setItemMeta(skullMeta);
                        ++helper;
                        inv.setItem(helper - 1, skull);
                    }
                }
            }
            player.openInventory(inv);
        });
    }

    private static String onlinePlaytime(Player target) {
        return utils.color("&f" + TimeFormat.getTime(target.getStatistic(Statistic.PLAY_ONE_TICK) / 20));
    }

    private static String offlinePlaytime(UUID target) {
        return utils.color("&f" + TimeFormat.getTime(PlayerdataManager.getPlayerStatistic(target, "PLAYTIME", Statistic.PLAY_ONE_TICK) / 20L));
    }
}
