package dev._2lstudios.mechanics.managers;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockManager {
  private final Map<Block, Player> breakersByBlock = new HashMap<>();

  public void removeBreaker(Block block) {
    this.breakersByBlock.remove(block);
  }

  public void addBreaker(Block block, Player player) {
    this.breakersByBlock.put(block, player);
  }

  public Player getBreaker(Block block) {
    return this.breakersByBlock.getOrDefault(block, null);
  }

  public void clear() {
    this.breakersByBlock.clear();
  }
}
