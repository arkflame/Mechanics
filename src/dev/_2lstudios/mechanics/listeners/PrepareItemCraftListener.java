package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.MaterialManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class PrepareItemCraftListener implements Listener {
  private final MaterialManager materialManager;

  public PrepareItemCraftListener(GameMechanicsManager gameMechanicsManager) {
    this.materialManager = gameMechanicsManager.getMaterialManager();
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onPrepareCraft(PrepareItemCraftEvent event) {
    Recipe recipe = event.getRecipe();

    if (recipe != null) {
      ItemStack result = recipe.getResult();

      if (result != null) {
        Material material = result.getType();
        Material material2 = this.materialManager.getMaterial(material);

        if (material2 != null)
          event.getInventory().setResult(new ItemStack(material2));
      }
    }
  }
}
