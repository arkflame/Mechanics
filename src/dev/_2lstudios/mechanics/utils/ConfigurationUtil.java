package dev._2lstudios.mechanics.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationUtil {
  private final Plugin plugin;

  public ConfigurationUtil(Plugin plugin) {
    this.plugin = plugin;
  }

  public YamlConfiguration getConfiguration(String filePath) {
    File dataFolder = this.plugin.getDataFolder();
    File file = new File(filePath.replace("%datafolder%", dataFolder.toPath().toString()));

    if (file.exists()) {
      return YamlConfiguration.loadConfiguration(file);
    }
    return new YamlConfiguration();
  }

  public void createConfiguration(String file) {
    try {
      File dataFolder = this.plugin.getDataFolder();

      file = file.replace("%datafolder%", dataFolder.toPath().toString());

      File configFile = new File(file);

      if (!configFile.exists()) {
        String[] files = file.split("/");
        InputStream inputStream = this.plugin.getClass().getClassLoader().getResourceAsStream(files[files.length - 1]);
        File parentFile = configFile.getParentFile();

        if (parentFile != null) {
          parentFile.mkdirs();
        }
        if (inputStream != null) {
          Files.copy(inputStream, configFile.toPath(), new java.nio.file.CopyOption[0]);
          this.plugin.getLogger().log(Level.INFO, ("[%pluginname%] File " + configFile + " has been created!")
              .replace("%pluginname%", this.plugin.getDescription().getName()));
        } else {
          configFile.createNewFile();
        }
      }
    } catch (IOException e) {
      this.plugin.getLogger().log(Level.INFO, "[%pluginname%] Unable to create configuration file!"
          .replace("%pluginname%", this.plugin.getDescription().getName()));
    }
  }

  public void saveConfiguration(YamlConfiguration yamlConfiguration, String file) {
    this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
      try {
        File dataFolder = this.plugin.getDataFolder();

        yamlConfiguration.save(file.replace("%datafolder%", dataFolder.toPath().toString()));
      } catch (IOException e) {
        this.plugin.getLogger().log(Level.INFO, "[%pluginname%] Unable to save configuration file!"
            .replace("%pluginname%", this.plugin.getDescription().getName()));
      }
    });
  }

  public void deleteConfiguration(String file) {
    this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
      File file1 = new File(file);
      if (file1.exists())
        file1.delete();
    });
  }
}
