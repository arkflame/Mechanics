package dev._2lstudios.mechanics.adapters;

import java.util.Collections;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

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

class BlockPlaceAdapter extends PacketAdapter {
  BlockPlaceAdapter(final Plugin plugin) {
    super(plugin, ListenerPriority.LOWEST, new PacketType[] { PacketType.Play.Client.BLOCK_PLACE });
    this.plugin = plugin;
  }

  public void onPacketReceiving(final PacketEvent event) {
    if (!event.isCancelled()) {
      final PacketContainer packet = event.getPacket();

      if (packet != null) {
        final StructureModifier<ItemStack> itemModifier = packet.getItemModifier();

        if (itemModifier != null && itemModifier.size() > 0) {
          final ItemStack itemStack = (ItemStack) itemModifier.read(0);

          if (itemStack != null && itemStack.getType().toString().endsWith("SWORD")) {
            final Server server = plugin.getServer();
            final BukkitScheduler scheduler = server.getScheduler();
            final PluginManager pluginManager = server.getPluginManager();
            final Player player = event.getPlayer();
            final Block targetBlock = player.getTargetBlock(Collections.emptySet(), 5);

            scheduler.runTask(plugin, () -> pluginManager.callEvent(
                new PlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, itemStack, targetBlock, BlockFace.UP)));
            event.setCancelled(true);
          }
        }
      }
    }
  }
}
