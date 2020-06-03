package dev._2lstudios.mechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryDragListener implements Listener {
	@EventHandler(ignoreCancelled = true)
	public void onInventoryDrag(final InventoryDragEvent event) {
		if (event.getInventory().getType() == InventoryType.CRAFTING && event.getInventorySlots().contains(40))
			event.setCancelled(true);
	}
}
