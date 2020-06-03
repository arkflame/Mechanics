package dev._2lstudios.mechanics.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.RegenerationManager;
import dev._2lstudios.mechanics.player.PlayerManager;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class PlayerQuitListener implements Listener {
	private final RegenerationManager regenerationManager;
	private final PlayerManager playerManager;

	public PlayerQuitListener(final GameMechanicsManager gameMechanicsManager) {
		this.regenerationManager = gameMechanicsManager.getRegenerationManager();
		this.playerManager = gameMechanicsManager.getPlayerManager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();

		playerManager.remove(player.getUniqueId());
		regenerationManager.getHealTimes().remove(event.getPlayer());

		if (VersionUtil.isOneDotNine()) {
			final AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);

			if (attribute.getBaseValue() != 4) {
				attribute.setBaseValue(4);
				player.saveData();
			}
		}
	}
}
