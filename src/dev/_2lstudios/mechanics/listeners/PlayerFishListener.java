package dev._2lstudios.mechanics.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class PlayerFishListener implements Listener {
	@EventHandler(ignoreCancelled = true)
	private void onPlayerFish(final PlayerFishEvent event) {
		if (VersionUtil.isOneDotNine()) {
			final PlayerFishEvent.State state = event.getState();
			final Entity hook = event.getHook();

			if (state == PlayerFishEvent.State.CAUGHT_ENTITY) {
				final PlayerInventory inventory = event.getPlayer().getInventory();
				final ItemStack itemInHand = inventory.getItem(inventory.getHeldItemSlot());

				if (itemInHand != null)
					itemInHand.setDurability((short) (itemInHand.getDurability() + 1));

				hook.remove();
				event.setCancelled(true);
			} else
				hook.setVelocity(hook.getVelocity().multiply(1.5));
		}
	}
}