package dev._2lstudios.mechanics.adapters.initializers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import dev._2lstudios.mechanics.adapters.BlockPlaceAdapter;
import dev._2lstudios.mechanics.adapters.NamedSoundEffectAdapter;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import org.bukkit.plugin.Plugin;

public class AdapterInitializer {
  public AdapterInitializer(Plugin plugin, GameMechanicsManager gameMechanicsManager) {
    ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    protocolManager.removePacketListeners(plugin);
    protocolManager.addPacketListener((PacketListener) new BlockPlaceAdapter(plugin));
    protocolManager.addPacketListener((PacketListener) new NamedSoundEffectAdapter(plugin, gameMechanicsManager));
  }
}
