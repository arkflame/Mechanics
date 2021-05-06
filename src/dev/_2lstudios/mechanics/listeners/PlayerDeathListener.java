package dev._2lstudios.mechanics.listeners;

import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public class PlayerDeathListener implements Listener {
  private final Plugin plugin;

  public PlayerDeathListener(Plugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPlayerDeath(PlayerDeathEvent event) {
    Server server = this.plugin.getServer();
    Player player = event.getEntity();
    PlayerInventory playerInventory = player.getInventory();
    World playerWorld = player.getWorld();
    Location playerLocation = player.getLocation();
    Location spawnLocation = ((World) server.getWorlds().get(0)).getSpawnLocation();

    if (player.getFallDistance() > 0.0F) {
      player.setFallDistance(0.0F);
    }

    if (player.getVehicle() != null) {
      player.leaveVehicle();
    }

    if (player.getHealth() < 20.0D) {
      player.setHealth(20.0D);
    }

    if (player.getFoodLevel() < 20) {
      player.setFoodLevel(20);
    }

    if (player.getSaturation() < 4.0F) {
      player.setSaturation(4.0F);
    }

    if (player.getExp() > 0.0F) {
      player.setExp(0.0F);
    }

    if (player.getLevel() > 0) {
      player.setLevel(0);
    }

    for (PotionEffect potionEffect : player.getActivePotionEffects()) {
      player.removePotionEffect(potionEffect.getType());
    }

    if (!event.getKeepLevel()) {
      int expToDrop = getPlayerExp(player);

      if (expToDrop > 0) {
        ExperienceOrb experienceOrb = (ExperienceOrb) playerWorld.spawn(playerLocation, ExperienceOrb.class);

        experienceOrb.setExperience(expToDrop);
      }
    }

    if (!event.getKeepInventory()) {
      Collection<ItemStack> drops = event.getDrops();

      for (ItemStack itemStack : drops) {
        if (itemStack != null && itemStack.getType() != Material.AIR) {
          playerWorld.dropItem(playerLocation, itemStack);
        }
      }

      drops.clear();
      playerInventory.clear();
      playerInventory.setContents(new ItemStack[0]);
      playerInventory.setHelmet(new ItemStack(Material.AIR));
      playerInventory.setChestplate(new ItemStack(Material.AIR));
      playerInventory.setLeggings(new ItemStack(Material.AIR));
      playerInventory.setBoots(new ItemStack(Material.AIR));
      event.setKeepInventory(true);
    }

    server.getPluginManager().callEvent((Event) new PlayerRespawnEvent(player, spawnLocation, false));
  }

  private static int getExpToLevelUp(int level) {
    if (level <= 15)
      return 2 * level + 7;
    if (level <= 30) {
      return 5 * level - 38;
    }
    return 9 * level - 158;
  }

  private static int getExpAtLevel(int level) {
    if (level <= 16)
      return (int) (Math.pow(level, 2.0D) + (6 * level));
    if (level <= 31) {
      return (int) (2.5D * Math.pow(level, 2.0D) - 40.5D * level + 360.0D);
    }
    return (int) (4.5D * Math.pow(level, 2.0D) - 162.5D * level + 2220.0D);
  }

  private static int getPlayerExp(Player player) {
    int exp = 0;
    int level = player.getLevel();

    exp += getExpAtLevel(level);

    exp += Math.round(getExpToLevelUp(level) * player.getExp());

    return exp;
  }
}
