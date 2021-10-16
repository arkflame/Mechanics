package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.Server;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {
  private final Server server;

  public ProjectileHitListener(final Server server) {
    this.server = server;
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onProjectileHit(final ProjectileHitEvent event) {
    final Entity hitEntity = event.getHitEntity();

    if (hitEntity instanceof LivingEntity) {
      final LivingEntity damaged = (LivingEntity) hitEntity;
      final Projectile projectile = event.getEntity();

      if ((projectile instanceof FishHook || projectile instanceof Egg || projectile instanceof Snowball
          || projectile instanceof EnderPearl) && damaged.getNoDamageTicks() == 0)
        this.server.getPluginManager().callEvent(new EntityDamageByEntityEvent((Entity) projectile, damaged,
            EntityDamageEvent.DamageCause.PROJECTILE, 0.1D));
    }
  }
}
