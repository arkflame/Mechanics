package dev._2lstudios.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import dev._2lstudios.mechanics.utils.VersionUtil;

import java.util.Collection;
import java.util.HashSet;

public class PlayerItemConsumeListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onItemConsume(final PlayerItemConsumeEvent event) {
		if (VersionUtil.isOneDotNine()) {
			final ItemStack itemStack = event.getItem();
			final Material material = itemStack.getType();

			if (material == Material.getMaterial("CHORUS_FRUIT")) {
				final Player player = event.getPlayer();
				final PlayerInventory playerInventory = player.getInventory();

				for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
					final PotionEffectType type = potionEffect.getType();
					final int duration = potionEffect.getDuration();
					final int amplifier = potionEffect.getAmplifier();

					if (duration < 100 && amplifier <= 0 && type.equals(PotionEffectType.REGENERATION))
						player.removePotionEffect(type);
					if (duration < 200 && amplifier <= 0 && type.equals(PotionEffectType.ABSORPTION))
						player.removePotionEffect(type);
				}

				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 0));

				final int foodLevel = player.getFoodLevel();

				if (foodLevel < 16)
					player.setFoodLevel(foodLevel + 4);
				else
					player.setFoodLevel(20);

				itemStack.setAmount(itemStack.getAmount() - 1);
				playerInventory.setItem(playerInventory.getHeldItemSlot(), itemStack);

				if (VersionUtil.isOneDotNine())
					player.playSound(player.getLocation(), Sound.valueOf("ENTITY_PLAYER_BURP"), 7f, 1f);

				event.setCancelled(true);
			} else if (material == Material.GOLDEN_APPLE && itemStack.getData().getData() == 1) {
				final Player player = event.getPlayer();
				final PlayerInventory playerInventory = player.getInventory();
				final Collection<PotionEffect> potionEffects = new HashSet<>();
				final int foodLevel = player.getFoodLevel();

				for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
					final PotionEffectType type = potionEffect.getType();
					final int duration = potionEffect.getDuration();
					final int amplifier = potionEffect.getAmplifier();

					if (duration < 600 && amplifier <= 3 && type.equals(PotionEffectType.REGENERATION)) {
						player.removePotionEffect(type);
					} else if (duration < 6000 && amplifier <= 0
							&& (type.equals(PotionEffectType.DAMAGE_RESISTANCE)
									|| type.equals(PotionEffectType.FIRE_RESISTANCE)
									|| type.equals(PotionEffectType.ABSORPTION)))
						player.removePotionEffect(type);
				}

				potionEffects.add(new PotionEffect(PotionEffectType.REGENERATION, 600, 3));
				potionEffects.add(new PotionEffect(PotionEffectType.ABSORPTION, 6000, 0));
				potionEffects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000, 0));
				potionEffects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 0));
				player.addPotionEffects(potionEffects);

				if (foodLevel < 16)
					player.setFoodLevel(foodLevel + 4);
				else
					player.setFoodLevel(20);

				itemStack.setAmount(itemStack.getAmount() - 1);
				playerInventory.setItem(playerInventory.getHeldItemSlot(), itemStack);

				if (VersionUtil.isOneDotNine())
					player.playSound(player.getLocation(), Sound.valueOf("ENTITY_PLAYER_BURP"), 7f, 1f);

				event.setCancelled(true);
			}
		}
	}
}
