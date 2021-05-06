package dev._2lstudios.mechanics.managers;

import dev._2lstudios.mechanics.brewing.BrewingManager;
import dev._2lstudios.mechanics.player.PlayerManager;
import dev._2lstudios.mechanics.utils.ConfigurationUtil;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class GameMechanicsManager {
  private final BrewingManager brewingManager;
  private final EnchantingManager enchantingManager;
  private final MaterialManager materialManager;
  private final RegenerationManager regenerationManager;
  private final SoundManager soundManager;
  private final PlayerManager playerManager;
  private final BlockManager blockManager;

  public GameMechanicsManager(Plugin plugin, ConfigurationUtil configurationUtil) {
    configurationUtil.createConfiguration("%datafolder%/config.yml");

    YamlConfiguration yamlConfiguration = configurationUtil.getConfiguration("%datafolder%/config.yml");

    this.brewingManager = new BrewingManager(plugin);
    this.enchantingManager = new EnchantingManager((Configuration) yamlConfiguration);
    this.materialManager = new MaterialManager();
    this.regenerationManager = new RegenerationManager();
    this.soundManager = new SoundManager();
    this.playerManager = new PlayerManager();
    this.blockManager = new BlockManager();
  }

  public BrewingManager getBrewingManager() {
    return this.brewingManager;
  }

  public EnchantingManager getEnchantingManager() {
    return this.enchantingManager;
  }

  public MaterialManager getMaterialManager() {
    return this.materialManager;
  }

  public RegenerationManager getRegenerationManager() {
    return this.regenerationManager;
  }

  public SoundManager getSoundManager() {
    return this.soundManager;
  }

  public PlayerManager getPlayerManager() {
    return this.playerManager;
  }

  public BlockManager getBlockManager() {
    return this.blockManager;
  }
}
