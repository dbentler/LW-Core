package me.ezjamo.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import me.ezjamo.Lonewolves;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ActionBarMgr {

    private final Lonewolves plugin;
    private final static List<Player> actionBars = new ArrayList<>();

    public ActionBarMgr(Lonewolves plugin) {
        this.plugin = plugin;
        startTask();
    }

    private void startTask() {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player p : actionBars) {
                    try {
                        sendActionBar(p, plugin.getMessage("ActionBarMessage"));
                    } catch (Exception e) {
                        cancel();
                        return;
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 2 * 20);
    }

    public void sendActionBar(Player p, String bar) {
        String json = "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', bar) + "\"}";
        WrappedChatComponent msg = WrappedChatComponent.fromJson(json);
        PacketContainer chatMsg = new PacketContainer(PacketType.Play.Server.CHAT);
        chatMsg.getChatComponents().write(0, msg);
        if (plugin.getVersionUtil().isOneDotXOrHigher(12))
            try {
                chatMsg.getChatTypes().write(0, EnumWrappers.ChatType.GAME_INFO);
            } catch (NoSuchMethodError e) {
                return;
            }
        else
            chatMsg.getBytes().write(0, (byte) 2);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, chatMsg);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot send packet " + chatMsg, e);
        }
    }

    public static void addActionBar(Player p) {
        actionBars.add(p);
    }

    public static void removeActionBar(Player p) {
        actionBars.remove(p);
    }
}