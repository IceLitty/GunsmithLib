package mod.chloeprime.gunsmithlib.common.util;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import mod.chloeprime.gunsmithlib.api.util.GunInfo;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class GsHelper {
    public static Optional<GunInfo> unpack(IGun gunItem, ItemStack gunStack) {
        var gunId = gunItem.getGunId(gunStack);
        return TimelessAPI.getCommonGunIndex(gunId).map(index -> new GunInfo(gunStack, gunItem, gunId, index));
    }

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

    private GsHelper() {}
}
