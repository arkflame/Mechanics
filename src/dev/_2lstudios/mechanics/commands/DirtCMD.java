package dev._2lstudios.mechanics.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev._2lstudios.mechanics.managers.GameMechanicsManager;
import dev._2lstudios.mechanics.player.GameMechanicsPlayer;
import dev._2lstudios.mechanics.player.PlayerManager;

public class DirtCMD implements CommandExecutor {
	private final PlayerManager playerManager;

	public DirtCMD(final GameMechanicsManager gameMechanicsManager) {
		this.playerManager = gameMechanicsManager.getPlayerManager();
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			final GameMechanicsPlayer gameMechanicsPlayer = playerManager.get(((Player) commandSender).getUniqueId());

			gameMechanicsPlayer.setCobblestone(!gameMechanicsPlayer.isDirt());

			if (gameMechanicsPlayer.isDirt())
				commandSender
						.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aActivaste el filtro de tierra!"));
			else
				commandSender.sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&cDesactivaste el filtro de tierraw!"));
		} else
			commandSender.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&cNo puedes usar este comando desde la consola!"));

		return true;
	}
}
