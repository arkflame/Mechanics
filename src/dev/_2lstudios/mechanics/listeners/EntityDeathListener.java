package dev._2lstudios.mechanics.listeners;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import dev._2lstudios.mechanics.utils.VersionUtil;

import java.util.Map;

public class EntityDeathListener implements Listener {
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onEntityDeath(final EntityDeathEvent event) {
		final LivingEntity livingEntity = event.getEntity();
		final Player killer = livingEntity.getKiller();

		if (killer != null) {
			final PlayerInventory playerInventory = killer.getInventory();
			final ItemStack itemStack = playerInventory.getItem(playerInventory.getHeldItemSlot());
			final Location location = killer.getLocation();
			final World world = livingEntity.getWorld();

			if (VersionUtil.isOneDotNine())
				killer.playSound(location, Sound.valueOf("ENTITY_ARROW_HIT_PLAYER"), 0.9f,
						(float) (1.5 + Math.random() / 2));
			else
				killer.playSound(location, Sound.valueOf("SUCCESSFUL_HIT"), 0.9f, (float) (1.5 + Math.random() / 2));

			world.playEffect(livingEntity.getLocation().add(0.0D, 0.85D, 0.0D), Effect.STEP_SOUND,
					Material.REDSTONE_BLOCK);

			if (itemStack != null) {
				final Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();

				if (enchantments != null && enchantments.containsKey(Enchantment.LOOT_BONUS_MOBS))
					event.setDroppedExp(event.getDroppedExp() * (enchantments.get(Enchantment.LOOT_BONUS_MOBS) + 1));
			}
		}
	}
}
