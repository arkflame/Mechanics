package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkUnloadListener implements Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onChunkUnload(ChunkUnloadEvent event) {
    byte b;
    int i;
    Entity[] arrayOfEntity;
    for (i = (arrayOfEntity = event.getChunk().getEntities()).length, b = 0; b < i;) {
      Entity entity = arrayOfEntity[b];
      EntityType entityType = entity.getType();

      if (entity instanceof LivingEntity && entityType != EntityType.PLAYER && entity.isValid()) {
        LivingEntity livingEntity = (LivingEntity) entity;

        livingEntity.setCustomName(null);
        livingEntity.remove();
        livingEntity.setRemoveWhenFarAway(true);
      }
      b++;
    }

  }
}
