package dev._2lstudios.mechanics.managers;

import org.bukkit.DyeColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class EnchantingManager {
	private final ItemStack lapisLazuli;
	private final boolean limitEnchants;
	private Collection<Enchantment> enchants = new HashSet<>();

	public EnchantingManager(final Configuration configuration) {
		final Collection<String> enchants = new HashSet<>();
		final Enchantment[] allEnchantments = Enchantment.values();
		final Dye dye = new Dye();

		dye.setColor(DyeColor.BLUE);

		this.lapisLazuli = dye.toItemStack(64);
		this.limitEnchants = configuration.getBoolean("limit_enchants");
		enchants.add("BINDING_CURSE");
		enchants.add("SWEEPING_EDGE");
		enchants.add("VANISHING_CURSE");
		enchants.add("FROST_WALKER");
		enchants.add("KNOCKBACK");
		enchants.add("ARROW_KNOCKBACK");
		enchants.add("PUNCH");
		enchants.add("MENDING");

		for (final String enchantment : enchants) {
			for (final Enchantment enchantment1 : allEnchantments) {
				if (enchantment.equalsIgnoreCase(enchantment1.getName()))
					this.enchants.add(enchantment1);
			}
		}
	}

	public ItemStack getLapisLazuli() {
		return lapisLazuli;
	}

	public ItemStack fixEnchants(final ItemStack itemStack) {
		if (itemStack != null) {
			for (final Enchantment enchantment : enchants) {
				if (itemStack.containsEnchantment(enchantment))
					itemStack.removeEnchantment(enchantment);
			}

			final Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();

			if (limitEnchants && enchantments != null && !enchantments.isEmpty()) {
				boolean damageLimit = false, protectionLimit = false;

				for (final Enchantment enchantment : new HashSet<>(enchantments.keySet())) {
					if (!damageLimit && enchantment.getName().equals(Enchantment.DAMAGE_ALL.getName())
							&& enchantments.get(enchantment) > 2) {
						itemStack.addEnchantment(Enchantment.DAMAGE_ALL, 2);
						damageLimit = true;
					} else if (!protectionLimit
							&& enchantment.getName().equals(Enchantment.PROTECTION_ENVIRONMENTAL.getName())
							&& enchantments.get(enchantment) > 2) {
						itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
						protectionLimit = true;
					}
				}
			}
		}

		return itemStack;
	}

	public void fixEnchants(final Map<Enchantment, Integer> enchantments) {
		if (limitEnchants) {
			boolean damageLimit = false, protectionLimit = false;

			for (final Enchantment enchantment : enchants) {
				enchantments.remove(enchantment);
			}

			for (final Enchantment enchantment : new HashSet<>(enchantments.keySet())) {
				if (!damageLimit && enchantment.getName().equals(Enchantment.DAMAGE_ALL.getName())
						&& enchantments.get(enchantment) > 2) {
					enchantments.put(Enchantment.DAMAGE_ALL, 2);
					damageLimit = true;
				} else if (!protectionLimit
						&& enchantment.getName().equals(Enchantment.PROTECTION_ENVIRONMENTAL.getName())
						&& enchantments.get(enchantment) > 2) {
					enchantments.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
					protectionLimit = true;
				}
			}
		}
	}
}
