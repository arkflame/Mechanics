package dev._2lstudios.mechanics.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;

import java.util.Map;

public class EnchantItemListener implements Listener {
	final private EnchantingManager enchantingManager;

	public EnchantItemListener(final GameMechanicsManager gameMechanicsManager) {
		this.enchantingManager = gameMechanicsManager.getEnchantingManager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEnchantItem(final EnchantItemEvent event) {
		final Map<Enchantment, Integer> enchantsToAdd = event.getEnchantsToAdd();
		final Inventory inventory = event.getInventory();
		final InventoryType inventoryType = inventory.getType();

		enchantingManager.fixEnchants(enchantsToAdd);

		if (event.getExpLevelCost() > 2)
			event.setExpLevelCost(2);

		if (inventoryType == InventoryType.ENCHANTING)
			inventory.setItem(1, enchantingManager.getLapisLazuli());
	}
}
