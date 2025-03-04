package mod.chloeprime.gunsmithlib.common;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.util.AttachmentDataUtils;
import mod.chloeprime.gunsmithlib.common.util.GsHelper;
import mod.chloeprime.gunsmithlib.common.util.InternalBulletCreateEvent;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Optional;
import java.util.function.BiConsumer;

import static mod.chloeprime.gunsmithlib.api.common.GunAttributes.*;

@EventBusSubscriber
public class MiscAttributeAdapter {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void bulletDamage(EntityHurtByGunEvent.Pre event) {
        if (event.getLogicalSide().isClient()) {
            return;
        }
        var attacker = event.getAttacker();
        if (attacker == null) {
            return;
        }
        var oldDamage = event.getBaseAmount();
        var newDamage = GsHelper.getAttributeValueWithBase(attacker, BULLET_DAMAGE, oldDamage);
        event.setBaseAmount((float) newDamage);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void bulletSpeed(InternalBulletCreateEvent event) {
        var attacker = event.getImpl().getShooter();
        if (attacker.level().isClientSide) {
            return;
        }
        var bullet = event.getImpl().getBullet();

        var oldMotion = bullet.getDeltaMovement();
        var oldSpeed = oldMotion.length();
        var newSpeed = GsHelper.getAttributeValueWithBase(attacker, BULLET_SPEED, oldSpeed);
        // 速度沒有被Attribute修改的情況
        if (Math.abs(newSpeed - oldSpeed) < 1e-4) {
            return;
        }
        var direction = oldSpeed == 0 ? bullet.getLookAngle() : oldMotion.scale(1 / oldSpeed);
        bullet.setDeltaMovement(direction.scale(newSpeed));
    }

    @SubscribeEvent
    public static void defaultValues(PlayerTickEvent.Pre event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        final var interval = 5;
        if ((event.getEntity().level().getGameTime() + event.getEntity().hashCode()) % interval != 0) {
            return;
        }
        var newMH = event.getEntity().getMainHandItem();
        Optional.ofNullable(IGun.getIGunOrNull(newMH))
                .map(kun -> kun.getGunId(newMH))
                .flatMap(TimelessAPI::getCommonGunIndex)
                .ifPresentOrElse(index -> {
                    var damage = AttachmentDataUtils.getDamageWithAttachment(newMH, index.getGunData()) / index.getBulletData().getBulletAmount();
                    var speed = index.getBulletData().getSpeed() / 20;
                    setBaseValue(event.getEntity(), BULLET_DAMAGE, damage);
                    setBaseValue(event.getEntity(), BULLET_SPEED, speed);
                }, () -> {
                    setBaseValue(event.getEntity(), BULLET_DAMAGE, 0);
                    setBaseValue(event.getEntity(), BULLET_SPEED, 0);
                });
    }

    private static void setBaseValue(LivingEntity owner, Holder<Attribute> attribute, double value) {
        Optional.ofNullable(owner.getAttribute(attribute))
                .ifPresent(ai -> ai.setBaseValue(value));
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class AttributeAttacher {
        @SubscribeEvent
        public static void onAttachAttributes(EntityAttributeModificationEvent event) {
            event.getTypes().forEach(et -> {
                event.add(et, BULLET_DAMAGE);
                event.add(et, BULLET_SPEED);
                event.add(et, V_RECOIL);
                event.add(et, H_RECOIL);
            });
        }

        @SafeVarargs
        private static void addAll(EntityType<? extends LivingEntity> type, BiConsumer<EntityType<? extends LivingEntity>, Attribute> add, DeferredHolder<? extends Attribute, ? extends Attribute>... attribs) {
            for (DeferredHolder<? extends Attribute, ? extends Attribute> a : attribs)
                add.accept(type, a.get());
        }
    }
}
