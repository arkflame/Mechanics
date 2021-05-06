package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {
  @EventHandler(ignoreCancelled = true)
  public void onEntitySpawn(EntitySpawnEvent event) {
    Entity entity = event.getEntity();

    if (entity != null && entity.getType() == EntityType.ENDER_CRYSTAL)
      event.setCancelled(true);
  }
}
