package dev._2lstudios.mechanics.brewing;

import org.bukkit.Material;

class BrewingPotion {
  private final Material[] requiredMaterials;
  private final Material[] optionalMaterials;

  public BrewingPotion(Material[] requiredMaterials, Material[] optionalMaterials, boolean splash, boolean extended,
      short data) {
    this.requiredMaterials = requiredMaterials;
    this.optionalMaterials = optionalMaterials;
    this.splash = splash;
    this.extended = extended;
    this.data = data;
  }

  private final boolean splash;
  private final boolean extended;
  private final short data;

  public Material[] getRequiredMaterials() {
    return this.requiredMaterials;
  }

  public Material[] getOptionalMaterials() {
    return this.optionalMaterials;
  }

  public boolean isSplash() {
    return this.splash;
  }

  public boolean isExtended() {
    return this.extended;
  }

  public short getData() {
    return this.data;
  }
}
