#### <span class="span-underline">Introduction</span>

The LW-Network was a series of game servers started up by my friends: Brennen Kosmeh, Brock Radford, Justin Sigai; and I.

We hosted these servers from June 2019 to December 2019, and provided services to a community of hundreds of players during our peak. Our servers had to be running 24 hours a day, 7 days a week, as at any given time we had
at least 100 players on the servers at any given time. At the end of our run, we had generated USD ~$21,000 in revenue through the sale of in-game cosmetics and items.

This article won't be going into much detail about the actual business. Instead, we'll be focusing on the software developed for it: LW-Core, LW-Hub, and Punish-GUI. These plugins were written by myself and others in order to improve our service and optimize server efficiency. In the cases of Punish-GUI and parts of LW-Core, they were designed to give a front-end interface to our back-end that our volunteer staff and administrators could utilize to make sure their daily tasks were completed.

If you are interested in learning more about the LW-Network besides the software aspect of it, you can [contact me](https://www.darrenbentler.com/contact).

#### <span class="span-underline">Definitions</span>

Now I'm going to be using a lot of terms you, the reader, may not be all too familiar with. For the sake of your clarity, I'll define the terms that'll be used in this article, along with links to additional reading (which I highly recommend checking out if you are **NOT** familar with something at all).

Technical terms include:

- **[Minecraft](https://en.wikipedia.org/wiki/Minecraft)**: A Java, voxel-based sandbox game developed by [Mojang Studios](https://en.wikipedia.org/wiki/Mojang_Studios).
- **Block**: A cube voxel - the foundation of Minecraft. One block is measured as having a width of 1 meter, a length of 1 meter, and height of 1 meter.
- **Chunk**: A unit of measurement made up of a 16x16x256 array of blocks.
- **The World**: A minecraft world with X, Y, and Z coordinates. Usually 30 million blocks across, we had a world border of 10,000 blocks.
- **[SpigotMC](https://www.spigotmc.org/wiki/about-spigot/)**: A modified server.jar based on [CraftBukkit](https://dev.bukkit.org/) with performance improvements and additional features. We ran the build compatible with Minecraft 1.8.9.
- **[SpigotAPI](https://hub.spigotmc.org/javadocs/bukkit/)**: Spigot's API, built off of the CraftBukkit API (Think C++ and C). It provides an interface for the server to interact with players and the in-game world, and vice versa. We used v1.8.9 of the API.
- **Plugin**: A Java program built off of either the Spigot or CraftBukkit API that provides a Minecraft server with custom command features that can be used by either the player or server directly.
- **[BungeeCord](https://www.spigotmc.org/wiki/about-bungeecord/)**: Proxy bridge between a player's client (their local instance of Minecraft) and a server. Allows the seamless connection to multiple servers at once.
- **[OVH Cloud](https://www.ovh.com/world/)**: A French cloud computing company that rents out VPS, dedicated servers, and other web services.
- **Factions**: A gametype where players form "teams" and battle against one another in order to become the dominant power. Teams or players can raid other teams or players in order to steal the latters belongings.
- **[SavageFactions](https://github.com/lonewolvespvp/SavageFactions)**: A fork of a fork (wow!) of the original Minecraft [Factions](https://www.spigotmc.org/resources/factions.1900/) plugin. What is linked is the LW team's fork of SavageFactions.
- **[Putty](https://www.putty.org/)**: An SSH and telnet client developed by Simon Tatham for Windows. We used this to interact with our OVH servers.
- **[BuyCraft/Tebex](https://www.spigotmc.org/resources/buycraft.336/)**: Created as a solution for server monetization, now under the name of [Tebex](https://www.tebex.io/). Will be referred by the original name of "BuyCraft".
- **[LuckPerms](https://github.com/lucko/LuckPerms)**: A Spigot plugin that allows what features a player can use by assigning them to groups.
- **[Vault](https://github.com/milkbowl/Vault)**: A plugin that provides a permissions, chat, and economy API for plugins to hook into, removing the need of dependencies.

Now for the non-technical terms:

- **We/Our/Admin Team/Us/The Team**: Used in reference to myself and the LW-Network administration team.
- **LW**: Slang for "LW-Network". LW stands for... *LoneWolves*. *God, what an embarrassing name*.
- **Spawn**: A term used to refer to point (0, ~, 0) in a world, where ~ is the y-value.
- **Alt**: A slang term used to reference alternative player accounts. I'll be refering to them with the full term, but the code may refer to this as "alt".
- **King of the Hill / KoTH**: A player vs player event where a team of players try to hold a certain (X, Y, Z) coordinate in the world uncontested.
- **Ban**: A permanent suspension from the LW-Network.
- **TempBan**: A temporary suspension from the LW-Network.
- **Mute**: A suspension of in-game chat privileges.
- **Staff**: Used in reference to our volunteer moderator team, and the admins.
- **Moderator**: A low-level staff member who does not have access to the backend of the server, but access to permissions such as ban and teleport.
- **Teleport**: A command that moves a player directly to the position of another player, or an (X, Y, Z) point in the world.
- **/help**: A command players can use that displays what features their current group assigned can access.
- **Warps**: Similar to spawn, a warp allows a player to teleport to any (X, Y, Z) position in the world that has been designated as a warp.
- **Anything prefaced by "/" ie: "/spawn"**: A way for players to interact with the server and call functions from plugins without leaving the game window.

With all of that out of the way, we are ready to begin diving deep into the software now.

#### <span class="span-underline">Where the heck am I?</span>

<div class="container center-text spacer-25px">
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/DistanceCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: Distance Command</button>
    </a>
</div>

LW-Core started off as a simple quality of life plugin developed by myself known as "Distance". Before we went ahead and launched our service to the public, we started with a beta phase to stress test our current infrastructure. One of the things we missed in the beta phase was setting our spawnpoint to the world's (0, Y, 0) position, as this point in that world was located in an ocean.

In the factions gamemodes, your goal to to journey out as far as you can from the spawn point until you feel like you have reached a safe enough position to build your own base with your team. How can players feel confident that they journeyed out far enough if the spawn point is something like (-4873, Y, 2372)?

Luckily for us, players usually only travel along the X and Z axis. The Y axis is only used to get the player's elevation in relation to a point in the world. If we disregard this Y-axis, we can easily plot where a player is within the world.

<img src="https://saylordotorg.github.io/text_elementary-algebra/section_06/a261b102ea95af58439ffec935b5f686.jpg#center" alt="Graph" class="center">
*An example of what I mean.*

Utilizing the SpigotAPI, we can grab the player's inidivdual's X and Z coordinates. Then, using the following mathematical formula:

<img src="https://www.gstatic.com/education/formulas2/355397047/en/distance_formula.svg" alt="Graph" class="center">
*It's 8th grade Algebra all over again!*

And it's pretty easy to do this through code. Don't worry, I'll walk you through that everything does in just a second. But continuing forward, you should reference the [SpigotAPI](https://hub.spigotmc.org/javadocs/bukkit/) to get a better sense of what's exactly happening in the code:

```java
  
package me.ezjamo.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.managers.SpawnManager;

public class DistanceCommand extends Utils implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		SpawnManager spawnCoords = SpawnManager.getManager();
		if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
			message(player, Messages.prefix + "&fThere is no spawn set.");
			return true;
		}
		Location pLocation = player.getLocation();
		int playerX = pLocation.getBlockX();
		int playerZ = pLocation.getBlockZ();
		double spawnX = spawnCoords.getConfig().getDouble("spawn.x");
		double spawnZ = spawnCoords.getConfig().getDouble("spawn.z");
		int dist = (int) Math.sqrt((Math.pow(playerX - spawnX, 2)) + (Math.pow(playerZ - spawnZ, 2)));

		message(player, Messages.prefix + Messages.distanceCommand.replace("%distance%", Integer.toString(dist)));
		return true;
	}
}
```

Alright, let's break it down:

```java
if (!(sender instanceof Player)) {
            message(sender, "&cThis command can only be used by players!");
            return true;
        }
```

This is a simple check to prevent the server terminal from calling /distance, as a server terminal can not be a player.

```java
Player player = (Player) sender;
        SpawnManager spawnCoords = SpawnManager.getManager();
        if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
            message(player, Messages.prefix + "&fThere is no spawn set.");
            return true;
        }
```

This code creates a local copy of of the Player instance who called /distance. It also checks our server configuration files to get a reference to whatever spawn point we had set with /setspawn. If no spawn is set, the server will inform the player that no spawn exists yet. Both `Player` and `SpawnManager` are from the [SpigotAPI](https://hub.spigotmc.org/javadocs/bukkit/), along with `Messages` and `Messages.prefix`. The functions `getConfig()`, and `getConfigurationSection()` are from a utilites class we built in later.

```java
Location pLocation = player.getLocation();
		int playerX = pLocation.getBlockX();
		int playerZ = pLocation.getBlockZ();
		double spawnX = spawnCoords.getConfig().getDouble("spawn.x");
		double spawnZ = spawnCoords.getConfig().getDouble("spawn.z");
		int dist = (int) Math.sqrt((Math.pow(playerX - spawnX, 2)) + (Math.pow(playerZ - spawnZ, 2)));

		message(player, Messages.prefix + Messages.distanceCommand.replace("%distance%", Integer.toString(dist)));
		return true;
```

This is where we implement the distance formula. Almost all calls are to the SpigotAPI, except for `spawnCoords.getConfig()`, which is from our utilities class.

Finally, to make sure our command is actually usable. We can add this to `Lonewolves.java`:

```java
getCommand("distance").setExecutor(new DistanceCommand())
```

#### <span class="span-underline">LW-Core: The Essentials</span>

<div class="container center-text spacer-25px">
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/SpawnCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: Spawn</button>
    </a>
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/SetWarpCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: Set Warps</button>
    </a>
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/SetSlotsCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: Set Slots</button>
    </a>
</div>

After the creation of the distance command, a revelation was had within the team. "*Why download someone else's plugin, which probably has rely on other plugins and increase our restart time, when we can just write our own version with exactly what we need?*"

And so, LW-Core came into existence. The first thing we needed to do was create essential commands such as: a way for players to return to spawn, a warp command for players to go a point of interest, and a way to set the amount of player slots available on the server without restarting.

In the next few sections, we'll go more in depth with these commands and how we implement them.

##### <span class="span-underline">LW-Core: Spawn</span>

<div class="container center-text spacer-25px">
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/SetSpawnCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: SetSpawn</button>
    </a>
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/SpawnCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: Spawn</button>
    </a>
</div>

A command like /spawn is an **essential** feature for any Minecraft server, especially a factions one. Spawn is where players can meet safely and trade items, work on their tools, or access the item shop using in-game currency. Outside of spawn is also an open
player vs player zone, with custom built monuments and terrain. If you're on a factions server and you're not in your base, chances are you're at spawn.

For a /setspawn plugin to work, we need it to be able to grab a player's location in the world along the X, Y, and Z axis. Not only that, we also need to save where the player is looking. Once we collect that data, we'll need to save it to a configuration file **and** inform the server that the world's spawn has been moved to position (X, Y, Z). This way, in the event someone dies, they won't be teleported to an unknown location.

The code for that would look like this:

```java
package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.managers.SpawnManager;

public class SetSpawnCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		SpawnManager spawnCoords = SpawnManager.getManager();
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (player.hasPermission("lw.setspawn")) {
				spawnCoords.getConfig().set("spawn.world", player.getLocation().getWorld().getName());
				spawnCoords.getConfig().set("spawn.x", player.getLocation().getX());
				spawnCoords.getConfig().set("spawn.y", player.getLocation().getY());
				spawnCoords.getConfig().set("spawn.z", player.getLocation().getZ());
				spawnCoords.getConfig().set("spawn.yaw", player.getLocation().getYaw());
				spawnCoords.getConfig().set("spawn.pitch", player.getLocation().getPitch());
				spawnCoords.saveConfig();
				message(player, Messages.prefix + "&fSpawn set.");
			}
			if (!player.hasPermission("lw.setspawn")) {
				message(player, Messages.prefix + Messages.noPermission);
			}
		}
		return true;
	}
}
```

Now let's look at the main meat of the code to understand what's going on:

```java
SpawnManager spawnCoords = SpawnManager.getManager();
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (player.hasPermission("lw.setspawn")) {
                spawnCoords.getConfig().set("spawn.world", player.getLocation().getWorld().getName());
                spawnCoords.getConfig().set("spawn.x", player.getLocation().getX());
                spawnCoords.getConfig().set("spawn.y", player.getLocation().getY());
                spawnCoords.getConfig().set("spawn.z", player.getLocation().getZ());
                spawnCoords.getConfig().set("spawn.yaw", player.getLocation().getYaw());
                spawnCoords.getConfig().set("spawn.pitch", player.getLocation().getPitch());
                spawnCoords.saveConfig();
                message(player, Messages.prefix + "&fSpawn set.");
            }
        }
```

The first thing we're checking for is `player.hasPermission("lw.setspawn")`. With LuckPerms installed and hooked, we're able to define custom permissions to make sure these commands can only be accessed by certain groups.

The next thing we're doing is setting the spawn location of the specific world we're in. Minecraft has three worlds by default: The Overworld, The Nether, and The End. `.getWorld().getName()` ensures we're setting the spawn only in the world we're currently in.

After that, we're grabbing the player who ran the commands `Player`'s (X, Y, Z) position, as well as their pitch and yaw.

Finally, we just simply save this information to a configuration file and reply with a confirmation message.


And of course, in the event a player who doesn't has the permission `lw.setspawn` tried to change the spawn location...

```java
if (!player.hasPermission("lw.setspawn")) {
				message(player, Messages.prefix + Messages.noPermission);
			}
```

...we return a preset message: "Invalid command! Type /help" that's configured in `Messages`.

Alright! That's great! But how do players get back to spawn on their own? What if they're stuck in a block, and need an admin or staff member to teleport them back to spawn? This is where things get a bit complex...

The first thing we'll need to do is get the world the player is in. Once we figure that out, we can pull information about the spawn's location from our configuration files:

```java
World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("spawn.world"));
double x = spawnCoords.getConfig().getDouble("spawn.x");
double y = spawnCoords.getConfig().getDouble("spawn.y");
double z = spawnCoords.getConfig().getDouble("spawn.z");
float yaw = (float)spawnCoords.getConfig().getDouble("spawn.yaw");
float pitch = (float)spawnCoords.getConfig().getDouble("spawn.pitch");
Location spawn = new Location(w, x, y, z, yaw, pitch);
```

Then we'll need to get the centered location of the spawn block. See, Minecraft's (X, Y, Z) coordinates are actually stored as `doubles`. We want to make sure we center the player on the spawn block instead of a little to the left or a little to the right. We handle this in the [SpawnManager](https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/managers/SpawnManager.java), as well as some other things. The code for centering the spawn location inside of the manager looks a little something like this:

```java
public static Location getCenteredLocation(Location loc) {
        World world = loc.getWorld();
        int x = loc.getBlockX();
        int y = (int) Math.round(loc.getY());
        int z = loc.getBlockZ();
        return new Location(world, x + 0.5, y, z + 0.5, loc.getYaw(), loc.getPitch());
    }
```

Once we receive that location, we can then check whether or not the player we're attempting to teleport is online. If so, we teleport them and send two confirmation messages: one to the command sender and one to the person who was teleported. If not, we return an error message that the player doesn't exist on the server.

```java
if (Lonewolves.plugin.getConfig().getBoolean("teleport-to-center")) {
            spawn = SpawnManager.getCenteredLocation(spawn);
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                message(sender, Messages.prefix + "&cPlayer not found.");
                return true;
            }
            if (ess != null) {
                User user = ess.getUser(target);
                user.setLastLocation();
            }
            target.teleport(spawn);
            message(sender, Messages.prefix + "&fTeleportation successful!");
            message(target, Messages.prefix + "&fTeleportation successful!");
            return true;
```

We also have another `if` set up for when it's only a player trying to teleport themselves to spawn, with similar functionality:

```java
 Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("spawn")) {
        	if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
        		message(player, Messages.prefix + "&cSpawn not set. Use &f/setspawn &cto set a spawnpoint.");
        		return true;
        	}
        	World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("spawn.world"));
            double x = spawnCoords.getConfig().getDouble("spawn.x");
            double y = spawnCoords.getConfig().getDouble("spawn.y");
            double z = spawnCoords.getConfig().getDouble("spawn.z");
            float yaw = (float)spawnCoords.getConfig().getDouble("spawn.yaw");
            float pitch = (float)spawnCoords.getConfig().getDouble("spawn.pitch");
            Location spawn = new Location(w, x, y, z, yaw, pitch);
            if (Lonewolves.plugin.getConfig().getBoolean("teleport-to-center")) {
                spawn = SpawnManager.getCenteredLocation(spawn);
            }
            if (player.hasPermission("lw.spawn")) {
                if (args.length < 1) {
                	if (player.hasPermission("lw.bypass.teleportdelay")) {
                		if (ess != null) {
                            User user = ess.getUser(player);
                            user.setLastLocation();
                        }
                    	player.teleport(spawn);
                    	message(player, Messages.prefix + "&fTeleportation successful!");
                        return true;
                    }
```

When you look at the Source Code, you'll see some additional code I omitted in this section. That's because they're there for gameplay specific scenarios. We may dive into them at a later section.

##### <span class="span-underline">LW-Core: Warps</span>

<div class="container center-text spacer-25px">
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/SetWarpCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: SetWarp</button>
    </a>
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/WarpCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: Warp</button>
    </a>
</div>

It's time for warps! Warps are usually used to indicate a place of interest to players. You can set warps at the shop, at a public anvil, or at a cool building you made.

The first thing we'll need to do is configure a [WarpManager](https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/managers/WarpManager.java) to handle our warps. This has similar functionality to the `SpawnManager` I briefly mentioned in the section above this one. Since I didn't really dive into the `SpawnManager`'s code, I'll just paste the `WarpManager`'s code here.

```java
package me.ezjamo.managers;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.commands.WarpCommand;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;

public class WarpManager extends Utils implements Listener {
	public Lonewolves plugin;
	public File file;
	public static FileConfiguration config;
	public static WarpManager manager;
	
	static {
		WarpManager.manager = new WarpManager();
	}
	
	public WarpManager() {
		plugin = Lonewolves.getPlugin(Lonewolves.class);
	}
	
	public static WarpManager getManager() {
		return WarpManager.manager;
	}

	public static FileConfiguration getWarps() {
		return WarpManager.config;
	}
	
	public void load() {
		file = new File(plugin.getDataFolder(), "warps.yml");
		if (!file.exists()) {
			plugin.getLogger().info("Creating default: " + file);
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
				plugin.getServer().getConsoleSender().sendMessage(color("&aSuccessfully created: " + file));
			} catch (Exception e) {
				plugin.getServer().getConsoleSender().sendMessage(color("&cCould not create: " + file));
			}
		}
		config = new YamlConfiguration();
		try {
			config.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration get() {
		return config;
	}
	
	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void save() {
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Location getCenteredLocation(Location loc) {
		World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = (int) Math.round(loc.getY());
		int z = loc.getBlockZ();
		return new Location(world, x + 0.5, y, z + 0.5, loc.getYaw(), loc.getPitch());
	}
	
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ() || event.getFrom().getBlockY() != event.getTo().getBlockY()) {
            BukkitTask task = WarpCommand.tasks.get(player);
            if (task != null) {
                message(player, Messages.prefix + "&cTeleportation cancelled!");
                task.cancel();
                WarpCommand.tasks.remove(player);
            }
        }
    }
}
```

You can ignore the `@EventHandler` for now. It's used for a gameplay specific element that we'll dive into later.

As you can see, all the manager does is create some configuration files to save our warp data to, access it when necessary, and solve the `getCenteredLocation` problem from earler.

Now onto `SetWarps.java`:

```java
package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.managers.WarpManager;

public class SetWarpCommand extends Utils implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        WarpManager warps = WarpManager.getManager();
        if (cmd.getName().equalsIgnoreCase("setwarp")) {
            if (!player.hasPermission("lw.setwarp")) {
            	message(player, Messages.prefix + "&cYou do not have permission to do this.");
                return true;
            }
            if (args.length == 0) {
                message(player, "&cUsage: &7/setwarp <name>");
                return true;
            }
            if (args.length == 1) {
                warps.get().set("Warps." + args[0].toLowerCase() + ".world", player.getLocation().getWorld().getName());
                warps.get().set("Warps." + args[0].toLowerCase() + ".x", player.getLocation().getX());
                warps.get().set("Warps." + args[0].toLowerCase() + ".y", player.getLocation().getY());
                warps.get().set("Warps." + args[0].toLowerCase() + ".z", player.getLocation().getZ());
                warps.get().set("Warps." + args[0].toLowerCase() + ".yaw", player.getLocation().getYaw());
                warps.get().set("Warps." + args[0].toLowerCase() + ".pitch", player.getLocation().getPitch());
                warps.save();
                message(player, Messages.prefix + "&aWarp &f" + args[0].toLowerCase() + " &aset.");
            }
        }
        return true;
    }
}
```

Very similar functionality to our Spawn implementations. The warps grabs the player who is setting the warp's (X, Y, Z) positions, along with their Pitch and Yaw. 

Then we save that information into the warps configuration file. It also creates a permission and warp name with the following syntax: `lw.warp.<name>`.

Now, how can players get to these warp locations? Well, it's very similar to how we did it in the `Spawn` implementation! We just need to read the data we saved, pass that information to `bukkit.teleport`, and send back a confirmation message.

We also check for valid permissions as well. Here's how the implementation is done for a staff member warping another player. You'll see a lot of familiarity to `Spawn`:

```java
public class WarpCommand extends Utils implements CommandExecutor, TabCompleter {
	public static Map<Player, BukkitTask> tasks = new HashMap<>();
	private Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		WarpManager warps = WarpManager.getManager();
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				if (warps.get().getConfigurationSection("Warps") == null || warps.get().getConfigurationSection("Warps").getKeys(false).isEmpty()) {
					message(sender, Messages.prefix + "&cThere are no warps available.");
					return true;
				}
				Set<String> warpList = warps.get().getConfigurationSection("Warps").getKeys(false);
				message(sender, "&cWarps: &f" + warpList.toString().replace("[", "").replace("]", ""));
				return true;
			}
			if (args.length == 1) {
				message(sender, "&cYou must specify a player to teleport!");
				return true;
			}
			if (args.length == 2) {
				if (warps.get().getConfigurationSection("Warps." + args[0].toLowerCase()) == null) {
					message(sender, "&fWarp &c" + args[0].toLowerCase() + " &fdoes not exist.");
					return true;
				}
				World w = Bukkit.getWorld(warps.get().getString("Warps." + args[0].toLowerCase() + ".world"));
				double x = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".x");
				double y = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".y");
				double z = warps.get().getDouble("Warps." + args[0].toLowerCase() + ".z");
				float yaw = (float)warps.get().getDouble("Warps." + args[0].toLowerCase() + ".yaw");
				float pitch = (float)warps.get().getDouble("Warps." + args[0].toLowerCase() + ".pitch");
				Location warpLoc = new Location(w, x, y, z, yaw, pitch);
				if (Lonewolves.plugin.getConfig().getBoolean("teleport-to-center")) {
					warpLoc = WarpManager.getCenteredLocation(warpLoc);
				}
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					message(sender, "&cPlayer not found.");
					return true;
				}
				if (ess != null) {
					User user2 = ess.getUser(target);
					user2.setLastLocation();
				}
				target.teleport(warpLoc);
				message(sender, Messages.prefix + "&fWarped &c" + target.getName() + " &fto &c" + args[0]);
				message(target, Messages.prefix + "&fWarped to &c" + args[0]);
			}
			else {
				message(sender, "&cUsage: &7/warp <warp> <player>");
			}
			return true;
		}
```

With that, warps are now done and ready to go.

##### <span class="span-underline">LW-Core: Player Slots</span>

<div class="container center-text spacer-25px">
    <a href="https://github.com/dbentler/LW-Core/blob/master/src/me/ezjamo/commands/SetSlotsCommand.java">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW-Core: Set Slots</button>
    </a>
</div>

The ability to add more or restrict player slots is vital when it comes to server security. By default, we had the capacity for 200 players on the server at any time.

Unfortunately, theis leaves a lot of room for people with hostile intentions to log on multiple accounts and spam our server chat - with either advertisements for other servers or profanity.

These bots could also all drop a bunch of items at the same time and create unnecessary strain on the server. This is because Minecraft renders dropped items as entities, Minecraft itself cannot handle too many entities on the screen at once.

So! We want to be able to set the amount of player slots available without having to restart the server in the event of one of the attacks. Luckily, the SpigotAPI allows us to do just this.

All that's really need is to make sure we catch any errors from the user, and from the server:

```java
package me.ezjamo.commands;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.ezjamo.Messages;

public class SetSlotsCommand implements CommandExecutor
{
	
    public boolean onCommand(CommandSender sender,Command command,String alias,String[] args) {
        if (!sender.hasPermission("lw.slots")) {
            sender.sendMessage(Messages.prefix + Messages.noPermission);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments.");
            return true;
        }
        try {
            this.changeSlots(Integer.parseInt(args[0]));
            sender.sendMessage(ChatColor.GREEN + "Slots updated.");
        }
        catch (NumberFormatException numbererr) {
            sender.sendMessage(ChatColor.RED + "You must provide a valid number.");
        }
        catch (ReflectiveOperationException errorerr) {
            sender.sendMessage(ChatColor.RED + "An unknown error has occurred");
        }
        return true;
    }
```

If everything checks out, we then can utilize SpigotAPI calls to change these player slots:

```java
public void changeSlots(int slots) throws ReflectiveOperationException {
       Object playerList = Bukkit.getServer().getClass().getDeclaredMethod("getHandle", (Class<?>[])new Class[0]).invoke(Bukkit.getServer(), new Object[0]);
       Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");
        maxPlayersField.setAccessible(true);
        maxPlayersField.set(playerList, slots);
    }
}
```

Just like that, we're done! A very simple way to make sure damage done from bot attacks won't as catastrophic.

We also developed other ways to secure the server from Bot attacks, but we'll get to those later. For now, let's take a break from the code and talk about how the server is set up.

<div class="container center-text spacer-25px">
    <a href="/projects">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">Go Back to Projects</button>
    </a>
    <a href="https://github.com/dbentler">
        <button type="button" id="back" onclick="" class="btn btn-dark btn-lg">LW Software Suite (Github)</button>
    </a>
    <button type="button" id="linkedin" onclick="" class="btn btn-dark btn-lg" disabled>Continue Reading (Coming Soon!)</button>
</div>

