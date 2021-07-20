package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.managers.BlockManager;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.player.GameMechanicsPlayer;
import dev._2lstudios.mechanics.player.PlayerManager;
import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemSpawnListener implements Listener {
  private final BlockManager blockManager;
  private final PlayerManager playerManager;

  public ItemSpawnListener(final GameMechanicsManager gameMechanicsManager) {
    this.blockManager = gameMechanicsManager.getBlockManager();
    this.playerManager = gameMechanicsManager.getPlayerManager();
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onItemSpawn(final ItemSpawnEvent event) {
    final Item item = event.getEntity();

    if (item == null) {
      return;
    }

    final ItemStack itemStack = item.getItemStack();

    if (itemStack == null || itemStack.getAmount() <= 0) {
      return;
    }

    final Material type = itemStack.getType();

    if (type == Material.GOLD_ORE) {
      itemStack.setType(Material.GOLD_INGOT);
    } else if (type == Material.IRON_ORE) {
      itemStack.setType(Material.IRON_INGOT);
    } else if (type == Material.COBBLESTONE && Math.random() <= 0.5D) {
      itemStack.setAmount(itemStack.getAmount() + 1);
    }

    final Block block = event.getLocation().getBlock();

    if (block == null) {
      return;
    }

    final Player player = this.blockManager.getBreaker(block);

    if (player == null) {
      return;
    }

    final GameMechanicsPlayer gameMechanicsPlayer = this.playerManager.get(player);

    if (gameMechanicsPlayer != null && gameMechanicsPlayer.isMagnet()) {
      if (player == null || !player.isOnline() || player.isDead()) {
        return;
      }

      final PlayerInventory playerInventory = player.getInventory();

      if (playerInventory.firstEmpty() != -1 && (!gameMechanicsPlayer.isCobblestone() || type != Material.COBBLESTONE)
          && (!gameMechanicsPlayer.isDirt() || itemStack.getType() != Material.DIRT)) {
        final float pitch = (float) Math.max(1.0D, Math.random() * 3.0D);

        if (VersionUtil.isOneDotNine()) {
          player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.25F, pitch);
        } else {
          player.playSound(player.getLocation(), Sound.valueOf("ITEM_PICKUP"), 0.25F, pitch);
        }

        playerInventory.addItem(new ItemStack[] { itemStack });
        event.setCancelled(true);
      }
    }
  }
}
