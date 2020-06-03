package dev._2lstudios.mechanics.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RegenerationManager {
	final private Map<Player, Long> healTimes = new HashMap<>();

	public Map<Player, Long> getHealTimes() {
		return healTimes;
	}
}
