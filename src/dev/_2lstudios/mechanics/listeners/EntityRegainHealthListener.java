package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.RegenerationManager;

import java.util.Map;

public class EntityRegainHealthListener implements Listener {
	final private RegenerationManager regenerationManager;

	public EntityRegainHealthListener(final GameMechanicsManager gameMechanicsManager) {
		this.regenerationManager = gameMechanicsManager.getRegenerationManager();
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityRegainHealth(final EntityRegainHealthEvent event) {
		if (event.getEntityType() == EntityType.PLAYER
				&& event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
			final Player player = (Player) event.getEntity();

			if (player.isOnline()) {
				final Map<Player, Long> healTimes = regenerationManager.getHealTimes();
				final long currentTime = System.currentTimeMillis();

				if (!(currentTime - healTimes.getOrDefault(player, (long) 0) >= 3000))
					event.setCancelled(true);
				else {
					final double maxHealth = player.getMaxHealth();
					final double health = player.getHealth();

					if (health < maxHealth - 1) {
						player.setHealth(health + 1);
						player.setExhaustion(0.5f);
					} else
						player.setHealth(health + maxHealth - health);

					healTimes.put(player, currentTime);

					event.setCancelled(true);
				}
			}
		}
	}
}
