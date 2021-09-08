package dev._2lstudios.mechanics;

import dev._2lstudios.mechanics.adapters.MechanicsAdapters;
import dev._2lstudios.mechanics.commands.CobblestoneCMD;
import dev._2lstudios.mechanics.commands.DirtCMD;
import dev._2lstudios.mechanics.commands.MagnetCMD;
import dev._2lstudios.mechanics.listeners.MechanicsListeners;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.utils.ConfigurationUtil;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Mechanics extends JavaPlugin {
  private static GameMechanicsManager instance;

  public static synchronized void setInstance(final GameMechanicsManager instance) {
    Mechanics.instance = instance;
  }

  public static synchronized GameMechanicsManager getInstance() {
    return instance;
  }

  public void onEnable() {
    final GameMechanicsManager gameMechanicsManager = new GameMechanicsManager(this, new ConfigurationUtil(this));

    setInstance(gameMechanicsManager);

    final Server server = getServer();
    final PluginManager pluginManager = server.getPluginManager();

    if (pluginManager.isPluginEnabled("ProtocolLib")) {

    }

    for (final Player player : server.getOnlinePlayers()) {
      instance.getPlayerManager().add(player);
    }

    final Material fenceMaterial = Material.getMaterial("IRON_FENCE");

    final ShapedRecipe bookRecipe = new ShapedRecipe(new ItemStack(Material.BOOK));
    final ShapedRecipe chainmailHelmet = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_HELMET));
    final ShapedRecipe chainmailChestplate = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
    final ShapedRecipe chainmailLeggings = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_LEGGINGS));
    final ShapedRecipe chainmailBoots = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_BOOTS));

    bookRecipe.shape(new String[] { "B" });
    bookRecipe.setIngredient('B', Material.ENCHANTED_BOOK);

    chainmailHelmet.shape(new String[] { "BBB", "B B" });
    chainmailHelmet.setIngredient('B', fenceMaterial);

    chainmailChestplate.shape(new String[] { "B B", "BBB", "BBB" });
    chainmailChestplate.setIngredient('B', fenceMaterial);

    chainmailLeggings.shape(new String[] { "BBB", "B B", "B B" });
    chainmailLeggings.setIngredient('B', fenceMaterial);

    chainmailBoots.shape(new String[] { "B B", "B B" });
    chainmailBoots.setIngredient('B', fenceMaterial);

    server.addRecipe(bookRecipe);
    server.addRecipe(chainmailHelmet);
    server.addRecipe(chainmailChestplate);
    server.addRecipe(chainmailLeggings);
    server.addRecipe(chainmailBoots);

    MechanicsAdapters.register(this, gameMechanicsManager);
    MechanicsListeners.register(this, gameMechanicsManager);

    getCommand("cobblestone").setExecutor((CommandExecutor) new CobblestoneCMD(instance));
    getCommand("dirt").setExecutor((CommandExecutor) new DirtCMD(instance));
    getCommand("magnet").setExecutor((CommandExecutor) new MagnetCMD(instance));

    server.getScheduler().runTaskTimer((Plugin) this, () -> instance.getBlockManager().clear(), 20L, 20L);
  }
}
