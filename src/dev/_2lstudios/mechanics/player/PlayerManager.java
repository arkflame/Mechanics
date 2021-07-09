package dev._2lstudios.mechanics.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerManager {
  private Map<UUID, GameMechanicsPlayer> gameMechanicsPlayerMap = new HashMap<>();

  public void add(Player player) {
    this.gameMechanicsPlayerMap.put(player.getUniqueId(), new GameMechanicsPlayer(player));
  }

  public GameMechanicsPlayer get(Player player) {
    return this.gameMechanicsPlayerMap.getOrDefault(player.getUniqueId(), null);
  }

  public void remove(Player player) {
    this.gameMechanicsPlayerMap.remove(player.getUniqueId());
  }
}
