package dev._2lstudios.mechanics.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.SoundManager;
import dev._2lstudios.mechanics.utils.VersionUtil;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;

public class NamedSoundEffectAdapter extends PacketAdapter {
  private final SoundManager soundManager;

  public NamedSoundEffectAdapter(Plugin plugin, GameMechanicsManager gameMechanicsManager) {
    super(plugin, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Server.NAMED_SOUND_EFFECT });
    this.soundManager = gameMechanicsManager.getSoundManager();
  }

  public void onPacketSending(PacketEvent event) {
    if (!event.isCancelled() && VersionUtil.isOneDotNine()) {
      Sound sound = (Sound) event.getPacket().getSoundEffects().read(0);

      if (this.soundManager.getSounds().contains(sound))
        event.setCancelled(true);
    }
  }
}
