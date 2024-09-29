package mod.chloeprime.gunsmithlib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.entity.shooter.LivingEntityReload;
import mod.chloeprime.gunsmithlib.common.util.GsHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LivingEntityReload.class, remap = false)
public class MixinReloadProcedure {
    @WrapOperation(
            method = "tickReloadState",
            at = @At(value = "INVOKE", target = "Lcom/tacz/guns/api/item/gun/AbstractGunItem;doReload(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Z)V")
    )
    private void postFeedEvents(AbstractGunItem gun, LivingEntity shooter, ItemStack gunItem, boolean loadBarrel, Operation<Void> original) {
        GsHooks.onReloadFeed(gun, shooter, gunItem, loadBarrel, () -> original.call(gun, shooter, gunItem, loadBarrel));
    }
}
