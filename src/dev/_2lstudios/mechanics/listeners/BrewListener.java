package dev._2lstudios.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class BrewListener implements Listener {
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBrew(final BrewEvent event) {
		if (VersionUtil.isOneDotNine()) {
			final BlockState blockState = event.getBlock().getState();

			if (blockState instanceof BrewingStand) {
				final BrewingStand brewingStand = (BrewingStand) blockState;

				if (brewingStand.getFuelLevel() < 19) {
					brewingStand.setFuelLevel(20);
					brewingStand.update();
				}

				final ItemStack ingredient = event.getContents().getIngredient();

				if (ingredient != null && ingredient.getType() == Material.BLAZE_POWDER)
					event.setCancelled(true);
			}
		}
	}
}
