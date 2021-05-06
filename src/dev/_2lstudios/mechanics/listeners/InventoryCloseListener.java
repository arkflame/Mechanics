package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.brewing.BrewingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryCloseListener implements Listener {
  private final BrewingManager brewingManager;

  public InventoryCloseListener(GameMechanicsManager gameMechanicsManager) {
    this.brewingManager = gameMechanicsManager.getBrewingManager();
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClose(InventoryCloseEvent event) {
    Inventory inventory = event.getInventory();

    if (inventory.getType() == InventoryType.ENCHANTING) {
      EnchantingInventory inv = (EnchantingInventory) event.getInventory();

      inv.setItem(1, null);
    } else {
      Collection<Inventory> brewingInventories = this.brewingManager.getBrewingInventories().values();

      if (containsEquals(inventory, brewingInventories)) {
        InventoryHolder inventoryHolder = inventory.getHolder();

        if (inventoryHolder instanceof BrewingStand) {
          Location location = ((BrewingStand) inventoryHolder).getLocation();
          HumanEntity player = event.getPlayer();
          PlayerInventory playerInventory = player.getInventory();
          World world = location.getWorld();
          ItemStack[] ingredients = new ItemStack[5];

          for (int i = 0; i < 5; i++) {
            ingredients[i] = inventory.getItem(20 + i);
            inventory.setItem(20 + i, null);
          }
          byte b;
          int j;
          ItemStack[] arrayOfItemStack1;
          for (j = (arrayOfItemStack1 = ingredients).length, b = 0; b < j;) {
            ItemStack ingredient = arrayOfItemStack1[b];
            if (ingredient != null && ingredient.getType() != Material.AIR) {
              if (playerInventory.firstEmpty() != -1) {
                playerInventory.addItem(new ItemStack[] { ingredient });
              } else {
                world.dropItem(location, ingredient);
              }
            }
            b++;
          }

        }
        brewingInventories.remove(inventory);
      }
    }
  }

  public boolean containsEquals(Object object, Iterable<?> collection) {
    for (Object object1 : collection) {
      if (object.equals(object1)) {
        return true;
      }
    }

    return false;
  }
}
