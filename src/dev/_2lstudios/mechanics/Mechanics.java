package dev._2lstudios.mechanics;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.mechanics.commands.CobblestoneCMD;
import dev._2lstudios.mechanics.commands.DirtCMD;
import dev._2lstudios.mechanics.commands.MagnetCMD;
import dev._2lstudios.mechanics.listeners.initializers.ListenerInitializer;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.ConfigurationUtil;
import dev._2lstudios.mechanics.adapters.initializers.AdapterInitializer;

public class Mechanics extends JavaPlugin {
	private static GameMechanicsManager gameMechanicsManager;

	public void onEnable() {
		gameMechanicsManager = new GameMechanicsManager(this, new ConfigurationUtil(this));

		final Server server = getServer();
		final PluginManager pluginManager = server.getPluginManager();

		new ListenerInitializer(this, gameMechanicsManager);

		if (pluginManager.isPluginEnabled("ProtocolLib")) {
			new AdapterInitializer(this, gameMechanicsManager);
		}

		final Material fenceMaterial;

		// TODO: Version check
		if (true) {
			// 1.13
			fenceMaterial = Material.getMaterial("IRON_BARS");
		} else {
			// 1.12
			fenceMaterial = Material.getMaterial("IRON_FENCE");
		}

		final ShapedRecipe bookRecipe = new ShapedRecipe(new ItemStack(Material.BOOK));
		final ShapedRecipe chainmailHelmet = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_HELMET));
		final ShapedRecipe chainmailChestplate = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		final ShapedRecipe chainmailLeggings = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		final ShapedRecipe chainmailBoots = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_BOOTS));

		bookRecipe.shape("BB");
		bookRecipe.setIngredient('B', Material.ENCHANTED_BOOK);

		chainmailHelmet.shape("BBB", "B B");
		chainmailHelmet.setIngredient('B', fenceMaterial);

		chainmailChestplate.shape("B B", "BBB", "BBB");
		chainmailChestplate.setIngredient('B', fenceMaterial);

		chainmailLeggings.shape("BBB", "B B", "B B");
		chainmailLeggings.setIngredient('B', fenceMaterial);

		chainmailBoots.shape("B B", "B B");
		chainmailBoots.setIngredient('B', fenceMaterial);

		server.addRecipe(bookRecipe);
		server.addRecipe(chainmailHelmet);
		server.addRecipe(chainmailChestplate);
		server.addRecipe(chainmailLeggings);
		server.addRecipe(chainmailBoots);

		server.getPluginCommand("cobblestone").setExecutor(new CobblestoneCMD(gameMechanicsManager));
		server.getPluginCommand("dirt").setExecutor(new DirtCMD(gameMechanicsManager));
		server.getPluginCommand("magnet").setExecutor(new MagnetCMD(gameMechanicsManager));
	}

	public static GameMechanicsManager getGameMechanicsManager() {
		return gameMechanicsManager;
	}
}