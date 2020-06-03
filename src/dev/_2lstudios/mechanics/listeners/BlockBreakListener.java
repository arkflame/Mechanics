package dev._2lstudios.mechanics.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.MaterialManager;
import dev._2lstudios.mechanics.player.GameMechanicsPlayer;
import dev._2lstudios.mechanics.player.PlayerManager;
import dev._2lstudios.mechanics.utils.VersionUtil;

import java.util.Map;

public class BlockBreakListener implements Listener {
	private final Plugin plugin;
	private final MaterialManager materialManager;
	private final PlayerManager playerManager;

	public BlockBreakListener(final Plugin plugin, final GameMechanicsManager gameMechanicsManager) {
		this.plugin = plugin;
		this.materialManager = gameMechanicsManager.getMaterialManager();
		this.playerManager = gameMechanicsManager.getPlayerManager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		final Player player = event.getPlayer();
		final GameMechanicsPlayer gameMechanicsPlayer = playerManager.get(player.getUniqueId());
		final PlayerInventory playerInventory = player.getInventory();
		final Block block = event.getBlock();

		if (block != null) {
			final Material material = block.getType();
			final Material material1 = materialManager.getMaterial(material);
			final String materialName = material.name();
			final ItemStack heldItem = playerInventory.getItem(playerInventory.getHeldItemSlot());
			final Location location = block.getLocation();

			if (material1 != null)
				block.setType(material1);

			if (materialName.endsWith("ORE") || materialName.endsWith("STONE")) {
				if (VersionUtil.isOneDotNine())
					player.playSound(location, Sound.valueOf("ENTITY_ARROW_HIT_PLAYER"), 0.1f,
							(float) (1.1 + Math.random() / 2));
				else
					player.playSound(location, Sound.valueOf("SUCCESSFUL_HIT"), 0.1f,
							(float) (1.1 + Math.random() / 2));
			}

			plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
				for (final Entity entity : block.getChunk().getEntities()) {
					final Location entityLocation = entity.getLocation();

					if (entity.getType() == EntityType.DROPPED_ITEM && location.distance(entityLocation) < 1.5) {
						final Item item = (Item) entity;
						final ItemStack itemStack = item.getItemStack();

						if (!item.isDead() && itemStack != null && itemStack.getAmount() != 0) {
							final Material type = itemStack.getType();

							if (type == Material.GOLD_ORE)
								itemStack.setType(Material.GOLD_INGOT);
							else if (type == Material.IRON_ORE)
								itemStack.setType(Material.IRON_INGOT);

							if (gameMechanicsPlayer.isMagnet() && playerInventory.firstEmpty() != -1
									&& (!gameMechanicsPlayer.isCobblestone() || type != Material.COBBLESTONE)
									&& (!gameMechanicsPlayer.isDirt() || itemStack.getType() != Material.COBBLESTONE)) {
								playerInventory.addItem(itemStack);
								item.remove();
							}
						}
					}
				}
			}, 1L);

			final int expToDrop = event.getExpToDrop();

			if (heldItem != null && expToDrop > 0) {
				final Map<Enchantment, Integer> enchantments = heldItem.getEnchantments();

				if (enchantments != null && enchantments.containsKey(Enchantment.LOOT_BONUS_BLOCKS))
					event.setExpToDrop(expToDrop * (enchantments.get(Enchantment.LOOT_BONUS_BLOCKS) + 1));
			}
		}
	}
}
