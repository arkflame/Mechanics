package dev._2lstudios.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.MaterialManager;

public class BlockDamageListener implements Listener {
	final private MaterialManager materialManager;

	public BlockDamageListener(final GameMechanicsManager gameMechanicsManager) {
		this.materialManager = gameMechanicsManager.getMaterialManager();
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockDamage(final BlockDamageEvent event) {
		final Block block = event.getBlock();

		if (block != null) {
			final Material material = block.getType();
			final Material material2 = materialManager.getMaterial(material);

			if (material2 != null)
				block.setType(material2);
		}
	}
}
