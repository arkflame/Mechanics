package dev._2lstudios.mechanics.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class PlayerTeleportListener implements Listener {
	private final EnchantingManager enchantingManager;

	public PlayerTeleportListener(final GameMechanicsManager gameMechanicsManager) {
		this.enchantingManager = gameMechanicsManager.getEnchantingManager();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onPlayerTeleportLow(final PlayerTeleportEvent event) {
		final PlayerTeleportEvent.TeleportCause teleportCause = event.getCause();

		if (VersionUtil.isOneDotNine() && teleportCause == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) {
			event.setCancelled(true);
		} else {
			final Player player = event.getPlayer();
			final Location to = event.getTo();
			final World toWorld = to.getWorld();

			if (to.distance(player.getBedLocation()) < 1) {
				event.setTo(toWorld.getSpawnLocation());
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPlayerTeleportHighest(final PlayerTeleportEvent event) {
		final Player player = event.getPlayer();
		final PlayerTeleportEvent.TeleportCause teleportCause = event.getCause();
		final Location playerLocation = player.getLocation(), to = event.getTo();
		final World playerWorld = playerLocation.getWorld(), toWorld = to.getWorld();

		player.setFallDistance(3);

		for (final ItemStack itemStack : player.getInventory().getContents()) {
			enchantingManager.fixEnchants(itemStack);
		}

		if (playerWorld == toWorld) {
			final double distance = playerLocation.distance(to);

			if (distance > 5) {
				playTeleportSound(player, to);
			}

			if (teleportCause == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
				if (distance > 1) {
					final Location toClone = to.clone();
					final double playerLocationX = playerLocation.getX();
					final double playerLocationZ = playerLocation.getZ();
					final double toX = to.getX();
					final double toZ = to.getZ();
					final double xDifference = Math.abs(playerLocationX - toX);
					final double zDifference = Math.abs(playerLocationZ - toZ);
					final double offset = 0.5;

					if (toClone.subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
						to.subtract(0, 1.5, 0);
					}

					if (xDifference > offset) {
						to.setX(toX + offset);
					} else if (xDifference < -offset) {
						to.setX(toX - offset);
					} else {
						to.setX(playerLocationX);
					}

					if (zDifference > offset) {
						to.setZ(toZ + offset);
					} else if (zDifference < -offset) {
						to.setZ(toZ - offset);
					} else {
						to.setZ(playerLocationZ);
					}

					to.setDirection(playerLocation.getDirection());
					to.setPitch(playerLocation.getPitch());
					to.setYaw(playerLocation.getYaw());
					event.setTo(to);
				} else {
					event.setCancelled(true);
				}
			}
		} else {
			playTeleportSound(player, to);
		}
	}

	private void playTeleportSound(final Player player, final Location to) {
		final float randomFloat = (float) (1 + Math.random() / 2);

		if (VersionUtil.isOneDotNine()) {
			player.playSound(to, Sound.valueOf("ENTITY_ENDERMEN_TELEPORT"), 1f, randomFloat);
		} else {
			player.playSound(to, Sound.valueOf("ENDERMAN_TELEPORT"), 1f, randomFloat);
		}
	}
}