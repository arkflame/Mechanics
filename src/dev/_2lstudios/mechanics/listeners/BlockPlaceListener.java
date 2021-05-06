package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.MaterialManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
  private final MaterialManager materialManager;

  public BlockPlaceListener(GameMechanicsManager gameMechanicsManager) {
    this.materialManager = gameMechanicsManager.getMaterialManager();
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onBlockPlace(BlockPlaceEvent event) {
    Block block = event.getBlock();

    if (block != null) {
      Material material = block.getType();
      Material material2 = this.materialManager.getMaterial(material);

      if (material2 != null)
        block.setType(material2);
    }
  }
}
