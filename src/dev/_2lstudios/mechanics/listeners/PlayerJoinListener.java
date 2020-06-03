package dev._2lstudios.mechanics.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class PlayerJoinListener implements Listener {
	final private EnchantingManager enchantingManager;

	public PlayerJoinListener(final GameMechanicsManager gameMechanicsManager) {
		this.enchantingManager = gameMechanicsManager.getEnchantingManager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		if (VersionUtil.isOneDotNine()) {
			final AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);

			if (attributeInstance.getBaseValue() != 50) {
				attributeInstance.setBaseValue(50);
			}
		}

		for (final ItemStack itemStack : player.getInventory().getContents()) {
			enchantingManager.fixEnchants(itemStack);
		}
	}
}
