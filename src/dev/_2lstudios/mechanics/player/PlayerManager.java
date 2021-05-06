package dev._2lstudios.mechanics.player;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class PlayerManager {
  private Map<Player, GameMechanicsPlayer> gameMechanicsPlayerMap = new HashMap<>();

  public void add(Player player) {
    this.gameMechanicsPlayerMap.put(player, new GameMechanicsPlayer(player));
  }

  public GameMechanicsPlayer get(Player player) {
    return this.gameMechanicsPlayerMap.getOrDefault(player, null);
  }

  public void remove(Player player) {
    this.gameMechanicsPlayerMap.remove(player);
  }
}
