package dev._2lstudios.mechanics.managers;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;

public class MaterialManager {
  private final Map<Material, Material> materialHashMap = new HashMap<>();

  MaterialManager() {
    this.materialHashMap.put(Material.getMaterial("END_BRICKS"), Material.getMaterial("ENDER_STONE"));
    this.materialHashMap.put(Material.getMaterial("PURPUR_BLOCK"), Material.getMaterial("ENDER_STONE"));
    this.materialHashMap.put(Material.getMaterial("MAGMA"), Material.getMaterial("NETHERRACK"));
    this.materialHashMap.put(Material.getMaterial("OBSERVER"), Material.getMaterial("COBBLESTONE"));
    this.materialHashMap.put(Material.getMaterial("NETHER_WART_BLOCK"), Material.getMaterial("NETHER_BRICK"));
    this.materialHashMap.put(Material.getMaterial("RED_NETHER_BRICK"), Material.getMaterial("NETHER_BRICK"));
    this.materialHashMap.put(Material.getMaterial("BONE_BLOCK"), Material.getMaterial("SANDSTONE"));
    this.materialHashMap.put(Material.getMaterial("CONCRETE"), Material.getMaterial("HARD_CLAY"));
    this.materialHashMap.put(Material.getMaterial("CONCRETE_POWDER"), Material.getMaterial("SAND"));
    this.materialHashMap.put(Material.getMaterial("CHORUS_FLOWER"), Material.getMaterial("AIR"));
    this.materialHashMap.put(Material.getMaterial("CHORUS_PLANT"), Material.getMaterial("AIR"));
    this.materialHashMap.put(Material.getMaterial("END_ROD"), Material.getMaterial("AIR"));
    this.materialHashMap.put(Material.getMaterial("SHIELD"), Material.getMaterial("AIR"));
    this.materialHashMap.put(Material.getMaterial("IRON_NUGGET"), Material.getMaterial("AIR"));
    this.materialHashMap.put(Material.getMaterial("SPECTRAL_ARROW"), Material.getMaterial("AIR"));
    this.materialHashMap.put(Material.getMaterial("TIPPED_ARROW"), Material.getMaterial("AIR"));
    this.materialHashMap.put(Material.getMaterial("BEETROOT_SOUP"), Material.getMaterial("AIR"));
    this.materialHashMap.put(Material.getMaterial("END_CRYSTAL"), Material.getMaterial("AIR"));
  }

  public Material getMaterial(Material material) {
    return this.materialHashMap.getOrDefault(material, null);
  }
}
