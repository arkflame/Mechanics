package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerRespawnListener implements Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    Player player = event.getPlayer();

    if (player.getFireTicks() > 1) {
      player.setFireTicks(1);
    }

    player.teleport(event.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
  }
}
