package mod.chloeprime.gunsmithlib.common.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class DamageUtils {
    public static double getAttributeValueWithBase(LivingEntity holder, Attribute attribute, double newBase) {
        var instance = holder.getAttribute(attribute);
        if (instance == null) {
            return newBase;
        }

        var oldBase = instance.getBaseValue();
        try {
            instance.setBaseValue(newBase);
            return instance.getValue();
        } finally {
            instance.setBaseValue(oldBase);
        }
    }

    private DamageUtils() {}
}
