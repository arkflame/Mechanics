package dev._2lstudios.mechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryMoveListener implements Listener {
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onInventoryMove(final InventoryMoveItemEvent event) {
		if (!event.isCancelled() && event.getDestination().getType() == InventoryType.BREWING
				&& event.getInitiator().getType() == InventoryType.HOPPER)
			event.setCancelled(true);
	}
}