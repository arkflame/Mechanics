package dev._2lstudios.mechanics.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import dev._2lstudios.mechanics.brewing.BrewingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;

import java.util.Collection;
import java.util.Iterator;

public class InventoryCloseListener implements Listener {
	final private BrewingManager brewingManager;

	public InventoryCloseListener(final GameMechanicsManager gameMechanicsManager) {
		this.brewingManager = gameMechanicsManager.getBrewingManager();
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onInventoryClose(final InventoryCloseEvent event) {
		final Inventory inventory = event.getInventory();

		if (inventory.getType() == InventoryType.ENCHANTING) {
			final EnchantingInventory inv = (EnchantingInventory) event.getInventory();

			inv.setItem(1, null);
		} else {
			final Collection<Inventory> brewingInventories = brewingManager.getBrewingInventories().values();

			if (containsEquals(inventory, brewingInventories)) {
				final InventoryHolder inventoryHolder = inventory.getHolder();

				if (inventoryHolder instanceof BrewingStand) {
					final Location location = ((BrewingStand) inventoryHolder).getLocation();
					final HumanEntity player = event.getPlayer();
					final PlayerInventory playerInventory = player.getInventory();
					final World world = location.getWorld();
					final ItemStack[] ingredients = new ItemStack[5];

					for (int i = 0; i < 5; i++) {
						ingredients[i] = inventory.getItem(20 + i);
					}

					for (final ItemStack ingredient : ingredients)
						if (ingredient != null && ingredient.getType() != Material.AIR) {
							if (playerInventory.firstEmpty() != -1)
								playerInventory.addItem(ingredient);
							else
								world.dropItem(location, ingredient);

							ingredient.setAmount(0);
						}
				}

				brewingInventories.remove(inventory);
			}
		}
	}

	public boolean containsEquals(final Object object, final Iterable<?> collection) {
		for (final Object object1 : collection) {
			if (object.equals(object1)) {
				return true;
			}
		}

		return false;
	}
}