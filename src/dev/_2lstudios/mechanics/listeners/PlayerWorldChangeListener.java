package dev._2lstudios.mechanics.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class PlayerWorldChangeListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerWorldChange(final PlayerChangedWorldEvent event) {
		if (VersionUtil.isOneDotNine()) {
			final AttributeInstance attributeInstance = event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);

			if (attributeInstance.getBaseValue() != 50)
				attributeInstance.setBaseValue(50);
		}
	}
}
