package mod.chloeprime.gunsmithlib.common.util;

import net.minecraft.world.damagesource.DamageSource;

@FunctionalInterface
public interface HurtFunction2 {
    void invoke(DamageSource source, float amount);
}
