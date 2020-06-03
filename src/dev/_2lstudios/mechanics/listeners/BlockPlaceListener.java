package dev._2lstudios.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.MaterialManager;

public class BlockPlaceListener implements Listener {
	final private MaterialManager materialManager;

	public BlockPlaceListener(final GameMechanicsManager gameMechanicsManager) {
		this.materialManager = gameMechanicsManager.getMaterialManager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPlace(final BlockPlaceEvent event) {
		final Block block = event.getBlock();

		if (block != null) {
			final Material material = block.getType();
			final Material material2 = materialManager.getMaterial(material);

			if (material2 != null) {
				block.setType(material2);
			}
		}
	}
}
