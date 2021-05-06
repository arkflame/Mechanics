package dev._2lstudios.mechanics.player;

import org.bukkit.entity.Player;

public class GameMechanicsPlayer {
  private final Player player;
  private boolean magnet = true, cobblestone = false, dirt = false;

  public GameMechanicsPlayer(Player player) {
    this.player = player;
  }

  public void setMagnet(boolean magnet) {
    this.magnet = magnet;
  }

  public boolean isMagnet() {
    return this.magnet;
  }

  public void setCobblestone(boolean cobblestone) {
    this.cobblestone = cobblestone;
  }

  public boolean isCobblestone() {
    return this.cobblestone;
  }

  public void setDirt(boolean dirt) {
    this.dirt = dirt;
  }

  public boolean isDirt() {
    return this.dirt;
  }

  public Player getPlayer() {
    return this.player;
  }
}
