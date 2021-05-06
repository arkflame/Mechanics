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
  public void onProjectileLaunch(ProjectileLaunchEvent event) {
    Projectile projectile = event.getEntity();
    EntityType projectileType = projectile.getType();
    ProjectileSource shooter = projectile.getShooter();

    if (shooter instanceof Player && (projectileType == EntityType.ARROW || projectileType == EntityType.ENDER_PEARL
        || projectileType == EntityType.SNOWBALL || projectileType == EntityType.EGG)) {
      Player player = (Player) shooter;
      Vector direction = player.getLocation().getDirection();
      double velocityLenght = projectile.getVelocity().length();

      projectile.setVelocity(direction.normalize().multiply(velocityLenght));
    }
  }
}
