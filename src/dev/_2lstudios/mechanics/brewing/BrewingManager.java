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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class BrewingManager {
	private final Plugin plugin;
	private final HashMap<BrewingStand, Inventory> brewingInventories = new HashMap<>();
	private final Collection<BrewingPotion> brewingPotions = new HashSet<>();
	private final ItemStack[] glasses;
	private final ItemStack craftAnvil, closeItem;

	public BrewingManager(final Plugin plugin) {
		final Material stainedGlassPaneMaterial;

		// TODO: Version check
		if (false) {
			stainedGlassPaneMaterial = Material.getMaterial("LEGACY_STAINED_GLASS_PANE");
		} else {
			stainedGlassPaneMaterial = Material.getMaterial("STAINED_GLASS_PANE");
		}

		this.plugin = plugin;
		this.glasses = new ItemStack[] { new ItemStack(stainedGlassPaneMaterial, 1, (short) 15),
				new ItemStack(stainedGlassPaneMaterial, 1, (short) 14),
				new ItemStack(stainedGlassPaneMaterial, 1, (short) 5) };
		this.craftAnvil = new ItemStack(Material.ANVIL, 1);
		this.closeItem = new ItemStack(Material.REDSTONE, 1);

		for (final ItemStack itemStack : this.glasses) {
			final ItemMeta itemMeta = itemStack.getItemMeta();

			itemMeta.setDisplayName("");
			itemStack.setItemMeta(itemMeta);
		}

		final ItemMeta craftAnvilItemMeta = craftAnvil.getItemMeta();
		craftAnvilItemMeta.setDisplayName(ChatColor.GREEN + "Crear Pocion");
		craftAnvilItemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Agrega ingredientes a los slots vacios,",
				ChatColor.GRAY + "las botellas de agua que tengas en el",
				ChatColor.GRAY + "inventario se convertiran en pociones!"));
		craftAnvil.setItemMeta(craftAnvilItemMeta);

		final ItemMeta closeItemItemMeta = closeItem.getItemMeta();
		closeItemItemMeta.setDisplayName(ChatColor.RED + "Cerrar");
		closeItem.setItemMeta(closeItemItemMeta);

		final Material netherStalk = Material.getMaterial("NETHER_STALK"),
				fermentedSpiderEye = Material.FERMENTED_SPIDER_EYE, rabbitFoot = Material.RABBIT_FOOT,
				sugar = Material.SUGAR, magmaCream = Material.MAGMA_CREAM, goldenCarrot = Material.GOLDEN_CARROT,
				rawFish = Material.getMaterial("RAW_FISH"), speckledMelon = Material.getMaterial("SPECKLED_MELON"),
				spiderEye = Material.SPIDER_EYE, blazePowder = Material.BLAZE_POWDER, ghastTear = Material.GHAST_TEAR;

		// Slowness
		final Material[] slownessRequired = new Material[] { netherStalk, fermentedSpiderEye },
				slownessOptional = new Material[] { rabbitFoot, sugar, magmaCream };

		this.brewingPotions.add(new BrewingPotion(slownessRequired, slownessOptional, false, false, (short) 8202));
		this.brewingPotions.add(new BrewingPotion(slownessRequired, slownessOptional, false, true, (short) 8266));
		this.brewingPotions.add(new BrewingPotion(slownessRequired, slownessOptional, true, false, (short) 16394));
		this.brewingPotions.add(new BrewingPotion(slownessRequired, slownessOptional, true, true, (short) 16458));

		// Harming
		final Material[] harmingRequired = new Material[] { netherStalk, fermentedSpiderEye },
				harmingOptional = new Material[] { rawFish, speckledMelon, spiderEye };

		this.brewingPotions.add(new BrewingPotion(harmingRequired, harmingOptional, false, false, (short) 8204));
		this.brewingPotions.add(new BrewingPotion(harmingRequired, harmingOptional, false, true, (short) 8236));
		this.brewingPotions.add(new BrewingPotion(harmingRequired, harmingOptional, true, false, (short) 16396));
		this.brewingPotions.add(new BrewingPotion(harmingRequired, harmingOptional, true, true, (short) 16428));

		// Invisibility
		final Material[] invisibilityRequired = new Material[] { netherStalk, fermentedSpiderEye, goldenCarrot };

		this.brewingPotions.add(new BrewingPotion(invisibilityRequired, null, false, false, (short) 8206));
		this.brewingPotions.add(new BrewingPotion(invisibilityRequired, null, false, true, (short) 8270));
		this.brewingPotions.add(new BrewingPotion(invisibilityRequired, null, true, false, (short) 16398));
		this.brewingPotions.add(new BrewingPotion(invisibilityRequired, null, true, true, (short) 16462));

		// Weakness
		final Material[] weaknessRequired = new Material[] { fermentedSpiderEye };

		this.brewingPotions.add(new BrewingPotion(weaknessRequired, null, false, false, (short) 8200));
		this.brewingPotions.add(new BrewingPotion(weaknessRequired, null, false, true, (short) 8264));
		this.brewingPotions.add(new BrewingPotion(weaknessRequired, null, true, false, (short) 16392));
		this.brewingPotions.add(new BrewingPotion(weaknessRequired, null, true, true, (short) 16456));

		// Leaping
		final Material[] leapingRequired = new Material[] { netherStalk, rabbitFoot };

		this.brewingPotions.add(new BrewingPotion(leapingRequired, null, false, false, (short) 8267));
		this.brewingPotions.add(new BrewingPotion(leapingRequired, null, false, true, (short) 8235));
		this.brewingPotions.add(new BrewingPotion(leapingRequired, null, true, false, (short) 16459));
		this.brewingPotions.add(new BrewingPotion(leapingRequired, null, true, true, (short) 16427));

		// Fire Resistance
		final Material[] fireResistanceRequired = new Material[] { netherStalk, magmaCream };

		this.brewingPotions.add(new BrewingPotion(fireResistanceRequired, null, false, false, (short) 8195));
		this.brewingPotions.add(new BrewingPotion(fireResistanceRequired, null, false, true, (short) 8259));
		this.brewingPotions.add(new BrewingPotion(fireResistanceRequired, null, true, false, (short) 16387));
		this.brewingPotions.add(new BrewingPotion(fireResistanceRequired, null, true, true, (short) 16451));

		// Swiftness
		final Material[] swiftnessRequired = new Material[] { netherStalk, sugar };

		this.brewingPotions.add(new BrewingPotion(swiftnessRequired, null, false, false, (short) 8194));
		this.brewingPotions.add(new BrewingPotion(swiftnessRequired, null, false, true, (short) 8226));
		this.brewingPotions.add(new BrewingPotion(swiftnessRequired, null, true, false, (short) 16386));
		this.brewingPotions.add(new BrewingPotion(swiftnessRequired, null, true, true, (short) 16418));

		// Water Breathing
		final Material[] waterBreathingRequired = new Material[] { netherStalk, rawFish };

		this.brewingPotions.add(new BrewingPotion(waterBreathingRequired, null, false, false, (short) 8205));
		this.brewingPotions.add(new BrewingPotion(waterBreathingRequired, null, false, true, (short) 8269));
		this.brewingPotions.add(new BrewingPotion(waterBreathingRequired, null, true, false, (short) 16397));
		this.brewingPotions.add(new BrewingPotion(waterBreathingRequired, null, true, true, (short) 16461));

		// Healing
		final Material[] healingRequired = new Material[] { netherStalk, speckledMelon };

		this.brewingPotions.add(new BrewingPotion(healingRequired, null, false, false, (short) 8197));
		this.brewingPotions.add(new BrewingPotion(healingRequired, null, false, true, (short) 8229));
		this.brewingPotions.add(new BrewingPotion(healingRequired, null, true, false, (short) 16389));
		this.brewingPotions.add(new BrewingPotion(healingRequired, null, true, true, (short) 16421));

		// Poison
		final Material[] poisonRequired = new Material[] { spiderEye };

		this.brewingPotions.add(new BrewingPotion(poisonRequired, null, false, false, (short) 8196));
		this.brewingPotions.add(new BrewingPotion(poisonRequired, null, false, true, (short) 8228));
		this.brewingPotions.add(new BrewingPotion(poisonRequired, null, true, false, (short) 16388));
		this.brewingPotions.add(new BrewingPotion(poisonRequired, null, true, true, (short) 16420));

		// Night Vision
		final Material[] nightVisionRequired = new Material[] { goldenCarrot };

		this.brewingPotions.add(new BrewingPotion(nightVisionRequired, null, false, false, (short) 8198));
		this.brewingPotions.add(new BrewingPotion(nightVisionRequired, null, false, true, (short) 8262));
		this.brewingPotions.add(new BrewingPotion(nightVisionRequired, null, true, false, (short) 16390));
		this.brewingPotions.add(new BrewingPotion(nightVisionRequired, null, true, true, (short) 16454));

		// Regeneration
		final Material[] regenerationRequired = new Material[] { ghastTear };

		this.brewingPotions.add(new BrewingPotion(regenerationRequired, null, false, false, (short) 8193));
		this.brewingPotions.add(new BrewingPotion(regenerationRequired, null, false, true, (short) 8225));
		this.brewingPotions.add(new BrewingPotion(regenerationRequired, null, true, false, (short) 16385));
		this.brewingPotions.add(new BrewingPotion(regenerationRequired, null, true, true, (short) 16417));

		// Strength
		// final Material[] strengthRequired = new Material[] { blazePowder };

		// this.brewingPotions.add(new BrewingPotion(strengthRequired, null, false,
		// false, (short) 8201));
		// this.brewingPotions.add(new BrewingPotion(strengthRequired, null, false,
		// true, (short) 8233));
		// this.brewingPotions.add(new BrewingPotion(strengthRequired, null, true,
		// false, (short) 16393));
		// this.brewingPotions.add(new BrewingPotion(strengthRequired, null, true, true,
		// (short) 16425));
	}

	public Inventory getBrewingInventory(final BrewingStand brewingStand) {
		final Inventory brewingInventory = brewingInventories.getOrDefault(brewingStand,
				plugin.getServer().createInventory(brewingStand, 54, ChatColor.DARK_GRAY + "Destiladora"));

		if (!brewingInventories.containsKey(brewingStand)) {
			brewingInventories.put(brewingStand, brewingInventory);
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

		brewingInventory.setItem(31, craftAnvil);
		brewingInventory.setItem(49, closeItem);

		return brewingInventory;
	}

	public boolean tryCraftPotion(final Location playerLocation, final Inventory inventory,
			final ItemStack[] waterBottles, final Collection<Material> ingredients) {
		final boolean extended = ingredients.contains(Material.GLOWSTONE_DUST)
				|| ingredients.contains(Material.REDSTONE);
		final boolean splash = ingredients.contains(Material.getMaterial("SULPHUR"));
		final Material potionMaterial = Material.POTION;
		BrewingPotion craftedPotion = null;
		int lastIngredients = 0;

		for (final BrewingPotion brewingPotion : this.brewingPotions) {
			if (brewingPotion.isExtended() == extended && brewingPotion.isSplash() == splash) {
				final Material[] requiredMaterials = brewingPotion.getRequiredMaterials();
				int currentIngredients = 0;
				boolean required = true, optional = false;

				if (requiredMaterials != null) {
					for (final Material ingredient : requiredMaterials) {
						if (!ingredients.contains(ingredient)) {
							required = false;
							break;
						} else {
							currentIngredients++;
						}
					}
				}

				if (required) {
					final Material[] optionalMaterials = brewingPotion.getOptionalMaterials();

					if (optionalMaterials != null) {
						for (final Material ingredient : optionalMaterials) {
							if (ingredients.contains(ingredient)) {
								optional = true;
								currentIngredients++;
								break;
							}
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
			final ItemStack potion = new ItemStack(potionMaterial, 1, craftedPotion.getData());

			for (final ItemStack waterBottle : waterBottles) {
				if (waterBottle != null) {
					final int firstWaterBottleIndex = inventory.first(waterBottle);
					final ItemStack waterBottle1 = inventory.getItem(firstWaterBottleIndex);
					final int waterBottle1Amount = waterBottle1.getAmount() - 1;

					if (waterBottle1Amount > 0) {
						waterBottle1.setAmount(waterBottle1Amount);
					} else {
						inventory.setItem(firstWaterBottleIndex, null);
					}

					if (inventory.firstEmpty() != -1) {
						inventory.addItem(potion);
					} else {
						playerLocation.getWorld().dropItem(playerLocation, potion);
					}
				}
			}
		}

		return craftedPotion != null;
	}

	public HashMap<BrewingStand, Inventory> getBrewingInventories() {
		return brewingInventories;
	}

	public ItemStack[] getGlasses() {
		return glasses;
	}

	public ItemStack getCraftAnvil() {
		return craftAnvil;
	}

	public ItemStack getCloseItem() {
		return closeItem;
	}
}
