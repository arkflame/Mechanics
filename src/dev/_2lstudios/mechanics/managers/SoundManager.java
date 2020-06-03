package dev._2lstudios.mechanics.managers;

import org.bukkit.Sound;

import java.util.Collection;
import java.util.HashSet;

public class SoundManager {
	private final Collection<Sound> sounds = new HashSet<>();

	public SoundManager() {
		addSound("BLOCK_BREWING_STAND_BREW");
		addSound("ENTITY_PLAYER_ATTACK_SWEEP");
		addSound("ENTITY_PLAYER_ATTACK_STRONG");
		addSound("ENTITY_PLAYER_ATTACK_KNOCKBACK");
		addSound("ENTITY_PLAYER_ATTACK_CRIT");
		addSound("ENTITY_PLAYER_ATTACK_WEAK");
		addSound("ENTITY_PLAYER_ATTACK_NODAMAGE");
		addSound("ITEM_ARMOR_EQUIP_DIAMOND");
		addSound("ITEM_ARMOR_EQUIP_IRON");
		addSound("ITEM_ARMOR_EQUIP_CHAIN");
		addSound("ITEM_ARMOR_EQUIP_CHAIN");
		addSound("ITEM_ARMOR_EQUIP_GOLD");
		addSound("ITEM_ARMOR_EQUIP_LEATHER");
		addSound("ITEM_ARMOR_EQUIP_GENERIC");
	}

	private void addSound(final String soundString) {
		final Sound[] sounds = Sound.values();

		for (final Sound sound : sounds)
			if (sound.toString().equals(soundString)) {
				this.sounds.add(Sound.valueOf(soundString));
				break;
			}
	}

	public Collection<Sound> getSounds() {
		return sounds;
	}
}
