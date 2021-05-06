package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.EnchantingManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.player.GameMechanicsPlayer;
import dev._2lstudios.mechanics.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class EntityPickupItemListener implements Listener {
  private final PlayerManager playerManager;
  private final EnchantingManager enchantingManager;

  public EntityPickupItemListener(GameMechanicsManager gameMechanicsManager) {
    this.playerManager = gameMechanicsManager.getPlayerManager();
    this.enchantingManager = gameMechanicsManager.getEnchantingManager();
  }

  @EventHandler(ignoreCancelled = true)
  public void onEntityPickup(EntityPickupItemEvent event) {
    LivingEntity entity = event.getEntity();

    if (entity instanceof Player) {
      Item item = event.getItem();
      ItemStack itemStack = this.enchantingManager.fixEnchants(item.getItemStack());
      Material material = itemStack.getType();
      int amount = itemStack.getAmount();

      item.setItemStack(itemStack);

      if (material == Material.COBBLESTONE) {
        Player player = (Player) entity;
        GameMechanicsPlayer gameMechanicsPlayer = this.playerManager.get(player);

        if (gameMechanicsPlayer.isCobblestone()) {
          event.setCancelled(true);
        }
      } else if (material == Material.DIRT) {
        Player player = (Player) entity;
        GameMechanicsPlayer gameMechanicsPlayer = this.playerManager.get(player);

        if (gameMechanicsPlayer.isDirt()) {
          event.setCancelled(true);
        }
      } else if (amount > itemStack.getMaxStackSize()) {
        item.remove();
      }
    }
  }
}
