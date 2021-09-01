package dev._2lstudios.mechanics.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PlayerPortalEnterListener implements Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
  public void onPlayerPortal(final PlayerPortalEvent event) {
    final Location from = event.getFrom();
    final Location to = event.getTo();

    if (to != null) {
      final World fromWorld = from.getWorld();
      final World toWorld = to.getWorld();

      if (fromWorld != toWorld && toWorld.getEnvironment() == World.Environment.THE_END) {
        event.setTo(toWorld.getSpawnLocation());
      }
    }
  }
}
