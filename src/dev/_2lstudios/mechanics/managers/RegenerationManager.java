package dev._2lstudios.mechanics.managers;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class RegenerationManager {
  private final Map<Player, Long> healTimes = new HashMap<>();

  public Map<Player, Long> getHealTimes() {
    return this.healTimes;
  }
}
