package dev._2lstudios.mechanics.utils;

import java.util.Arrays;
import java.util.List;

import com.comphenix.protocol.wrappers.BlockPosition;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class MinecraftUtil {
    private static List<Material> interactables = Arrays.asList(Material.getMaterial("ACACIA_DOOR"),
            Material.getMaterial("ACACIA_FENCE_GATE"), Material.getMaterial("ANVIL"), Material.getMaterial("BEACON"),
            Material.getMaterial("BED"), Material.getMaterial("BIRCH_DOOR"), Material.getMaterial("BIRCH_FENCE_GATE"),
            Material.getMaterial("BOAT"), Material.getMaterial("BOAT_ACACIA"), Material.getMaterial("BOAT_BIRCH"),
            Material.getMaterial("BOAT_DARK_OAK"), Material.getMaterial("BOAT_JUNGLE"),
            Material.getMaterial("BOAT_SPRUCE"), Material.getMaterial("BREWING_STAND"), Material.getMaterial("COMMAND"),
            Material.getMaterial("CHEST"), Material.getMaterial("DARK_OAK_DOOR"),
            Material.getMaterial("DARK_OAK_FENCE_GATE"), Material.getMaterial("DAYLIGHT_DETECTOR"),
            Material.getMaterial("DAYLIGHT_DETECTOR_INVERTED"), Material.getMaterial("DISPENSER"),
            Material.getMaterial("DROPPER"), Material.getMaterial("ENCHANTMENT_TABLE"),
            Material.getMaterial("ENDER_CHEST"), Material.getMaterial("FENCE_GATE"), Material.getMaterial("FURNACE"),
            Material.getMaterial("HOPPER"), Material.getMaterial("HOPPER_MINECART"), Material.getMaterial("ITEM_FRAME"),
            Material.getMaterial("JUNGLE_DOOR"), Material.getMaterial("JUNGLE_FENCE_GATE"),
            Material.getMaterial("LEVER"), Material.getMaterial("MINECART"), Material.getMaterial("NOTE_BLOCK"),
            Material.getMaterial("POWERED_MINECART"), Material.getMaterial("REDSTONE_COMPARATOR"),
            Material.getMaterial("REDSTONE_COMPARATOR_OFF"), Material.getMaterial("REDSTONE_COMPARATOR_ON"),
            Material.getMaterial("SIGN"), Material.getMaterial("SIGN_POST"), Material.getMaterial("STORAGE_MINECART"),
            Material.getMaterial("TRAP_DOOR"), Material.getMaterial("TRAPPED_CHEST"), Material.getMaterial("WALL_SIGN"),
            Material.getMaterial("WOOD_BUTTON"), Material.getMaterial("WOOD_DOOR"));

    public static boolean isInteractable(final Material material) {
        return interactables.contains(material);
    }

    public static boolean isInteractable(final Block block) {
        return block == null ? false : isInteractable(block.getType());
    }

    public static Block getBlockAt(final World world, final BlockPosition blockPosition) {
        return world.getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }
}
