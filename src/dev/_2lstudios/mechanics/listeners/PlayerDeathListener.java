package dev._2lstudios.mechanics.listeners;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public class PlayerDeathListener implements Listener {
	private final Plugin plugin;

	public PlayerDeathListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerDeath(final PlayerDeathEvent event) {
		final Server server = plugin.getServer();
		final Player player = event.getEntity();
		final PlayerInventory playerInventory = player.getInventory();
		final World playerWorld = player.getWorld();
		final Location playerLocation = player.getLocation(),
				spawnLocation = server.getWorlds().get(0).getSpawnLocation();

		if (player.getFireTicks() > 0) {
			player.setFireTicks(-1);
		}

		if (player.getFallDistance() > 1) {
			player.setFallDistance(-1);
		}

		if (player.getVehicle() != null) {
			player.leaveVehicle();
		}

		if (player.getHealth() < 20) {
			player.setHealth(20);
		}

		if (player.getFoodLevel() < 20) {
			player.setFoodLevel(20);
		}

		if (player.getSaturation() < 4.0f) {
			player.setSaturation(4.0f);
		}

		if (player.getExp() > 0) {
			player.setExp(0);
		}

		if (player.getLevel() > 0) {
			player.setLevel(0);
		}

		for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
			player.removePotionEffect(potionEffect.getType());
		}

		if (!event.getKeepLevel()) {
			final int expToDrop = getPlayerExp(player);

			if (expToDrop > 0) {
				final ExperienceOrb experienceOrb = playerWorld.spawn(playerLocation, ExperienceOrb.class);

				experienceOrb.setExperience(expToDrop);
			}
		}

		if (!event.getKeepInventory()) {
			final Collection<ItemStack> drops = event.getDrops();

			for (final ItemStack itemStack : drops) {
				if (itemStack != null && itemStack.getType() != Material.AIR) {
					playerWorld.dropItem(playerLocation, itemStack);
				}
			}

			drops.clear();
			playerInventory.clear();
			playerInventory.setContents(new ItemStack[0]);
			playerInventory.setHelmet(new ItemStack(Material.AIR));
			playerInventory.setChestplate(new ItemStack(Material.AIR));
			playerInventory.setLeggings(new ItemStack(Material.AIR));
			playerInventory.setBoots(new ItemStack(Material.AIR));
			event.setKeepInventory(true);
		}

		server.getPluginManager().callEvent(new PlayerRespawnEvent(player, spawnLocation, false));
	}

	private static int getExpToLevelUp(final int level) {
		if (level <= 15) {
			return 2 * level + 7;
		} else if (level <= 30) {
			return 5 * level - 38;
		} else {
			return 9 * level - 158;
		}
	}

	private static int getExpAtLevel(final int level) {
		if (level <= 16) {
			return (int) (Math.pow(level, 2) + 6 * level);
		} else if (level <= 31) {
			return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
		} else {
			return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
		}
	}

	private static int getPlayerExp(final Player player) {
		int exp = 0;
		final int level = player.getLevel();

		exp += getExpAtLevel(level);

		exp += Math.round(getExpToLevelUp(level) * player.getExp());

		return exp;
	}
}
