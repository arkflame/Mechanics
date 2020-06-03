package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.KnockbackManager;

public class PlayerVelocityListener implements Listener {
	final private KnockbackManager knockbackManager;

	public PlayerVelocityListener(final GameMechanicsManager gameMechanicsManager) {
		this.knockbackManager = gameMechanicsManager.getKnockbackManager();
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerVelocity(final PlayerVelocityEvent event) {
		final Player damaged = event.getPlayer();

		if (damaged.isOnline()) {
			final EntityDamageEvent lastDamageCause = damaged.getLastDamageCause();

			damaged.setFallDistance(damaged.getFallDistance() / 2);

			if (lastDamageCause != null && !lastDamageCause.isCancelled()) {
				final EntityDamageEvent.DamageCause damageCause = lastDamageCause.getCause();

				if (lastDamageCause instanceof EntityDamageByEntityEvent) {
					final EntityDamageByEntityEvent lastDamageCauseByEntity = (EntityDamageByEntityEvent) lastDamageCause;
					final Entity damager = lastDamageCauseByEntity.getDamager();
					final Entity hitEntity = lastDamageCauseByEntity.getEntity();

					if (hitEntity instanceof LivingEntity) {
						final LivingEntity hitLivingEntity = (LivingEntity) hitEntity;
						final int maximumNoDamageTicks = hitLivingEntity.getMaximumNoDamageTicks();

						if (damager instanceof Player && hitLivingEntity instanceof Player
								&& damager != hitLivingEntity) {
							final Player hitPlayer = (Player) hitLivingEntity;

							if (hitPlayer.getNoDamageTicks() == maximumNoDamageTicks) {
								final Player damagerPlayer = (Player) damager;
								final Vector knockbackFormula = knockbackManager.applyKnockbackFormula(hitPlayer,
										damagerPlayer);

								knockbackManager.sendKnockbackPacket(hitPlayer, knockbackFormula);
							}

							event.setCancelled(true);
						}
					}
				} else if (damageCause == EntityDamageEvent.DamageCause.FALL
						|| damageCause == EntityDamageEvent.DamageCause.CONTACT
						|| damageCause == EntityDamageEvent.DamageCause.FIRE_TICK)
					event.setCancelled(true);
			}
		}
	}
}
