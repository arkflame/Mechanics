package dev._2lstudios.mechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHandItemsListener implements Listener {
	@EventHandler(ignoreCancelled = true)
	public void onPlayerSwapHands(final PlayerSwapHandItemsEvent event) {
		event.setCancelled(true);
	}
}
