package dev._2lstudios.mechanics.managers;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;
import dev._2lstudios.mechanics.player.PlayerManager;
import dev._2lstudios.mechanics.utils.ConfigurationUtil;

public class GameMechanicsManager {
	final private BrewingManager brewingManager;
	final private EnchantingManager enchantingManager;
	final private KnockbackManager knockbackManager;
	final private MaterialManager materialManager;
	final private RegenerationManager regenerationManager;
	final private SoundManager soundManager;
	final private PlayerManager playerManager;

	public GameMechanicsManager(final Plugin plugin, final ConfigurationUtil configurationUtil) {
		configurationUtil.createConfiguration("%datafolder%/config.yml");

		final Configuration configuration = configurationUtil.getConfiguration("%datafolder%/config.yml");

		this.brewingManager = new BrewingManager(plugin);
		this.enchantingManager = new EnchantingManager(configuration);
		this.knockbackManager = new KnockbackManager();
		this.materialManager = new MaterialManager();
		this.regenerationManager = new RegenerationManager();
		this.soundManager = new SoundManager();
		this.playerManager = new PlayerManager();
	}

	public BrewingManager getBrewingManager() {
		return brewingManager;
	}

	public EnchantingManager getEnchantingManager() {
		return enchantingManager;
	}

	public KnockbackManager getKnockbackManager() {
		return knockbackManager;
	}

	public MaterialManager getMaterialManager() {
		return materialManager;
	}

	public RegenerationManager getRegenerationManager() {
		return regenerationManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}
}
