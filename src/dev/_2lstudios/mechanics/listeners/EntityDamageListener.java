package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onEntityDamage(EntityDamageEvent event) {
    Entity entity = event.getEntity();

    if (entity instanceof Player) {
      EntityDamageEvent.DamageCause damageCause = event.getCause();

      if (damageCause == EntityDamageEvent.DamageCause.VOID && event.getDamage() < 12.0D) {
        event.setDamage(12.0D);
      } else if (VersionUtil.isOneDotNine()) {
        if (damageCause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
          event.setCancelled(true);
        } else if (damageCause == EntityDamageEvent.DamageCause.FIRE_TICK
            || damageCause == EntityDamageEvent.DamageCause.FIRE) {
          ((Player) entity).playSound(entity.getLocation(), Sound.ENTITY_PLAYER_HURT, 7.0F, 1.0F);
        }
      }
    }
  }
}
