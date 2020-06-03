package dev._2lstudios.mechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;

public class BlockDispenseListener implements Listener {
	final private EnchantingManager enchantingManager;

	public BlockDispenseListener(final GameMechanicsManager gameMechanicsManager) {
		this.enchantingManager = gameMechanicsManager.getEnchantingManager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockDispense(final BlockDispenseEvent event) {
		event.setItem(enchantingManager.fixEnchants(event.getItem()));
	}
}
