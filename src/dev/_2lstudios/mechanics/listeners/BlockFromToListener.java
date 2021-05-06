package dev._2lstudios.mechanics.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onBlockFromTo(BlockFromToEvent event) {
    Block block = event.getBlock();

    int id = block.getType().getId();

    if (id >= 8 && id <= 11) {
      Block toBlock = event.getToBlock();
      int toid = toBlock.getType().getId();

      if (toid == 0 && generatesCobble(id, toBlock)) {
        event.setCancelled(true);
      }
    }
  }

  private final BlockFace[] faces = new BlockFace[] { BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH,
      BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

  public final boolean generatesCobble(int id, Block b) {
    int mirrorID1 = (id == 8 || id == 9) ? 10 : 8;
    int mirrorID2 = (id == 8 || id == 9) ? 10 : 8;
    byte b1;
    int i;
    BlockFace[] arrayOfBlockFace;
    for (i = (arrayOfBlockFace = this.faces).length, b1 = 0; b1 < i;) {
      BlockFace face = arrayOfBlockFace[b1];
      Block r = b.getRelative(face, 1);
      int blockId = r.getType().getId();

      if (blockId == mirrorID1 || blockId == mirrorID2)
        return true;
      b1++;
    }

    return false;
  }
}
