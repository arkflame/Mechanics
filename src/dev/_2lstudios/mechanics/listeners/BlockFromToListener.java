package dev._2lstudios.mechanics.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockFromTo(final BlockFromToEvent event) {
		final Block block = event.getBlock();

		if (block != null) {
			final int id = block.getType().getId();

			if (id >= 8 && id <= 11) {
				final Block toBlock = event.getToBlock();
				final int toid = toBlock.getType().getId();

				if (toid == 0) {
					if (generatesCobble(id, toBlock)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

	private final BlockFace[] faces = new BlockFace[] { BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH,
			BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

	public final boolean generatesCobble(final int id, final Block b) {
		final int mirrorID1 = (id == 8 || id == 9 ? 10 : 8);
		final int mirrorID2 = (id == 8 || id == 9 ? 10 : 8);

		for (final BlockFace face : faces) {
			final Block r = b.getRelative(face, 1);

			if (r.getType().getId() == mirrorID1 || r.getType().getId() == mirrorID2) {
				return true;
			}
		}
		return false;
	}
}