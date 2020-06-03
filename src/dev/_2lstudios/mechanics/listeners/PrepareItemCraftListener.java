package dev._2lstudios.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.MaterialManager;

public class PrepareItemCraftListener implements Listener {
	final private MaterialManager materialManager;

	public PrepareItemCraftListener(final GameMechanicsManager gameMechanicsManager) {
		this.materialManager = gameMechanicsManager.getMaterialManager();
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPrepareCraft(final PrepareItemCraftEvent event) {
		final Recipe recipe = event.getRecipe();

		if (recipe != null) {
			final ItemStack result = recipe.getResult();

			if (result != null) {
				final Material material = result.getType();
				final Material material2 = materialManager.getMaterial(material);

				if (material2 != null)
					event.getInventory().setResult(new ItemStack(material2));
			}
		}
	}
}
