package dev._2lstudios.mechanics.brewing;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class BrewingManager {
  private final Plugin plugin;
  private final HashMap<BrewingStand, Inventory> brewingInventories = new HashMap<>();
  private final Collection<BrewingPotion> brewingPotions = new HashSet<>();

  private final ItemStack[] glasses;

  private final ItemStack craftAnvil;

  private final ItemStack closeItem;

  public BrewingManager(Plugin plugin) {
    Material stainedGlassPaneMaterial = Material.getMaterial("STAINED_GLASS_PANE");

    this.plugin = plugin;
    this.glasses = new ItemStack[] { new ItemStack(stainedGlassPaneMaterial, 1, (short) 15),
        new ItemStack(stainedGlassPaneMaterial, 1, (short) 14), new ItemStack(stainedGlassPaneMaterial, 1, (short) 5) };
    this.craftAnvil = new ItemStack(Material.ANVIL, 1);
    this.closeItem = new ItemStack(Material.REDSTONE, 1);
    byte b;
    int i;
    ItemStack[] arrayOfItemStack;
    for (i = (arrayOfItemStack = this.glasses).length, b = 0; b < i;) {
      ItemStack itemStack = arrayOfItemStack[b];
      ItemMeta itemMeta = itemStack.getItemMeta();

      itemMeta.setDisplayName("");
      itemStack.setItemMeta(itemMeta);
      b++;
    }

    ItemMeta craftAnvilItemMeta = this.craftAnvil.getItemMeta();
    craftAnvilItemMeta.setDisplayName(ChatColor.GREEN + "Crear Pocion");
    craftAnvilItemMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Agrega ingredientes a los slots vacios,",
        ChatColor.GRAY + "las botellas de agua que tengas en el",
        ChatColor.GRAY + "inventario se convertiran en pociones!" }));
    this.craftAnvil.setItemMeta(craftAnvilItemMeta);

    ItemMeta closeItemItemMeta = this.closeItem.getItemMeta();
    closeItemItemMeta.setDisplayName(ChatColor.RED + "Cerrar");
    this.closeItem.setItemMeta(closeItemItemMeta);

    Material netherStalk = Material.getMaterial("NETHER_STALK");
    Material fermentedSpiderEye = Material.FERMENTED_SPIDER_EYE, rabbitFoot = Material.RABBIT_FOOT;
    Material sugar = Material.SUGAR, magmaCream = Material.MAGMA_CREAM, goldenCarrot = Material.GOLDEN_CARROT;
    Material rawFish = Material.getMaterial("RAW_FISH"), speckledMelon = Material.getMaterial("SPECKLED_MELON");
    Material spiderEye = Material.SPIDER_EYE, blazePowder = Material.BLAZE_POWDER, ghastTear = Material.GHAST_TEAR;

    Material[] slownessRequired = { netherStalk, fermentedSpiderEye };
    Material[] slownessOptional = { rabbitFoot, sugar, magmaCream };

    this.brewingPotions.add(new BrewingPotion(slownessRequired, slownessOptional, false, false, (short) 8202));
    this.brewingPotions.add(new BrewingPotion(slownessRequired, slownessOptional, false, true, (short) 8266));
    this.brewingPotions.add(new BrewingPotion(slownessRequired, slownessOptional, true, false, (short) 16394));
    this.brewingPotions.add(new BrewingPotion(slownessRequired, slownessOptional, true, true, (short) 16458));

    Material[] harmingRequired = { netherStalk, fermentedSpiderEye };
    Material[] harmingOptional = { rawFish, speckledMelon, spiderEye };

    this.brewingPotions.add(new BrewingPotion(harmingRequired, harmingOptional, false, false, (short) 8204));
    this.brewingPotions.add(new BrewingPotion(harmingRequired, harmingOptional, false, true, (short) 8236));
    this.brewingPotions.add(new BrewingPotion(harmingRequired, harmingOptional, true, false, (short) 16396));
    this.brewingPotions.add(new BrewingPotion(harmingRequired, harmingOptional, true, true, (short) 16428));

    Material[] invisibilityRequired = { netherStalk, fermentedSpiderEye, goldenCarrot };

    this.brewingPotions.add(new BrewingPotion(invisibilityRequired, null, false, false, (short) 8206));
    this.brewingPotions.add(new BrewingPotion(invisibilityRequired, null, false, true, (short) 8270));
    this.brewingPotions.add(new BrewingPotion(invisibilityRequired, null, true, false, (short) 16398));
    this.brewingPotions.add(new BrewingPotion(invisibilityRequired, null, true, true, (short) 16462));

    Material[] weaknessRequired = { fermentedSpiderEye };

    this.brewingPotions.add(new BrewingPotion(weaknessRequired, null, false, false, (short) 8200));
    this.brewingPotions.add(new BrewingPotion(weaknessRequired, null, false, true, (short) 8264));
    this.brewingPotions.add(new BrewingPotion(weaknessRequired, null, true, false, (short) 16392));
    this.brewingPotions.add(new BrewingPotion(weaknessRequired, null, true, true, (short) 16456));

    Material[] leapingRequired = { netherStalk, rabbitFoot };

    this.brewingPotions.add(new BrewingPotion(leapingRequired, null, false, false, (short) 8267));
    this.brewingPotions.add(new BrewingPotion(leapingRequired, null, false, true, (short) 8235));
    this.brewingPotions.add(new BrewingPotion(leapingRequired, null, true, false, (short) 16459));
    this.brewingPotions.add(new BrewingPotion(leapingRequired, null, true, true, (short) 16427));

    Material[] fireResistanceRequired = { netherStalk, magmaCream };

    this.brewingPotions.add(new BrewingPotion(fireResistanceRequired, null, false, false, (short) 8195));
    this.brewingPotions.add(new BrewingPotion(fireResistanceRequired, null, false, true, (short) 8259));
    this.brewingPotions.add(new BrewingPotion(fireResistanceRequired, null, true, false, (short) 16387));
    this.brewingPotions.add(new BrewingPotion(fireResistanceRequired, null, true, true, (short) 16451));

    Material[] swiftnessRequired = { netherStalk, sugar };

    this.brewingPotions.add(new BrewingPotion(swiftnessRequired, null, false, false, (short) 8194));
    this.brewingPotions.add(new BrewingPotion(swiftnessRequired, null, false, true, (short) 8226));
    this.brewingPotions.add(new BrewingPotion(swiftnessRequired, null, true, false, (short) 16386));
    this.brewingPotions.add(new BrewingPotion(swiftnessRequired, null, true, true, (short) 16418));

    Material[] waterBreathingRequired = { netherStalk, rawFish };

    this.brewingPotions.add(new BrewingPotion(waterBreathingRequired, null, false, false, (short) 8205));
    this.brewingPotions.add(new BrewingPotion(waterBreathingRequired, null, false, true, (short) 8269));
    this.brewingPotions.add(new BrewingPotion(waterBreathingRequired, null, true, false, (short) 16397));
    this.brewingPotions.add(new BrewingPotion(waterBreathingRequired, null, true, true, (short) 16461));

    Material[] healingRequired = { netherStalk, speckledMelon };

    this.brewingPotions.add(new BrewingPotion(healingRequired, null, false, false, (short) 8197));
    this.brewingPotions.add(new BrewingPotion(healingRequired, null, false, true, (short) 8229));
    this.brewingPotions.add(new BrewingPotion(healingRequired, null, true, false, (short) 16389));
    this.brewingPotions.add(new BrewingPotion(healingRequired, null, true, true, (short) 16421));

    Material[] poisonRequired = { spiderEye };

    this.brewingPotions.add(new BrewingPotion(poisonRequired, null, false, false, (short) 8196));
    this.brewingPotions.add(new BrewingPotion(poisonRequired, null, false, true, (short) 8228));
    this.brewingPotions.add(new BrewingPotion(poisonRequired, null, true, false, (short) 16388));
    this.brewingPotions.add(new BrewingPotion(poisonRequired, null, true, true, (short) 16420));

    Material[] nightVisionRequired = { goldenCarrot };

    this.brewingPotions.add(new BrewingPotion(nightVisionRequired, null, false, false, (short) 8198));
    this.brewingPotions.add(new BrewingPotion(nightVisionRequired, null, false, true, (short) 8262));
    this.brewingPotions.add(new BrewingPotion(nightVisionRequired, null, true, false, (short) 16390));
    this.brewingPotions.add(new BrewingPotion(nightVisionRequired, null, true, true, (short) 16454));

    Material[] regenerationRequired = { ghastTear };

    this.brewingPotions.add(new BrewingPotion(regenerationRequired, null, false, false, (short) 8193));
    this.brewingPotions.add(new BrewingPotion(regenerationRequired, null, false, true, (short) 8225));
    this.brewingPotions.add(new BrewingPotion(regenerationRequired, null, true, false, (short) 16385));
    this.brewingPotions.add(new BrewingPotion(regenerationRequired, null, true, true, (short) 16417));

    Material[] strengthRequired = { blazePowder };

    this.brewingPotions.add(new BrewingPotion(strengthRequired, null, false, false, (short) 8201));
    this.brewingPotions.add(new BrewingPotion(strengthRequired, null, false, true, (short) 8233));
    this.brewingPotions.add(new BrewingPotion(strengthRequired, null, true, false, (short) 16393));
    this.brewingPotions.add(new BrewingPotion(strengthRequired, null, true, true, (short) 16425));
  }

  public Inventory getBrewingInventory(BrewingStand brewingStand) {
    Inventory brewingInventory = this.brewingInventories.getOrDefault(brewingStand, this.plugin.getServer()
        .createInventory((InventoryHolder) brewingStand, 54, ChatColor.DARK_GRAY + "Destiladora"));

    if (!this.brewingInventories.containsKey(brewingStand)) {
      this.brewingInventories.put(brewingStand, brewingInventory);
    }

    for (int i = 0; i < 54; i++) {
      if (i > 28 && i < 34) {
        brewingInventory.setItem(i, this.glasses[2]);
      } else if (i > 44) {
        brewingInventory.setItem(i, this.glasses[1]);
      } else if (i < 20 || (i > 24 && i < 45)) {
        brewingInventory.setItem(i, this.glasses[0]);
      }
    }

    brewingInventory.setItem(31, this.craftAnvil);
    brewingInventory.setItem(49, this.closeItem);

    return brewingInventory;
  }

  public boolean tryCraftPotion(Location playerLocation, Inventory inventory, ItemStack[] waterBottles,
      Collection<Material> ingredients) {
    boolean extended = !(!ingredients.contains(Material.GLOWSTONE_DUST) && !ingredients.contains(Material.REDSTONE));
    boolean splash = ingredients.contains(Material.getMaterial("SULPHUR"));
    Material potionMaterial = Material.POTION;
    BrewingPotion craftedPotion = null;
    int lastIngredients = 0;

    for (BrewingPotion brewingPotion : this.brewingPotions) {
      if (brewingPotion.isExtended() == extended && brewingPotion.isSplash() == splash) {
        Material[] requiredMaterials = brewingPotion.getRequiredMaterials();
        int currentIngredients = 0;
        boolean required = true, optional = false;

        if (requiredMaterials != null) {
          byte b;
          int i;
          Material[] arrayOfMaterial;
          for (i = (arrayOfMaterial = requiredMaterials).length, b = 0; b < i;) {
            Material ingredient = arrayOfMaterial[b];
            if (!ingredients.contains(ingredient)) {
              required = false;
              break;
            }
            currentIngredients++;

            b++;
          }

        }
        if (required) {
          Material[] optionalMaterials = brewingPotion.getOptionalMaterials();

          if (optionalMaterials != null) {
            byte b;
            int i;
            Material[] arrayOfMaterial;
            for (i = (arrayOfMaterial = optionalMaterials).length, b = 0; b < i;) {
              Material ingredient = arrayOfMaterial[b];
              if (ingredients.contains(ingredient)) {
                optional = true;
                currentIngredients++;
              }
              b++;
            }

          } else {
            optional = true;
          }

          if (optional && currentIngredients > lastIngredients) {
            lastIngredients = currentIngredients;
            craftedPotion = brewingPotion;
          }
        }
      }
    }

    if (craftedPotion != null) {
      ItemStack potion = new ItemStack(potionMaterial, 1, craftedPotion.getData());
      byte b;
      int i;
      ItemStack[] arrayOfItemStack;
      for (i = (arrayOfItemStack = waterBottles).length, b = 0; b < i;) {
        ItemStack waterBottle = arrayOfItemStack[b];
        if (waterBottle != null) {
          int firstWaterBottleIndex = inventory.first(waterBottle);
          ItemStack waterBottle1 = inventory.getItem(firstWaterBottleIndex);
          int waterBottle1Amount = waterBottle1.getAmount() - 1;

          if (waterBottle1Amount > 0) {
            waterBottle1.setAmount(waterBottle1Amount);
          } else {
            inventory.setItem(firstWaterBottleIndex, null);
          }

          if (inventory.firstEmpty() != -1) {
            inventory.addItem(new ItemStack[] { potion });
          } else {
            playerLocation.getWorld().dropItem(playerLocation, potion);
          }
        }
        b++;
      }

    }
    return (craftedPotion != null);
  }

  public HashMap<BrewingStand, Inventory> getBrewingInventories() {
    return this.brewingInventories;
  }

  public ItemStack[] getGlasses() {
    return this.glasses;
  }

  public ItemStack getCraftAnvil() {
    return this.craftAnvil;
  }

  public ItemStack getCloseItem() {
    return this.closeItem;
  }
}
