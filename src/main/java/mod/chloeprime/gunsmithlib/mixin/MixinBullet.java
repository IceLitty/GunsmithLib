package mod.chloeprime.gunsmithlib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tacz.guns.entity.EntityKineticBullet;
import mod.chloeprime.gunsmithlib.common.util.HurtFunction1;
import mod.chloeprime.gunsmithlib.common.util.SpecialHurtable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityKineticBullet.class, remap = false)
public class MixinBullet {
    @WrapOperation(
            method = "tacAttackEntity",
            at = @At(value = "INVOKE", remap = true, target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean useSpecialHurtByTag(Entity victim, DamageSource source, float amount, Operation<Boolean> original) {
        if (!(victim instanceof SpecialHurtable injected)){
            return original.call(victim, source, amount);
        }
        HurtFunction1 method = injected.gunsmith$usingSpecialHurt()
                ? injected.getSpecialHurtFunction1()
                : ((source1, amount1) -> original.call(victim, source1, amount1));
        return method.invoke(source, amount);
    }
}
