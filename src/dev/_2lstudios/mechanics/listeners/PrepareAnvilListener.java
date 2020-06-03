package dev._2lstudios.mechanics.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;

public class PrepareAnvilListener implements Listener {
	final private EnchantingManager enchantingManager;

	public PrepareAnvilListener(final GameMechanicsManager gameMechanicsManager) {
		this.enchantingManager = gameMechanicsManager.getEnchantingManager();
	}

	@EventHandler(ignoreCancelled = true)
	public void onPrepareAnvil(final PrepareAnvilEvent event) {
		final AnvilInventory anvilInventory = event.getInventory();

		if (anvilInventory != null) {
			final Location inventoryLocation = anvilInventory.getLocation();
			final ItemStack result = event.getResult();

			if (inventoryLocation != null) {
				final Block block = inventoryLocation.getBlock();

				if (block != null) {
					block.setData((byte) 0);
				}
			}

			if (result != null) {
				final Material material = result.getType();

				if (material == Material.TRIPWIRE_HOOK)
					event.setResult(new ItemStack(Material.AIR));
				else if (material != Material.AIR) {
					enchantingManager.fixEnchants(result);

					if (anvilInventory.getRepairCost() > 2) {
						anvilInventory.setRepairCost(2);
					}
				}
			}
		}
	}
}
