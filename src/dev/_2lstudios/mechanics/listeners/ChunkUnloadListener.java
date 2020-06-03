package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkUnloadListener implements Listener {
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onChunkUnload(final ChunkUnloadEvent event) {
		for (final Entity entity : event.getChunk().getEntities()) {
			if (!(entity instanceof Player) && entity instanceof LivingEntity && entity.isValid()) {
				final LivingEntity livingEntity = (LivingEntity) entity;

				livingEntity.setCustomName(null);
				livingEntity.remove();
				livingEntity.setRemoveWhenFarAway(true);
			}
		}
	}
}
