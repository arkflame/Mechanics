package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class PlayerItemHeldListener implements Listener {
  @EventHandler(ignoreCancelled = true)
  public void onPlayerItemHeld(PlayerItemHeldEvent event) {
    Player player = event.getPlayer();

    if (!VersionUtil.isOneDotNine()) {
      player.playSound(player.getLocation(), Sound.valueOf("CLICK"), 0.1F, 8.0F);
    } else {
      player.playSound(player.getLocation(), Sound.valueOf("UI_BUTTON_CLICK"), 0.1F, 8.0F);
    }
  }
}
