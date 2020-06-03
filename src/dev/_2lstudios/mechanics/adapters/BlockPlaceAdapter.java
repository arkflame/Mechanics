package dev._2lstudios.mechanics.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;

public class BlockPlaceAdapter extends PacketAdapter {

	public BlockPlaceAdapter(final Plugin plugin) {
		super(plugin, ListenerPriority.LOWEST, PacketType.Play.Client.BLOCK_PLACE);
		this.plugin = plugin;
	}

	@Override
	public void onPacketReceiving(final PacketEvent event) {
		if (!event.isCancelled()) {
			final PacketContainer packet = event.getPacket();

			if (packet != null) {
				final StructureModifier<ItemStack> itemModifier = packet.getItemModifier();

				if (itemModifier != null && itemModifier.size() > 0) {
					final ItemStack itemStack = itemModifier.read(0);

					if (itemStack != null && itemStack.getType().toString().endsWith("SWORD")) {
						final StructureModifier<Integer> integers = packet.getIntegers();

						if (integers.size() > 0 && integers.read(0) == 255) {
							final Server server = plugin.getServer();
							final Player player = event.getPlayer();

							server.getScheduler().runTask(plugin, () -> {
								server.getPluginManager()
										.callEvent(new PlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, itemStack,
												player.getTargetBlock(new HashSet<>(), 100), BlockFace.UP));
							});

							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
