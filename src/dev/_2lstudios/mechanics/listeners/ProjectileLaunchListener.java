package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class ProjectileLaunchListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onProjectileLaunch(final ProjectileLaunchEvent event) {
		final Projectile projectile = event.getEntity();
		final EntityType projectileType = projectile.getType();
		final ProjectileSource shooter = projectile.getShooter();

		if (shooter instanceof Player)
			if (projectileType == EntityType.ARROW || projectileType == EntityType.ENDER_PEARL
					|| projectileType == EntityType.SNOWBALL || projectileType == EntityType.EGG) {
				final Player player = (Player) shooter;
				final Vector direction = player.getLocation().getDirection();
				final double velocityLenght = projectile.getVelocity().length();

				projectile.setVelocity(direction.normalize().multiply(velocityLenght));
			} else if (projectileType == EntityType.SPLASH_POTION && ((Player) shooter).isSprinting()) {
				final Vector velocity = projectile.getVelocity();

				velocity.setY(-90);
				projectile.setVelocity(velocity);
			}
	}
}
