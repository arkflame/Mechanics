package dev._2lstudios.mechanics.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import java.util.HashSet;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockPlaceAdapter extends PacketAdapter {
  public BlockPlaceAdapter(Plugin plugin) {
    super(plugin, ListenerPriority.LOWEST, new PacketType[] { PacketType.Play.Client.BLOCK_PLACE });
    this.plugin = plugin;
  }

  public void onPacketReceiving(PacketEvent event) {
    if (!event.isCancelled()) {
      PacketContainer packet = event.getPacket();

      if (packet != null) {
        StructureModifier<ItemStack> itemModifier = packet.getItemModifier();

        if (itemModifier != null && itemModifier.size() > 0) {
          ItemStack itemStack = (ItemStack) itemModifier.read(0);

          if (itemStack != null && itemStack.getType().toString().endsWith("SWORD")) {
            StructureModifier<Integer> integers = packet.getIntegers();

            if (integers.size() > 0 && ((Integer) integers.read(0)).intValue() == 255) {
              Server server = this.plugin.getServer();
              Player player = event.getPlayer();

              server.getScheduler().runTask(this.plugin,
                  () -> server.getPluginManager().callEvent((Event) new PlayerInteractEvent(player,
                      Action.RIGHT_CLICK_AIR, itemStack, player.getTargetBlock(new HashSet<>(), 10), BlockFace.UP)));

              event.setCancelled(true);
            }
          }
        }
      }
    }
  }
}
