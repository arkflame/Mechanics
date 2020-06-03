package dev._2lstudios.mechanics.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;
import dev._2lstudios.mechanics.managers.BrewingManager;
import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.VersionUtil;

import java.util.Collection;
import java.util.HashSet;

public class InventoryClickListener implements Listener {
	final private Plugin plugin;
	final private EnchantingManager enchantingManager;
	final private BrewingManager brewingManager;

	public InventoryClickListener(final Plugin plugin, final GameMechanicsManager gameMechanicsManager) {
		this.plugin = plugin;
		this.enchantingManager = gameMechanicsManager.getEnchantingManager();
		this.brewingManager = gameMechanicsManager.getBrewingManager();
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onInventoryClick(final InventoryClickEvent event) {
		final InventoryType inventoryType = event.getInventory().getType();
		final int slot = event.getSlot();

		if (inventoryType == InventoryType.ENCHANTING && event.getSlotType() == InventoryType.SlotType.CRAFTING
				&& slot == 1)
			event.setCancelled(true);
		else if (VersionUtil.isOneDotNine() && event.getSlotType() == InventoryType.SlotType.QUICKBAR && slot == 40)
			event.setCancelled(true);
		else {
			final ItemStack currentItem = event.getCurrentItem();

			if (currentItem != null) {
				if (currentItem.equals(brewingManager.getBlackGlass())
						|| currentItem.equals(brewingManager.getRedGlass())) {
					event.setCancelled(true);
				} else if (currentItem.equals(brewingManager.getCraftAnvil())) {
					final Collection<Material> ingredientTypes = new HashSet<>();
					final Inventory clickedInventory = event.getClickedInventory();
					final HumanEntity whoClicked = event.getWhoClicked();
					final ItemStack[] ingredients = new ItemStack[] { clickedInventory.getItem(20),
							clickedInventory.getItem(21), clickedInventory.getItem(23), clickedInventory.getItem(24) };

					for (final ItemStack ingredient : ingredients) {
						if (ingredient != null)
							ingredientTypes.add(ingredient.getType());
					}

					if (!ingredientTypes.isEmpty()) {
						final Inventory whoClickedInventory = whoClicked.getInventory();
						final ItemStack[] waterBottles = new ItemStack[3];
						int bottleCount = 0;

						for (final ItemStack itemStack : whoClickedInventory.getContents()) {
							if (itemStack != null) {
								if (itemStack.getType() == Material.POTION) {
									if (VersionUtil.isOneDotNine()) {
										final PotionType potionType = ((PotionMeta) itemStack.getItemMeta())
												.getBasePotionData().getType();

										if (potionType == PotionType.WATER) {
											waterBottles[bottleCount] = itemStack;
											bottleCount++;

											if (bottleCount == 3)
												break;
										}
									} else {
										if (itemStack.getDurability() == 0) {
											waterBottles[bottleCount] = itemStack;
											bottleCount++;

											if (bottleCount == 3)
												break;
										}
									}
								}
							}
						}

						if (bottleCount > 0) {
							if (brewingManager.tryCraftPotion(event.getWhoClicked().getLocation(), whoClickedInventory,
									waterBottles, ingredientTypes)) {
								final InventoryHolder inventoryHolder = clickedInventory.getHolder();

								for (final ItemStack ingredient : ingredients) {
									if (ingredient != null) {
										final int amount = ingredient.getAmount() - 1;

										if (amount > 0) {
											ingredient.setAmount(amount);
										} else {
											clickedInventory.remove(ingredient);
										}
									}
								}

								if (VersionUtil.isOneDotNine() && inventoryHolder instanceof BrewingStand) {
									final BrewingStand brewingStand = (BrewingStand) inventoryHolder;

									plugin.getServer().getPluginManager().callEvent(
											new BrewEvent(brewingStand.getBlock(), brewingStand.getInventory(), 20));
								}

								if (VersionUtil.isOneDotNine()) {
									whoClicked.getWorld().playSound(whoClicked.getLocation(),
											Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), 1, 8);
								} else {
									whoClicked.getWorld().playSound(whoClicked.getLocation(),
											Sound.valueOf("ORB_PICKUP"), 1, 8);
								}

								whoClicked.sendMessage(ChatColor.GREEN + "Pocion creada correctamente!");
							} else {
								for (final ItemStack ingredient : ingredients) {
									if (ingredient != null)
										ingredient.setAmount(ingredient.getAmount() - 1);
								}

								if (VersionUtil.isOneDotNine())
									whoClicked.getWorld().playSound(whoClicked.getLocation(),
											Sound.valueOf("ENTITY_ENDERMEN_TELEPORT"), 0.5F, 8);
								else
									whoClicked.getWorld().playSound(whoClicked.getLocation(),
											Sound.valueOf("ENDERMAN_TELEPORT"), 0.5F, 8);

								whoClicked.sendMessage(ChatColor.RED
										+ "No has logrado crear ninguna pocion! Nota: Las pociones de fuerza estan deshabilitadas");
							}
						} else
							whoClicked.sendMessage(ChatColor.RED + "No tienes botellas de agua en tu inventario!");
					} else
						whoClicked.sendMessage(ChatColor.RED + "No colocaste ningun ingrediente!");

					event.setCancelled(true);
				} else if (currentItem.equals(brewingManager.getCloseItem())) {
					event.setCancelled(true);
					event.getWhoClicked().closeInventory();
				} else {
					event.setCurrentItem(enchantingManager.fixEnchants(currentItem));
				}
			}
		}
	}
}
