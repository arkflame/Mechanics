package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.RegenerationManager;
import dev._2lstudios.mechanics.utils.VersionUtil;
import java.util.Map;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {
  private final RegenerationManager regenerationManager;

  public EntityRegainHealthListener(GameMechanicsManager gameMechanicsManager) {
    this.regenerationManager = gameMechanicsManager.getRegenerationManager();
  }

  @EventHandler(ignoreCancelled = true)
  public void onEntityRegainHealth(EntityRegainHealthEvent event) {
    if (!VersionUtil.isOneDotNine()) {
      return;
    }

    if (event.getEntityType() == EntityType.PLAYER
        && event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
      Player player = (Player) event.getEntity();

      if (player.isOnline()) {
        Map<Player, Long> healTimes = this.regenerationManager.getHealTimes();
        long currentTime = System.currentTimeMillis();

        if (currentTime - ((Long) healTimes.getOrDefault(player, Long.valueOf(0L))).longValue() < 3000L) {
          event.setCancelled(true);
        } else {
          double maxHealth = player.getMaxHealth();
          double health = player.getHealth();

          if (health < maxHealth - 1.0D) {
            player.setHealth(health + 1.0D);
            player.setExhaustion(0.5F);
          } else {
            player.setHealth(maxHealth);
          }

          healTimes.put(player, Long.valueOf(currentTime));

          event.setCancelled(true);
        }
      }
    }
  }
}
