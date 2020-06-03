package dev._2lstudios.mechanics.managers;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class MaterialManager {
	final private Map<Material, Material> materialHashMap = new HashMap<>();

	MaterialManager() {
		materialHashMap.put(Material.getMaterial("END_BRICKS"), Material.getMaterial("ENDER_STONE"));
		materialHashMap.put(Material.getMaterial("PURPUR_BLOCK"), Material.getMaterial("ENDER_STONE"));
		materialHashMap.put(Material.getMaterial("MAGMA"), Material.getMaterial("NETHERRACK"));
		materialHashMap.put(Material.getMaterial("OBSERVER"), Material.getMaterial("COBBLESTONE"));
		materialHashMap.put(Material.getMaterial("NETHER_WART_BLOCK"), Material.getMaterial("NETHER_BRICK"));
		materialHashMap.put(Material.getMaterial("RED_NETHER_BRICK"), Material.getMaterial("NETHER_BRICK"));
		materialHashMap.put(Material.getMaterial("BONE_BLOCK"), Material.getMaterial("SANDSTONE"));
		materialHashMap.put(Material.getMaterial("CONCRETE"), Material.getMaterial("HARD_CLAY"));
		materialHashMap.put(Material.getMaterial("CONCRETE_POWDER"), Material.getMaterial("SAND"));
		materialHashMap.put(Material.getMaterial("CHORUS_FLOWER"), Material.getMaterial("AIR"));
		materialHashMap.put(Material.getMaterial("CHORUS_PLANT"), Material.getMaterial("AIR"));
		materialHashMap.put(Material.getMaterial("END_ROD"), Material.getMaterial("AIR"));
		materialHashMap.put(Material.getMaterial("SHIELD"), Material.getMaterial("AIR"));
		materialHashMap.put(Material.getMaterial("IRON_NUGGET"), Material.getMaterial("AIR"));
		materialHashMap.put(Material.getMaterial("SPECTRAL_ARROW"), Material.getMaterial("AIR"));
		materialHashMap.put(Material.getMaterial("TIPPED_ARROW"), Material.getMaterial("AIR"));
		materialHashMap.put(Material.getMaterial("BEETROOT_SOUP"), Material.getMaterial("AIR"));
		materialHashMap.put(Material.getMaterial("END_CRYSTAL"), Material.getMaterial("AIR"));
	}

	public Material getMaterial(final Material material) {
		return materialHashMap.getOrDefault(material, null);
	}
}
