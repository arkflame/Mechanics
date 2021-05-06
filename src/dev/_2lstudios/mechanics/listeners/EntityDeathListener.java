package dev._2lstudios.mechanics.listeners;

import dev._2lstudios.mechanics.utils.VersionUtil;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EntityDeathListener implements Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
  public void onEntityDeath(EntityDeathEvent event) {
    LivingEntity livingEntity = event.getEntity();
    Player killer = livingEntity.getKiller();

    if (killer != null) {
      PlayerInventory playerInventory = killer.getInventory();
      ItemStack itemStack = playerInventory.getItem(playerInventory.getHeldItemSlot());
      Location location = killer.getLocation();
      World world = livingEntity.getWorld();

      if (VersionUtil.isOneDotNine()) {
        killer.playSound(location, Sound.valueOf("ENTITY_ARROW_HIT_PLAYER"), 0.9F,
            (float) (1.5D + Math.random() / 2.0D));
      } else {
        killer.playSound(location, Sound.valueOf("SUCCESSFUL_HIT"), 0.9F, (float) (1.5D + Math.random() / 2.0D));
      }
      world.playEffect(livingEntity.getLocation().add(0.0D, 0.85D, 0.0D), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);

      if (itemStack != null) {
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();

        if (enchantments != null && enchantments.containsKey(Enchantment.LOOT_BONUS_MOBS))
          event.setDroppedExp(
              event.getDroppedExp() * (((Integer) enchantments.get(Enchantment.LOOT_BONUS_MOBS)).intValue() + 1));
      }
    } else if (livingEntity instanceof org.bukkit.entity.Monster && event.getDroppedExp() < 1) {
      event.setDroppedExp(1);
    }
  }
}
