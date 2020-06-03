package dev._2lstudios.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class EntityDamageByEntityListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
		final double damage = event.getDamage();

		if (damage > 0) {
			final EntityDamageEvent.DamageCause damageCause = event.getCause();
			final Entity hitEntity = event.getEntity();

			if (VersionUtil.isOneDotNine() && damageCause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)
				event.setCancelled(true);
			else if (hitEntity instanceof LivingEntity) {
				final LivingEntity hitLivingEntity = (LivingEntity) hitEntity;
				final int hitDelay = 20;

				if (hitLivingEntity.getMaximumNoDamageTicks() != hitDelay)
					hitLivingEntity.setMaximumNoDamageTicks(hitDelay);

				if (VersionUtil.isOneDotNine()) {
					final Entity damager = event.getDamager();

					if (damager instanceof Projectile) {
						final Projectile projectile = (Projectile) damager;
						final EntityType projectileType = projectile.getType();

						if (projectileType == EntityType.ARROW)
							event.setDamage(damage / 2);

						if (hitLivingEntity.getNoDamageTicks() < hitDelay / 2
								&& (projectileType == EntityType.ENDER_PEARL || projectileType == EntityType.SNOWBALL
										|| projectileType == EntityType.EGG
										|| projectileType == EntityType.FISHING_HOOK)) {
							final Vector velocity = projectile.getVelocity();

							hitLivingEntity.damage(0.01);
							hitLivingEntity.setVelocity(velocity.normalize().multiply(0.35).setY(0.32));
						}
					} else if (damager instanceof HumanEntity) {
						final PlayerInventory inventory = ((HumanEntity) damager).getInventory();
						final ItemStack heldItem = inventory.getItem(inventory.getHeldItemSlot());

						if (heldItem != null) {
							final Material material = heldItem.getType();

							if (material == Material.DIAMOND_AXE || material == Material.IRON_AXE
									|| material == Material.STONE_AXE || material == Material.GOLD_AXE
									|| material == Material.WOOD_AXE)
								event.setDamage(damage - 3);
							else if (material == Material.DIAMOND_SPADE || material == Material.IRON_SPADE
									|| material == Material.STONE_SPADE || material == Material.GOLD_SPADE
									|| material == Material.WOOD_SPADE)
								event.setDamage(damage - 0.5);
						}
					}
				}
			}
		}
	}
}