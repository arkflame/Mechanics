package dev._2lstudios.mechanics.commands;

import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.player.GameMechanicsPlayer;
import dev._2lstudios.mechanics.player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DirtCMD implements CommandExecutor {
  private final PlayerManager playerManager;

  public DirtCMD(GameMechanicsManager gameMechanicsManager) {
    this.playerManager = gameMechanicsManager.getPlayerManager();
  }

  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    if (commandSender instanceof Player) {
      GameMechanicsPlayer gameMechanicsPlayer = this.playerManager.get((Player) commandSender);

      gameMechanicsPlayer.setCobblestone(!gameMechanicsPlayer.isDirt());

      if (gameMechanicsPlayer.isDirt()) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aActivaste el filtro de tierra!"));
      } else {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDesactivaste el filtro de tierraw!"));
      }
    } else {
      commandSender
          .sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo puedes usar este comando desde la consola!"));
    }

    return true;
  }
}
