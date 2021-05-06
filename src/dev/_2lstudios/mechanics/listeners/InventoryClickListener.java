package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.brewing.BrewingManager;
import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.VersionUtil;
import java.util.Collection;
import java.util.HashSet;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

public class InventoryClickListener implements Listener {
  private final Plugin plugin;
  private final EnchantingManager enchantingManager;
  private final BrewingManager brewingManager;

  public InventoryClickListener(Plugin plugin, GameMechanicsManager gameMechanicsManager) {
    this.plugin = plugin;
    this.enchantingManager = gameMechanicsManager.getEnchantingManager();
    this.brewingManager = gameMechanicsManager.getBrewingManager();
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    InventoryType inventoryType = event.getInventory().getType();
    int slot = event.getSlot();

    if (inventoryType == InventoryType.ENCHANTING && event.getSlotType() == InventoryType.SlotType.CRAFTING
        && slot == 1) {
      event.setCancelled(true);
    } else if (VersionUtil.isOneDotNine() && event.getSlotType() == InventoryType.SlotType.QUICKBAR && slot == 40) {
      event.setCancelled(true);
    } else {
      ItemStack currentItem = event.getCurrentItem();

      if (currentItem != null)
        if (containsEquals(currentItem, (Object[]) this.brewingManager.getGlasses())) {
          event.setCancelled(true);
        } else if (currentItem.equals(this.brewingManager.getCraftAnvil())) {
          Collection<Material> ingredientTypes = new HashSet<>();
          Inventory clickedInventory = event.getClickedInventory();
          HumanEntity whoClicked = event.getWhoClicked();
          Location whoClickedLocation = whoClicked.getLocation();
          ItemStack[] ingredients = new ItemStack[5];

          for (int i = 0; i < 5; i++)
            ingredients[i] = clickedInventory.getItem(20 + i);
          byte b;
          int j;
          ItemStack[] arrayOfItemStack1;
          for (j = (arrayOfItemStack1 = ingredients).length, b = 0; b < j;) {
            ItemStack ingredient = arrayOfItemStack1[b];
            if (ingredient != null) {
              ingredientTypes.add(ingredient.getType());
            }
            b++;
          }

          if (!ingredientTypes.isEmpty()) {
            PlayerInventory playerInventory = whoClicked.getInventory();
            ItemStack[] waterBottles = new ItemStack[3];
            int bottleCount = 0;
            byte b1;
            int k;
            ItemStack[] arrayOfItemStack2;
            for (k = (arrayOfItemStack2 = playerInventory.getContents()).length, b1 = 0; b1 < k;) {
              ItemStack itemStack = arrayOfItemStack2[b1];
              if (itemStack != null && itemStack.getType() == Material.POTION) {
                if (VersionUtil.isOneDotNine()) {
                  PotionType potionType = ((PotionMeta) itemStack.getItemMeta()).getBasePotionData().getType();

                  if (potionType == PotionType.WATER) {
                    waterBottles[bottleCount] = itemStack;
                    bottleCount++;

                    if (bottleCount == 3) {
                      break;
                    }
                  }
                } else if (itemStack.getDurability() == 0) {
                  waterBottles[bottleCount] = itemStack;
                  bottleCount++;

                  if (bottleCount == 3) {
                    break;
                  }
                }
              }

              b1++;
            }

            if (bottleCount > 0) {
              if (this.brewingManager.tryCraftPotion(whoClicked.getLocation(), (Inventory) playerInventory,
                  waterBottles, ingredientTypes)) {
                InventoryHolder inventoryHolder = clickedInventory.getHolder();
                ItemStack[] arrayOfItemStack;
                for (int m = (arrayOfItemStack = ingredients).length; k < m;) {
                  ItemStack ingredient = arrayOfItemStack[k];
                  if (ingredient != null) {
                    int amount = ingredient.getAmount() - 1;

                    if (amount > 0) {
                      ingredient.setAmount(amount);
                    } else {
                      clickedInventory.remove(ingredient);
                    }
                  }
                  k++;
                }

                if (VersionUtil.isOneDotNine() && inventoryHolder instanceof BrewingStand) {
                  BrewingStand brewingStand = (BrewingStand) inventoryHolder;

                  this.plugin.getServer().getPluginManager()
                      .callEvent((Event) new BrewEvent(brewingStand.getBlock(), brewingStand.getInventory(), 20));
                }

                if (VersionUtil.isOneDotNine()) {
                  whoClickedLocation.getWorld().playSound(whoClickedLocation,
                      Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), 1.0F, 8.0F);
                } else {
                  whoClickedLocation.getWorld().playSound(whoClickedLocation, Sound.valueOf("ORB_PICKUP"), 1.0F, 8.0F);
                }

                whoClicked.sendMessage(ChatColor.GREEN + "Pocion creada correctamente!");
              } else {
                for (k = (arrayOfItemStack2 = ingredients).length, b1 = 0; b1 < k;) {
                  ItemStack ingredient = arrayOfItemStack2[b1];
                  if (ingredient != null)
                    ingredient.setAmount(ingredient.getAmount() - 1);
                  b1++;
                }

                if (VersionUtil.isOneDotNine()) {
                  whoClickedLocation.getWorld().playSound(whoClickedLocation, Sound.valueOf("ENTITY_ENDERMEN_TELEPORT"),
                      0.5F, 8.0F);
                } else {
                  whoClickedLocation.getWorld().playSound(whoClickedLocation, Sound.valueOf("ENDERMAN_TELEPORT"), 0.5F,
                      8.0F);
                }

                whoClicked.sendMessage(ChatColor.RED
                    + "No has logrado crear ninguna pocion! Nota: Las pociones de fuerza estan deshabilitadas");
              }
            } else {
              whoClicked.sendMessage(ChatColor.RED + "No tienes botellas de agua en tu inventario!");
            }
          } else {
            whoClicked.sendMessage(ChatColor.RED + "No colocaste ningun ingrediente!");
          }

          event.setCancelled(true);
        } else if (currentItem.equals(this.brewingManager.getCloseItem())) {
          event.setCancelled(true);
          event.getWhoClicked().closeInventory();
        } else {
          event.setCurrentItem(this.enchantingManager.fixEnchants(currentItem));
        }
    }
  }

  public boolean containsEquals(Object object, Object[] collection) {
    byte b;
    int i;
    Object[] arrayOfObject;
    for (i = (arrayOfObject = collection).length, b = 0; b < i;) {
      Object object1 = arrayOfObject[b];
      if (object.equals(object1)) {
        return true;
      }
      b++;
    }

    return false;
  }
}
