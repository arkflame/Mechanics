package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class PrepareAnvilListener implements Listener {
  private final EnchantingManager enchantingManager;

  public PrepareAnvilListener(GameMechanicsManager gameMechanicsManager) {
    this.enchantingManager = gameMechanicsManager.getEnchantingManager();
  }

  @EventHandler(ignoreCancelled = true)
  public void onPrepareAnvil(PrepareAnvilEvent event) {
    AnvilInventory anvilInventory = event.getInventory();
    Location inventoryLocation = anvilInventory.getLocation();
    ItemStack result = event.getResult();

    if (inventoryLocation != null) {
      Block block = inventoryLocation.getBlock();

      block.setData((byte) 1);
    }

    if (result != null) {
      Material material = result.getType();

      if (material == Material.TRIPWIRE_HOOK) {
        event.setResult(new ItemStack(Material.AIR));
      } else if (material != Material.AIR) {
        this.enchantingManager.fixEnchants(result);

        if (anvilInventory.getRepairCost() > 2)
          anvilInventory.setRepairCost(2);
      }
    }
  }
}
