package dev._2lstudios.mechanics.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class ProjectileHitListener implements Listener {
	private Boolean exists = null;

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onProjectileHit(final ProjectileHitEvent event) {
		if (VersionUtil.isOneDotNine()) {
			if (exists == null)
				try {
					exists = true;
					event.getClass().getMethod("getHitEntity");
				} catch (NoSuchMethodException e) {
					exists = false;
				}

			if (exists) {
				final Entity hitEntity = event.getHitEntity();

				if (hitEntity instanceof LivingEntity) {
					final LivingEntity damaged = (LivingEntity) hitEntity;
					final Projectile projectile = event.getEntity();

					if ((projectile instanceof FishHook || projectile instanceof Egg || projectile instanceof Snowball
							|| projectile instanceof EnderPearl)
							&& damaged.getNoDamageTicks() <= damaged.getMaximumNoDamageTicks() / 2)
						Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(projectile, damaged,
								EntityDamageEvent.DamageCause.PROJECTILE, 0.1));
				}
			}
		}
	}
}
