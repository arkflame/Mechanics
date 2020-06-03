package dev._2lstudios.mechanics.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class PlayerMoveListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerMove(final PlayerMoveEvent event) {
		if (VersionUtil.isOneDotNine()) {
			final Player player = event.getPlayer();
			final ItemStack chestplate = player.getInventory().getChestplate();

			if (chestplate != null && chestplate.getType() == Material.getMaterial("ELYTRA")) {
				player.sendMessage(ChatColor.RED + "No se pueden usar elytras en este servidor!");
				event.setCancelled(true);
			}
		}
	}
}
