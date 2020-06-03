package dev._2lstudios.mechanics.adapters.initializers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.Plugin;
import dev._2lstudios.mechanics.adapters.BlockPlaceAdapter;
import dev._2lstudios.mechanics.adapters.NamedSoundEffectAdapter;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;

public class AdapterInitializer {
	public AdapterInitializer(final Plugin plugin, final GameMechanicsManager gameMechanicsManager) {
		final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

		protocolManager.removePacketListeners(plugin);
		protocolManager.addPacketListener(new BlockPlaceAdapter(plugin));
		protocolManager.addPacketListener(new NamedSoundEffectAdapter(plugin, gameMechanicsManager));
	}
}
