package dev._2lstudios.mechanics.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import dev._2lstudios.mechanics.utils.MinecraftUtil;

class BlockPlaceAdapter extends PacketAdapter {
  private final Plugin plugin;
  private final BukkitScheduler scheduler;
  private final PluginManager pluginManager;

  BlockPlaceAdapter(final Plugin plugin) {
    super(plugin, ListenerPriority.LOWEST, new PacketType[] { PacketType.Play.Client.BLOCK_PLACE });

    final Server server = plugin.getServer();

    this.plugin = plugin;
    this.scheduler = server.getScheduler();
    this.pluginManager = server.getPluginManager();
  }

  public void onPacketReceiving(final PacketEvent event) {
    if (!event.isCancelled()) {
      final PacketContainer packet = event.getPacket();

      if (packet != null) {
        final StructureModifier<ItemStack> itemModifier = packet.getItemModifier();

        if (itemModifier != null && itemModifier.size() > 0) {
          final ItemStack itemStack = itemModifier.read(0);

          if (itemStack != null && itemStack.getType().name().endsWith("SWORD")) {
            final StructureModifier<BlockPosition> positionModifier = packet.getBlockPositionModifier();

            if (positionModifier != null && positionModifier.size() > 0) {
              final BlockPosition blockPosition = positionModifier.read(0);
              final Player player = event.getPlayer();
              final Block targetBlock = MinecraftUtil.getBlockAt(player.getWorld(), blockPosition);
              final Material targetBlockType = targetBlock.getType();
              final boolean isAir = targetBlock == null || targetBlockType == Material.AIR;

              if (isAir || !MinecraftUtil.isInteractable(targetBlockType)) {
                final Action actionType = isAir ? Action.RIGHT_CLICK_AIR : Action.RIGHT_CLICK_BLOCK;

                scheduler.runTask(plugin, () -> pluginManager
                    .callEvent(new PlayerInteractEvent(player, actionType, itemStack, targetBlock, BlockFace.UP)));
                event.setCancelled(true);
              }
            }
          }
        }
      }
    }
  }
}
