package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.MaterialManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockDamageListener implements Listener {
  private final MaterialManager materialManager;

  public BlockDamageListener(GameMechanicsManager gameMechanicsManager) {
    this.materialManager = gameMechanicsManager.getMaterialManager();
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onBlockDamage(BlockDamageEvent event) {
    Block block = event.getBlock();

    if (block != null) {
      Material material = block.getType();
      Material material2 = this.materialManager.getMaterial(material);

      if (material2 != null)
        block.setType(material2);
    }
  }
}
