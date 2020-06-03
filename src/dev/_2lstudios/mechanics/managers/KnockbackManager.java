package dev._2lstudios.mechanics.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import dev._2lstudios.mechanics.utils.VersionUtil;

import java.lang.reflect.InvocationTargetException;

public class KnockbackManager {
	public Vector applyKnockbackFormula(final Player entity, final Player damager) {
		final Location damagerPlayerLocation = entity.getLocation();
		final Location damagedPlayerLocation = damager.getLocation();
		final double xDiff = damagerPlayerLocation.getX() - damagedPlayerLocation.getX();
		final double zDiff = damagerPlayerLocation.getZ() - damagedPlayerLocation.getZ();
		final float horizontalDistance = (float) Math.sqrt(xDiff * xDiff + zDiff * zDiff);
		final Vector velocity = entity.getVelocity();
		final ItemStack itemStack = damager.getItemInHand();
		final float oneDotNineAmount = 0.245f, oneDotEightAmount = 0.55f * 1.15f;

		velocity.setX(xDiff / horizontalDistance
				* (VersionUtil.isOneDotNine() ? oneDotNineAmount * 1.25 : oneDotEightAmount));
		velocity.setY(0.335D);
		velocity.setZ(zDiff / horizontalDistance
				* (VersionUtil.isOneDotNine() ? oneDotNineAmount * 1.25 : oneDotEightAmount));

		final double i = ((damager.isSprinting() ? 1 : 0)
				+ (itemStack == null ? 0 : itemStack.getEnchantmentLevel(Enchantment.KNOCKBACK) * 0.65));

		if (i > 0.4) {
			velocity.setX((velocity.getX() * (VersionUtil.isOneDotNine() ? 2 : 1)) * (i * 1.65));
			velocity.setZ((velocity.getZ() * (VersionUtil.isOneDotNine() ? 2 : 1)) * (i * 1.65));
		}

		if (velocity.getX() < 9999)
			entity.setVelocity(velocity);

		return velocity;
	}

	public void sendKnockbackPacket(final Player player, final Vector vector) {
		final PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_VELOCITY);
		final StructureModifier<Integer> integers = packet.getIntegers();

		integers.write(0, player.getEntityId());
		integers.write(1, (int) (vector.getX() * 8000.0D));
		integers.write(2, (int) (vector.getY() * 8000.0D));
		integers.write(3, (int) (vector.getZ() * 8000.0D));

		final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

		try {
			protocolManager.sendServerPacket(player, packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
