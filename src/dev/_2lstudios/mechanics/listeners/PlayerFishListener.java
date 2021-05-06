package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.entity.FishHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerFishListener implements Listener {
  @EventHandler(ignoreCancelled = true)
  private void onPlayerFish(PlayerFishEvent event) {
    if (VersionUtil.isOneDotNine()) {
      PlayerFishEvent.State state = event.getState();
      FishHook fishHook = event.getHook();

      if (state == PlayerFishEvent.State.CAUGHT_ENTITY) {
        PlayerInventory inventory = event.getPlayer().getInventory();
        ItemStack itemInHand = inventory.getItem(inventory.getHeldItemSlot());

        if (itemInHand != null) {
          itemInHand.setDurability((short) (itemInHand.getDurability() + 1));
        }
        fishHook.remove();
        event.setCancelled(true);
      } else {
        fishHook.setVelocity(fishHook.getVelocity().multiply(1.5D));
      }
    }
  }
}
