package dev._2lstudios.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.player.GameMechanicsPlayer;
import dev._2lstudios.mechanics.player.PlayerManager;

public class EntityPickupItemListener implements Listener {
	private final PlayerManager playerManager;
	private final EnchantingManager enchantingManager;

	public EntityPickupItemListener(final GameMechanicsManager gameMechanicsManager) {
		this.playerManager = gameMechanicsManager.getPlayerManager();
		this.enchantingManager = gameMechanicsManager.getEnchantingManager();
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityPickup(final EntityPickupItemEvent event) {
		final LivingEntity entity = event.getEntity();

		if (entity instanceof Player) {
			final Item item = event.getItem();
			final ItemStack itemStack = enchantingManager.fixEnchants(item.getItemStack());
			final Material material = itemStack.getType();
			final int amount = itemStack.getAmount();

			item.setItemStack(itemStack);

			if (material == Material.COBBLESTONE) {
				final Player player = (Player) entity;
				final GameMechanicsPlayer gameMechanicsPlayer = playerManager.get(player.getUniqueId());

				if (gameMechanicsPlayer.isCobblestone()) {
					event.setCancelled(true);
				}
			} else if (material == Material.DIRT) {
				final Player player = (Player) entity;
				final GameMechanicsPlayer gameMechanicsPlayer = playerManager.get(player.getUniqueId());

				if (gameMechanicsPlayer.isDirt()) {
					event.setCancelled(true);
				}
			} else if (amount > itemStack.getMaxStackSize()) {
				item.remove();
			}
		}
	}
}
