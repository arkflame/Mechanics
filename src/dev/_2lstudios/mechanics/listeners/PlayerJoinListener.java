package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.player.PlayerManager;
import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {
  private final EnchantingManager enchantingManager;
  private final PlayerManager playerManager;

  public PlayerJoinListener(GameMechanicsManager gameMechanicsManager) {
    this.enchantingManager = gameMechanicsManager.getEnchantingManager();
    this.playerManager = gameMechanicsManager.getPlayerManager();
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    if (player.getMaximumNoDamageTicks() != 20) {
      player.setMaximumNoDamageTicks(20);
    }

    this.playerManager.add(player);

    if (VersionUtil.isOneDotNine()) {
      AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);

      if (attributeInstance.getBaseValue() != 512.0D)
        attributeInstance.setBaseValue(512.0D);
    }
    byte b;
    int i;
    ItemStack[] arrayOfItemStack;
    for (i = (arrayOfItemStack = player.getInventory().getContents()).length, b = 0; b < i;) {
      ItemStack itemStack = arrayOfItemStack[b];
      this.enchantingManager.fixEnchants(itemStack);
      b++;
    }

  }
}
