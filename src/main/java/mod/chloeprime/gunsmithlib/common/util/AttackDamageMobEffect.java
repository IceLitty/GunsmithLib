package mod.chloeprime.gunsmithlib.common.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.DoubleSupplier;

public class AttackDamageMobEffect extends MobEffect {
    protected final DoubleSupplier multiplier;

    public AttackDamageMobEffect(MobEffectCategory category, Color color, DoubleSupplier multiplier) {
        super(category, color.getRGB());
        this.multiplier = multiplier;
    }

    public double getAttributeModifierValue(int amplifier, @NotNull AttributeModifier modifier) {
        return this.multiplier.getAsDouble() * (amplifier + 1);
    }
}