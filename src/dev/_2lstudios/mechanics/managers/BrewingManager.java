package dev._2lstudios.mechanics.managers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

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
	private final ItemStack blackGlass;
	private final ItemStack redGlass;
	private final ItemStack craftAnvil;
	private final ItemStack closeItem;

	public BrewingManager(final Plugin plugin) {
		final Material stainedGlassPaneMaterial;

		// TODO: Version check
		if (true) {
			stainedGlassPaneMaterial = Material.getMaterial("LEGACY_STAINED_GLASS_PANE");
		} else {
			stainedGlassPaneMaterial = Material.getMaterial("STAINED_GLASS_PANE");
		}

		this.plugin = plugin;
		this.blackGlass = new ItemStack(stainedGlassPaneMaterial, 1, (short) 15);
		this.redGlass = new ItemStack(stainedGlassPaneMaterial, 1, (short) 14);
		this.craftAnvil = new ItemStack(Material.ANVIL, 1);
		this.closeItem = new ItemStack(Material.REDSTONE, 1);

		final ItemMeta blackGlassItemMeta = blackGlass.getItemMeta();
		blackGlassItemMeta.setDisplayName(ChatColor.DARK_GRAY + "");
		blackGlass.setItemMeta(blackGlassItemMeta);

		final ItemMeta redGlassItemMeta = redGlass.getItemMeta();
		redGlassItemMeta.setDisplayName(ChatColor.RED + "");
		redGlass.setItemMeta(redGlassItemMeta);

		final ItemMeta craftAnvilItemMeta = craftAnvil.getItemMeta();
		craftAnvilItemMeta.setDisplayName(ChatColor.GREEN + "Crear Pocion");
		craftAnvilItemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Agrega ingredientes a los slots vacios,",
				ChatColor.GRAY + "las botellas de agua que tengas en el",
				ChatColor.GRAY + "inventario se convertiran en pociones!"));
		craftAnvil.setItemMeta(craftAnvilItemMeta);

		final ItemMeta closeItemItemMeta = closeItem.getItemMeta();
		closeItemItemMeta.setDisplayName(ChatColor.RED + "Cerrar");
		closeItem.setItemMeta(closeItemItemMeta);
	}

	public Inventory getBrewingInventory(final BrewingStand brewingStand) {
		final Inventory brewingInventory = brewingInventories.getOrDefault(brewingStand,
				plugin.getServer().createInventory(brewingStand, 54, ChatColor.DARK_GRAY + "Destiladora"));

		if (!brewingInventories.containsKey(brewingStand))
			brewingInventories.put(brewingStand, brewingInventory);

		for (int i = 0; i < 54; i++)
			if (i < 20 || (i > 24 && i < 45))
				brewingInventory.setItem(i, blackGlass);
			else if (i > 44)
				brewingInventory.setItem(i, redGlass);

		brewingInventory.setItem(22, craftAnvil);
		brewingInventory.setItem(49, closeItem);

		return brewingInventory;
	}

	public boolean tryCraftPotion(final Location playerLocation, final Inventory inventory,
			final ItemStack[] waterBottles, final Collection<Material> ingredients) {
		final boolean powered = ingredients.contains(Material.GLOWSTONE_DUST)
				|| ingredients.contains(Material.REDSTONE);
		final boolean fermented = ingredients.contains(Material.FERMENTED_SPIDER_EYE);
		final boolean splash = ingredients.contains(Material.SULPHUR);
		ItemStack potion = null;
		final Material potionMaterial = Material.POTION;

		if (fermented)
			if (ingredients.contains(Material.RABBIT_FOOT) || ingredients.contains(Material.MAGMA_CREAM)
					|| ingredients.contains(Material.SUGAR))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8202);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8266);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16394);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16458);
			else if (ingredients.contains(Material.RAW_FISH) || ingredients.contains(Material.SPECKLED_MELON)
					|| ingredients.contains(Material.SPIDER_EYE))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8204);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8236);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16396);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16428);
			else if (ingredients.contains(Material.GOLDEN_CARROT))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8206);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8270);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16398);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16462);
			else if (!splash)
				if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 8200);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 8264);
			else if (!powered)
				potion = new ItemStack(potionMaterial, 1, (short) 16392);
			else
				potion = new ItemStack(potionMaterial, 1, (short) 16456);
		else if (ingredients.contains(Material.NETHER_STALK))
			if (ingredients.contains(Material.RABBIT_FOOT))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8267);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8235);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16459);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16427);
			else if (ingredients.contains(Material.MAGMA_CREAM))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8195);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8259);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16387);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16451);
			else if (ingredients.contains(Material.SUGAR))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8194);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8226);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16386);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16418);
			else if (ingredients.contains(Material.RAW_FISH))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8205);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8269);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16397);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16461);
			else if (ingredients.contains(Material.SPECKLED_MELON))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8197);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8229);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16389);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16421);
			else if (ingredients.contains(Material.SPIDER_EYE))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8196);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8228);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16388);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16420);
			else if (ingredients.contains(Material.GOLDEN_CARROT))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8198);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8262);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16390);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16454);
			else if (ingredients.contains(Material.GHAST_TEAR))
				if (!splash)
					if (!powered)
						potion = new ItemStack(potionMaterial, 1, (short) 8193);
					else
						potion = new ItemStack(potionMaterial, 1, (short) 8225);
				else if (!powered)
					potion = new ItemStack(potionMaterial, 1, (short) 16385);
				else
					potion = new ItemStack(potionMaterial, 1, (short) 16417);
		/*
		 * else if (ingredients.contains(Material.BLAZE_POWDER)) if (!splash) if
		 * (!powered) potion = new ItemStack(potionMaterial, 1, (short) 8201); else
		 * potion = new ItemStack(potionMaterial, 1, (short) 8233); else if (!powered)
		 * potion = new ItemStack(potionMaterial, 1, (short) 16393); else potion = new
		 * ItemStack(potionMaterial, 1, (short) 16425);
		 */

		if (potion != null) {
			final int inventorySize = inventory.getSize();

			for (final ItemStack waterBottle : waterBottles) {
				if (waterBottle != null) {
					final int amount = waterBottle.getAmount() - 1;

					if (amount > 0) {
						waterBottle.setAmount(amount);
					} else {
						for (int index = 0; index < inventorySize; index++) {
							final ItemStack itemStack = inventory.getItem(index);

							if (itemStack != null && itemStack.isSimilar(waterBottle)) {
								inventory.setItem(index, null);
								break;
							}
						}
					}

					if (inventory.firstEmpty() != -1) {
						inventory.addItem(potion);
					} else {
						playerLocation.getWorld().dropItem(playerLocation, potion);

					}
				}
			}
		}

		return potion != null;
	}

	public HashMap<BrewingStand, Inventory> getBrewingInventories() {
		return brewingInventories;
	}

	public ItemStack getBlackGlass() {
		return blackGlass;
	}

	public ItemStack getRedGlass() {
		return redGlass;
	}

	public ItemStack getCraftAnvil() {
		return craftAnvil;
	}

	public ItemStack getCloseItem() {
		return closeItem;
	}
}
