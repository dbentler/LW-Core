package me.ezjamo.managers;

import static com.comphenix.protocol.PacketType.Play.Server.ABILITIES;
import static com.comphenix.protocol.PacketType.Play.Server.ENTITY_METADATA;
import static com.comphenix.protocol.PacketType.Play.Server.GAME_STATE_CHANGE;
import static com.comphenix.protocol.PacketType.Play.Server.PLAYER_INFO;
import static org.bukkit.Material.CHEST;
import static org.bukkit.Material.ENDER_CHEST;
import static org.bukkit.Material.TRAPPED_CHEST;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.google.common.collect.ImmutableList;

import lombok.Data;
import me.ezjamo.Lonewolves;

public class SilentOpenChest implements Listener {

    private final Map<Player, StateInfo> playerStateInfoMap = new HashMap<>();

    public void onDisable() {
        for (Player p : playerStateInfoMap.keySet()) {
            StateInfo stateInfo = playerStateInfoMap.remove(p);
            if (stateInfo == null) continue;
            restoreState(stateInfo, p);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpectatorClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        Player p = (Player) e.getWhoClicked();
        File file = new File("plugins//LW-Essentials//Vanished//" + p.getName() + ".yml");
        if (!file.exists()) return;
        if (!playerStateInfoMap.containsKey(p)) return;
        if (p.getGameMode() != GameMode.SURVIVAL && p.getGameMode() != GameMode.ADVENTURE
                && p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        StateInfo stateInfo = playerStateInfoMap.remove(p);
        if (stateInfo == null) return;
        restoreState(stateInfo, p);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (playerStateInfoMap.containsKey(p)
                && e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if (playerStateInfoMap.containsKey(p) && e.getNewGameMode() != GameMode.SPECTATOR) {
            // Don't let low-priority event listeners cancel the gamemode change
            if (e.isCancelled()) e.setCancelled(false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChestInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        File file = new File("plugins//LW-Essentials//Vanished//" + p.getName() + ".yml");
        if (!file.exists()) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (p.getGameMode() == GameMode.SPECTATOR) return;
        //noinspection deprecation
        if (p.isSneaking() && p.getItemInHand() != null
                && p.getItemInHand().getType().isBlock()
                && p.getItemInHand().getType() != Material.AIR)
            return;
        Block block = e.getClickedBlock();
        if (block == null) return;
        if (block.getType() == ENDER_CHEST) {
            e.setCancelled(true);
            p.openInventory(p.getEnderChest());
            return;
        }
        if (!(block.getType() == CHEST || block.getType() == TRAPPED_CHEST))
            return;
        StateInfo stateInfo = StateInfo.extract(p);
        playerStateInfoMap.put(p, stateInfo);
        p.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChestClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player))
            return;
        final Player p = (Player) e.getPlayer();
        if (!playerStateInfoMap.containsKey(p)) return;
        if (!(p.getInventory().getType() == InventoryType.CHEST))
        new BukkitRunnable() {
            @Override
            public void run() {
                StateInfo stateInfo = playerStateInfoMap.get(p);
                if (stateInfo == null) return;
                restoreState(stateInfo, p);
                playerStateInfoMap.remove(p);
            }
        }.runTaskLater(Lonewolves.plugin, 1);
    }

    private void restoreState(StateInfo stateInfo, Player p) {
        p.setGameMode(stateInfo.gameMode);
        p.setAllowFlight(stateInfo.canFly);
        p.setFlying(stateInfo.isFlying);
    }

    public boolean hasSilentlyOpenedChest(Player p) {
        return playerStateInfoMap.containsKey(p);
    }

    public void onEnable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(Lonewolves.plugin, ListenerPriority.LOW, PLAYER_INFO, GAME_STATE_CHANGE, ABILITIES,
                        ENTITY_METADATA) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        try {
                            if (event.getPacketType() == PLAYER_INFO) {
                                // multiple events share same packet object
                                event.setPacket(event.getPacket().shallowClone());

                                List<PlayerInfoData> infoDataList = new ArrayList<>(
                                        event.getPacket().getPlayerInfoDataLists().read(0));
                                Player receiver = event.getPlayer();
                                File file = new File("plugins//LW-Essentials//Vanished//" + receiver.getName() + ".yml");
                                for (PlayerInfoData infoData : ImmutableList.copyOf(infoDataList)) {
                                    if (!file.exists()
                                            && file.exists()) {
                                        Player vanishedTabPlayer = Bukkit.getPlayer(infoData.getProfile().getUUID());
                                        if (infoData.getGameMode() == EnumWrappers.NativeGameMode.SPECTATOR
                                                && hasSilentlyOpenedChest(vanishedTabPlayer)
                                                && event.getPacket().getPlayerInfoAction().read(0)
                                                == EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE) {
                                            int latency;
                                            try {
                                                latency = infoData.getLatency();
                                            } catch (NoSuchMethodError e) {
                                                latency = 21;
                                            }
                                            PlayerInfoData newData = new PlayerInfoData(infoData.getProfile(),
                                                    latency, EnumWrappers.NativeGameMode.SURVIVAL,
                                                    infoData.getDisplayName());
                                            infoDataList.remove(infoData);
                                            infoDataList.add(newData);
                                        }
                                    }
                                }
                                event.getPacket().getPlayerInfoDataLists().write(0, infoDataList);
                            } else if (event.getPacketType() == GAME_STATE_CHANGE) {
                            	File file = new File("plugins//LW-Essentials//Vanished//" + event.getPlayer().getName() + ".yml");
                                if (file.exists()) {
                                    if (event.getPacket().getIntegers().read(0) != 3) return;
                                    if (!hasSilentlyOpenedChest(event.getPlayer())) return;
                                    event.setCancelled(true);
                                }
                            } else if (event.getPacketType() == ABILITIES) {
                            	File file = new File("plugins//LW-Essentials//Vanished//" + event.getPlayer().getName() + ".yml");
                                if (file.exists()) {
                                    if (!hasSilentlyOpenedChest(event.getPlayer())) return;
                                    event.setCancelled(true);
                                }
                            } else if (event.getPacketType() == ENTITY_METADATA) {
                                int entityID = event.getPacket().getIntegers().read(0);
                                if (entityID == event.getPlayer().getEntityId()) {
                                	File file = new File("plugins//LW-Essentials//Vanished//" + event.getPlayer().getName() + ".yml");
                                    if (file.exists()) {
                                        if (!hasSilentlyOpenedChest(event.getPlayer())) return;
                                        event.setCancelled(true);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            if (e.getMessage() == null
                                    || !e.getMessage().endsWith("is not supported for temporary players.")) {
                                return;
                            }
                        }
                    }
                });
    }

    @Data
    private static class StateInfo {

        private final boolean canFly, isFlying;
        private final GameMode gameMode;

        static StateInfo extract(Player p) {
            return new StateInfo(
                    p.getAllowFlight(),
                    p.isFlying(),
                    p.getGameMode()
            );
        }
    }
}