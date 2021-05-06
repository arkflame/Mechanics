package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class EnchantItemListener implements Listener {
  private final EnchantingManager enchantingManager;

  public EnchantItemListener(GameMechanicsManager gameMechanicsManager) {
    this.enchantingManager = gameMechanicsManager.getEnchantingManager();
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onEnchantItem(EnchantItemEvent event) {
    Map<Enchantment, Integer> enchantsToAdd = event.getEnchantsToAdd();
    Inventory inventory = event.getInventory();
    InventoryType inventoryType = inventory.getType();

    this.enchantingManager.fixEnchants(enchantsToAdd);

    if (event.getExpLevelCost() > 2) {
      event.setExpLevelCost(2);
    }
    if (inventoryType == InventoryType.ENCHANTING)
      inventory.setItem(1, this.enchantingManager.getLapisLazuli());
  }
}
