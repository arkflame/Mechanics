package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.RegenerationManager;
import dev._2lstudios.mechanics.player.PlayerManager;
import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
  private final RegenerationManager regenerationManager;
  private final PlayerManager playerManager;

  public PlayerQuitListener(GameMechanicsManager gameMechanicsManager) {
    this.regenerationManager = gameMechanicsManager.getRegenerationManager();
    this.playerManager = gameMechanicsManager.getPlayerManager();
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    if (player.getMaximumNoDamageTicks() != 20) {
      player.setMaximumNoDamageTicks(20);
    }

    this.playerManager.remove(player);
    this.regenerationManager.getHealTimes().remove(event.getPlayer());

    if (VersionUtil.isOneDotNine()) {
      AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);

      if (attribute.getBaseValue() != attribute.getDefaultValue()) {
        attribute.setBaseValue(attribute.getDefaultValue());
        player.saveData();
      }
    }
  }
}
