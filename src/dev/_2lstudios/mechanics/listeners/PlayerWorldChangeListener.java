package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerWorldChangeListener implements Listener {
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
    if (VersionUtil.isOneDotNine()) {
      AttributeInstance attributeInstance = event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);

      if (attributeInstance.getBaseValue() != 512.0D)
        attributeInstance.setBaseValue(512.0D);
    }
  }
}
