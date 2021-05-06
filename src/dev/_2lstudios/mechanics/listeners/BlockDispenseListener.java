package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class BlockDispenseListener implements Listener {
  private final EnchantingManager enchantingManager;

  public BlockDispenseListener(GameMechanicsManager gameMechanicsManager) {
    this.enchantingManager = gameMechanicsManager.getEnchantingManager();
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onBlockDispense(BlockDispenseEvent event) {
    event.setItem(this.enchantingManager.fixEnchants(event.getItem()));
  }
}
