package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerTeleportListener implements Listener {
  private final EnchantingManager enchantingManager;

  public PlayerTeleportListener(GameMechanicsManager gameMechanicsManager) {
    this.enchantingManager = gameMechanicsManager.getEnchantingManager();
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerTeleportLow(PlayerTeleportEvent event) {
    PlayerTeleportEvent.TeleportCause teleportCause = event.getCause();

    if (VersionUtil.isOneDotNine() && teleportCause == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) {
      event.setCancelled(true);
    }
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
  public void onPlayerTeleportHigh(PlayerTeleportEvent event) {
    Player player = event.getPlayer();
    Location from = event.getFrom();
    Location to = event.getTo();
    World fromWorld = from.getWorld();
    World toWorld = to.getWorld();

    player.setFallDistance(1.0F);
    player.setVelocity(new Vector(0, 0, 0));
    byte b;
    int i;
    ItemStack[] arrayOfItemStack;
    for (i = (arrayOfItemStack = player.getInventory().getContents()).length, b = 0; b < i;) {
      ItemStack itemStack = arrayOfItemStack[b];
      this.enchantingManager.fixEnchants(itemStack);
      b++;
    }

    if (fromWorld == toWorld) {
      double distance = from.distance(to);

      if (distance > 5.0D) {
        playTeleportSound(player, to);
      }
    } else {
      playTeleportSound(player, to);
    }
  }

  private void playTeleportSound(Player player, Location to) {
    float randomFloat = (float) (1.0D + Math.random() / 2.0D);

    if (VersionUtil.isOneDotNine()) {
      player.playSound(to, Sound.valueOf("ENTITY_ENDERMEN_TELEPORT"), 1.0F, randomFloat);
    } else {
      player.playSound(to, Sound.valueOf("ENDERMAN_TELEPORT"), 1.0F, randomFloat);
    }
  }
}
