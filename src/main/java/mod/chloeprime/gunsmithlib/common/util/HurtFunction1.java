package mod.chloeprime.gunsmithlib.common.util;

import net.minecraft.world.damagesource.DamageSource;

@FunctionalInterface
public interface HurtFunction1 {
    boolean invoke(DamageSource source, float amount);
}
