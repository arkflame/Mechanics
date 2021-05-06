package dev._2lstudios.mechanics.managers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import org.bukkit.DyeColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public class EnchantingManager {
  private final ItemStack lapisLazuli;
  private final boolean limitEnchants;
  private Collection<Enchantment> enchants = new HashSet<>();

  public EnchantingManager(Configuration configuration) {
    Collection<String> enchants = new HashSet<>();
    Enchantment[] allEnchantments = Enchantment.values();
    Dye dye = new Dye();

    dye.setColor(DyeColor.BLUE);

    this.lapisLazuli = dye.toItemStack(64);
    this.limitEnchants = configuration.getBoolean("limit_enchants");
    enchants.add("BINDING_CURSE");
    enchants.add("SWEEPING_EDGE");
    enchants.add("VANISHING_CURSE");
    enchants.add("FROST_WALKER");
    enchants.add("KNOCKBACK");
    enchants.add("ARROW_KNOCKBACK");
    enchants.add("PUNCH");
    enchants.add("MENDING");

    for (String enchantment : enchants) {
      byte b;
      int i;
      Enchantment[] arrayOfEnchantment;
      for (i = (arrayOfEnchantment = allEnchantments).length, b = 0; b < i;) {
        Enchantment enchantment1 = arrayOfEnchantment[b];
        if (enchantment.equalsIgnoreCase(enchantment1.getName()))
          this.enchants.add(enchantment1);
        b++;
      }

    }
  }

  public ItemStack getLapisLazuli() {
    return this.lapisLazuli;
  }

  public ItemStack fixEnchants(ItemStack itemStack) {
    if (itemStack != null) {
      for (Enchantment enchantment : this.enchants) {
        if (itemStack.containsEnchantment(enchantment)) {
          itemStack.removeEnchantment(enchantment);
        }
      }
      Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();

      if (this.limitEnchants && enchantments != null && !enchantments.isEmpty()) {
        boolean damageLimit = false, protectionLimit = false;

        for (Enchantment enchantment : new HashSet<>(enchantments.keySet())) {
          if (!damageLimit && enchantment.getName().equals(Enchantment.DAMAGE_ALL.getName())
              && ((Integer) enchantments.get(enchantment)).intValue() > 2) {
            itemStack.addEnchantment(Enchantment.DAMAGE_ALL, 2);
            damageLimit = true;
            continue;
          }
          if (!protectionLimit && enchantment.getName().equals(Enchantment.PROTECTION_ENVIRONMENTAL.getName())
              && ((Integer) enchantments.get(enchantment)).intValue() > 2) {
            itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            protectionLimit = true;
          }
        }
      }
    }

    return itemStack;
  }

  public void fixEnchants(Map<Enchantment, Integer> enchantments) {
    if (this.limitEnchants) {
      boolean damageLimit = false, protectionLimit = false;

      for (Enchantment enchantment : this.enchants) {
        enchantments.remove(enchantment);
      }

      for (Enchantment enchantment : new HashSet<>(enchantments.keySet())) {
        String enchantmentName = enchantment.getName();

        if (!damageLimit && enchantmentName.equals(Enchantment.DAMAGE_ALL.getName())
            && ((Integer) enchantments.get(enchantment)).intValue() > 2) {
          enchantments.put(Enchantment.DAMAGE_ALL, Integer.valueOf(2));
          damageLimit = true;
          continue;
        }
        if (!protectionLimit && enchantmentName.equals(Enchantment.PROTECTION_ENVIRONMENTAL.getName())
            && ((Integer) enchantments.get(enchantment)).intValue() > 2) {
          enchantments.put(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.valueOf(2));
          protectionLimit = true;
        }
      }
    }
  }
}
