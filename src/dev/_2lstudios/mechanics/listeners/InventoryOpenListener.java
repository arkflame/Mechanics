package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.brewing.BrewingManager;
import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryOpenListener implements Listener {
  private final BrewingManager brewingManager;
  private final EnchantingManager enchantingManager;

  public InventoryOpenListener(GameMechanicsManager gameMechanicsManager) {
    this.brewingManager = gameMechanicsManager.getBrewingManager();
    this.enchantingManager = gameMechanicsManager.getEnchantingManager();
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onInventoryOpen(InventoryOpenEvent event) {
    if (!event.isCancelled()) {
      Inventory inventory = event.getInventory();
      InventoryType inventoryType = inventory.getType();

      if (inventoryType == InventoryType.ENCHANTING) {
        inventory.setItem(1, this.enchantingManager.getLapisLazuli());
      } else if (inventoryType == InventoryType.BREWING) {
        InventoryHolder holder = inventory.getHolder();

        if (holder instanceof BrewingStand) {
          BrewingStand brewingStand = (BrewingStand) holder;

          if (VersionUtil.isOneDotNine() && brewingStand.getFuelLevel() < 19) {
            brewingStand.setFuelLevel(20);
            brewingStand.update();
          }

          event.getPlayer().openInventory(this.brewingManager.getBrewingInventory(brewingStand));
          event.setCancelled(true);
        }
      }
    }
  }
}
