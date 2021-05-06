package dev._2lstudios.mechanics.managers;

import java.util.Collection;
import java.util.HashSet;
import org.bukkit.Sound;

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

  private void addSound(String soundString) {
    Sound[] sounds = Sound.values();
    byte b;
    int i;
    Sound[] arrayOfSound1;
    for (i = (arrayOfSound1 = sounds).length, b = 0; b < i;) {
      Sound sound = arrayOfSound1[b];
      if (sound.toString().equals(soundString)) {
        this.sounds.add(Sound.valueOf(soundString));
        break;
      }
      b++;
    }

  }

  public Collection<Sound> getSounds() {
    return this.sounds;
  }
}
