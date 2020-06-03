package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PotionSplashListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	void onPotionSplash(final PotionSplashEvent event) {
		final ProjectileSource shooter = event.getEntity().getShooter();

		if (shooter instanceof Player) {
			final Player player = (Player) shooter;
			final double intensity = event.getIntensity(player);

			if (intensity > 0.25D)
				event.setIntensity(player, 1.0D);
		}
	}
}
