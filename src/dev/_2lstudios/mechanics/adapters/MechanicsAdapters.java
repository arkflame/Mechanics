package dev._2lstudios.mechanics.adapters;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import org.bukkit.plugin.Plugin;

import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class MechanicsAdapters {
  public static void register(final Plugin plugin, final GameMechanicsManager gameMechanicsManager) {
    final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    protocolManager.removePacketListeners(plugin);
    protocolManager.addPacketListener(new BlockPlaceAdapter(plugin));

    if (VersionUtil.isOneDotNine()) {
      protocolManager.addPacketListener(new NamedSoundEffectAdapter(plugin, gameMechanicsManager));
    }
  }
}
