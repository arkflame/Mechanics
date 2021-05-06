package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PotionSplashListener implements Listener {
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  void onPotionSplash(PotionSplashEvent event) {
    ProjectileSource shooter = event.getEntity().getShooter();

    if (shooter instanceof Player) {
      Player player = (Player) shooter;
      double intensity = event.getIntensity((LivingEntity) player);

      if (intensity > 0.25D)
        event.setIntensity((LivingEntity) player, 1.0D);
    }
  }
}
