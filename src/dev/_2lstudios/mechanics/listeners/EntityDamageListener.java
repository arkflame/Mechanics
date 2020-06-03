package dev._2lstudios.mechanics.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class EntityDamageListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDamage(final EntityDamageEvent event) {
		final Entity entity = event.getEntity();

		if (entity instanceof Player) {
			final EntityDamageEvent.DamageCause damageCause = event.getCause();

			if (damageCause == EntityDamageEvent.DamageCause.VOID) {
				event.setDamage(12);
			} else if (VersionUtil.isOneDotNine()) {
				if (damageCause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
					event.setCancelled(true);
				} else if (damageCause == EntityDamageEvent.DamageCause.FIRE_TICK
						|| damageCause == EntityDamageEvent.DamageCause.FIRE) {
					((Player) entity).playSound(entity.getLocation(), Sound.ENTITY_PLAYER_HURT, 7f, 1f);
				}
			}
		}
	}
}