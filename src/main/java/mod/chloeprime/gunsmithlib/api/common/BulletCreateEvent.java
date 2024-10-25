package mod.chloeprime.gunsmithlib.api.common;

import mod.chloeprime.gunsmithlib.api.util.GunInfo;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nonnull;

public final class BulletCreateEvent extends Event {
    @ApiStatus.Internal
    public BulletCreateEvent(@Nonnull Projectile bullet, @Nonnull LivingEntity shooter, @Nonnull GunInfo gun) {
        this.bullet = bullet;
        this.shooter = shooter;
        this.gun = gun;
    }

    public @Nonnull Projectile getBullet() {
        return bullet;
    }

    public @Nonnull LivingEntity getShooter() {
        return shooter;
    }

    public @Nonnull GunInfo getGunInfo() {
        return gun;
    }

    public @Nonnull ItemStack getGun() {
        return gun.gunStack();
    }

    private final @Nonnull Projectile bullet;
    private final @Nonnull LivingEntity shooter;
    private final @Nonnull GunInfo gun;
}
