package dev._2lstudios.mechanics.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
	private Map<UUID, GameMechanicsPlayer> gameMechanicsPlayerMap = new HashMap<>();

	public GameMechanicsPlayer get(final UUID uuid) {
		GameMechanicsPlayer gameMechanicsPlayer = gameMechanicsPlayerMap.getOrDefault(uuid, null);

		if (gameMechanicsPlayer == null) {
			gameMechanicsPlayer = new GameMechanicsPlayer();
			gameMechanicsPlayerMap.put(uuid, gameMechanicsPlayer);
		}

		return gameMechanicsPlayer;
	}

	public void remove(final UUID uuid) {
		gameMechanicsPlayerMap.remove(uuid);
	}
}
