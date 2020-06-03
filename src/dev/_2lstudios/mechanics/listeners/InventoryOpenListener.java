package dev._2lstudios.mechanics.listeners;

import org.bukkit.block.BrewingStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import dev._2lstudios.mechanics.managers.BrewingManager;
import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class InventoryOpenListener implements Listener {
	final private BrewingManager brewingManager;
	final private EnchantingManager enchantingManager;

	public InventoryOpenListener(final GameMechanicsManager gameMechanicsManager) {
		this.brewingManager = gameMechanicsManager.getBrewingManager();
		this.enchantingManager = gameMechanicsManager.getEnchantingManager();
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryOpen(final InventoryOpenEvent event) {
		if (!event.isCancelled()) {
			final Inventory inventory = event.getInventory();
			final InventoryType inventoryType = inventory.getType();

			if (inventoryType == InventoryType.ENCHANTING)
				inventory.setItem(1, enchantingManager.getLapisLazuli());
			else if (inventoryType == InventoryType.BREWING) {
				final InventoryHolder holder = inventory.getHolder();

				if (holder instanceof BrewingStand) {
					final BrewingStand brewingStand = (BrewingStand) holder;

					if (VersionUtil.isOneDotNine() && brewingStand.getFuelLevel() < 19) {
						brewingStand.setFuelLevel(20);
						brewingStand.update();
					}

					event.getPlayer().openInventory(brewingManager.getBrewingInventory(brewingStand));
					event.setCancelled(true);
				}
			}
		}
	}
}
