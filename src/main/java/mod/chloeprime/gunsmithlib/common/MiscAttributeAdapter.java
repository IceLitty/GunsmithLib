package mod.chloeprime.gunsmithlib.common;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.util.AttachmentDataUtils;
import mod.chloeprime.gunsmithlib.common.util.GsHelper;
import mod.chloeprime.gunsmithlib.common.util.InternalBulletCreateEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;
import java.util.function.BiConsumer;

import static mod.chloeprime.gunsmithlib.api.common.GunAttributes.*;

@Mod.EventBusSubscriber
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
        var attribute = BULLET_DAMAGE.get();
        var oldDamage = event.getBaseAmount();
        var newDamage = GsHelper.getAttributeValueWithBase(attacker, attribute, oldDamage);
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
        var attribute = BULLET_SPEED.get();
        var oldSpeed = oldMotion.length();
        var newSpeed = GsHelper.getAttributeValueWithBase(attacker, attribute, oldSpeed);
        // 速度沒有被Attribute修改的情況
        if (Math.abs(newSpeed - oldSpeed) < 1e-4) {
            return;
        }
        var direction = oldSpeed == 0 ? bullet.getLookAngle() : oldMotion.scale(1 / oldSpeed);
        bullet.setDeltaMovement(direction.scale(newSpeed));
    }

    @SubscribeEvent
    public static void defaultValues(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide) {
            return;
        }
        final var interval = 5;
        if ((event.player.level().getGameTime() + event.player.hashCode()) % interval != 0) {
            return;
        }
        var newMH = event.player.getMainHandItem();
        Optional.ofNullable(IGun.getIGunOrNull(newMH))
                .map(kun -> kun.getGunId(newMH))
                .flatMap(TimelessAPI::getCommonGunIndex)
                .ifPresentOrElse(index -> {
                    var damage = AttachmentDataUtils.getDamageWithAttachment(newMH, index.getGunData()) / index.getBulletData().getBulletAmount();
                    var speed = index.getBulletData().getSpeed() / 20;
                    setBaseValue(event.player, BULLET_DAMAGE.get(), damage);
                    setBaseValue(event.player, BULLET_SPEED.get(), speed);
                }, () -> {
                    setBaseValue(event.player, BULLET_DAMAGE.get(), 0);
                    setBaseValue(event.player, BULLET_SPEED.get(), 0);
                });
    }

    private static void setBaseValue(LivingEntity owner, Attribute attribute, double value) {
        Optional.ofNullable(owner.getAttribute(attribute))
                .ifPresent(ai -> ai.setBaseValue(value));
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class AttributeAttacher {
        @SubscribeEvent
        public static void onAttachAttributes(EntityAttributeModificationEvent event) {
            event.getTypes().forEach(et -> addAll(et, event::add,
                    BULLET_DAMAGE,
                    BULLET_SPEED,
                    V_RECOIL,
                    H_RECOIL));
        }

        @SafeVarargs
        private static void addAll(EntityType<? extends LivingEntity> type, BiConsumer<EntityType<? extends LivingEntity>, Attribute> add, RegistryObject<? extends Attribute>... attribs) {
            for (RegistryObject<? extends Attribute> a : attribs)
                add.accept(type, a.get());
        }
    }
}
