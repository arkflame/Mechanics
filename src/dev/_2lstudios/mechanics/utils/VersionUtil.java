package dev._2lstudios.mechanics.utils;

import org.bukkit.Bukkit;

public class VersionUtil {
	private static Boolean oneDotNine = null;

	public static boolean isOneDotNine() {
		if (oneDotNine == null)
			oneDotNine = !Bukkit.getServer().getVersion().contains("1.8")
					&& !Bukkit.getServer().getVersion().contains("1.7");

		return oneDotNine;
	}
}
