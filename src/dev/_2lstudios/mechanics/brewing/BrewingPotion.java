package dev._2lstudios.mechanics.brewing;

import org.bukkit.Material;

class BrewingPotion {
    private final Material[] requiredMaterials, optionalMaterials;
    private final boolean splash, extended;
    private final short data;

    public BrewingPotion(final Material[] requiredMaterials, final Material[] optionalMaterials, final boolean splash,
            final boolean extended, final short data) {
        this.requiredMaterials = requiredMaterials;
        this.optionalMaterials = optionalMaterials;
        this.splash = splash;
        this.extended = extended;
        this.data = data;
    }

    public Material[] getRequiredMaterials() {
        return requiredMaterials;
    }

    public Material[] getOptionalMaterials() {
        return optionalMaterials;
    }

    public boolean isSplash() {
        return splash;
    }

    public boolean isExtended() {
        return extended;
    }

    public short getData() {
        return data;
    }
}