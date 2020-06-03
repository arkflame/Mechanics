package dev._2lstudios.mechanics.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.managers.SoundManager;
import dev._2lstudios.mechanics.utils.VersionUtil;

public class NamedSoundEffectAdapter extends PacketAdapter {
	final private SoundManager soundManager;

	public NamedSoundEffectAdapter(final Plugin plugin, final GameMechanicsManager gameMechanicsManager) {
		super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT);
		this.soundManager = gameMechanicsManager.getSoundManager();
	}

	@Override
	public void onPacketSending(final PacketEvent event) {
		if (!event.isCancelled() && VersionUtil.isOneDotNine()) {
			final Sound sound = event.getPacket().getSoundEffects().read(0);

			if (soundManager.getSounds().contains(sound))
				event.setCancelled(true);
		}
	}
}
