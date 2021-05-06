package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.utils.VersionUtil;
import java.util.Collection;
import java.util.HashSet;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class EntityDamageByEntityListener implements Listener {
    private Collection<Material> axes = new HashSet<>();
    private Collection<Material> spades = new HashSet<>();

    public EntityDamageByEntityListener() {
        this.axes.add(Material.getMaterial("DIAMOND_AXE"));
        this.axes.add(Material.getMaterial("IRON_AXE"));
        this.axes.add(Material.getMaterial("STONE_AXE"));
        this.axes.add(Material.getMaterial("GOLD_AXE"));
        this.axes.add(Material.getMaterial("WOOD_AXE"));

        this.spades.add(Material.getMaterial("DIAMOND_SPADE"));
        this.spades.add(Material.getMaterial("IRON_SPADE"));
        this.spades.add(Material.getMaterial("STONE_SPADE"));
        this.spades.add(Material.getMaterial("GOLD_SPADE"));
        this.spades.add(Material.getMaterial("WOOD_SPADE"));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntityLow(EntityDamageByEntityEvent event) {
        EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (event.getDamager() instanceof org.bukkit.entity.EnderPearl
                || (VersionUtil.isOneDotNine() && damageCause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamageByEntityHigh(EntityDamageByEntityEvent event) {
        double damage = event.getDamage();

        if (damage > 0.0D) {
            Entity hitEntity = event.getEntity();

            if (hitEntity instanceof LivingEntity) {
                LivingEntity hitLivingEntity = (LivingEntity) hitEntity;

                if (VersionUtil.isOneDotNine()) {
                    Entity damager = event.getDamager();

                    if (damager instanceof Projectile) {
                        Projectile projectile = (Projectile) damager;
                        EntityType projectileType = projectile.getType();

                        if (projectileType == EntityType.ARROW) {
                            event.setDamage(damage / 2.0D);
                        } else {
                            Vector velocity = projectile.getVelocity();

                            hitLivingEntity.damage(0.01D);
                            hitLivingEntity.setVelocity(velocity.normalize().multiply(0.35D).setY(0.32D));
                        }
                    } else if (damager instanceof HumanEntity) {
                        PlayerInventory inventory = ((HumanEntity) damager).getInventory();
                        ItemStack heldItem = inventory.getItem(inventory.getHeldItemSlot());

                        if (heldItem != null) {
                            Material material = heldItem.getType();

                            if (this.axes.contains(material)) {
                                event.setDamage(damage - 3.0D);
                            } else if (this.spades.contains(material)) {
                                event.setDamage(damage - 0.5D);
                            }
                        }
                    }
                }
            }
        }
    }
}
