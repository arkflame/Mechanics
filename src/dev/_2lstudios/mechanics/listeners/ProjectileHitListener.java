package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {
  public ProjectileHitListener(Server server) {
    this.server = server;

    try {
      ProjectileHitEvent.class.getMethod("getHitEntity", new Class[0]);
      this.isOneDotNine = VersionUtil.isOneDotNine();
    } catch (NoSuchMethodException noSuchMethodException) {
    }
  }

  private final Server server;

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onProjectileHit(ProjectileHitEvent event) {
    if (this.isOneDotNine) {
      Entity hitEntity = event.getHitEntity();

      if (hitEntity instanceof LivingEntity) {
        LivingEntity damaged = (LivingEntity) hitEntity;
        Projectile projectile = event.getEntity();

        if ((projectile instanceof org.bukkit.entity.FishHook || projectile instanceof org.bukkit.entity.Egg
            || projectile instanceof org.bukkit.entity.Snowball || projectile instanceof org.bukkit.entity.EnderPearl)
            && damaged.getNoDamageTicks() == 0)
          this.server.getPluginManager().callEvent((Event) new EntityDamageByEntityEvent((Entity) projectile,
              (Entity) damaged, EntityDamageEvent.DamageCause.PROJECTILE, 0.1D));
      }
    }
  }

  private boolean isOneDotNine = false;
}
