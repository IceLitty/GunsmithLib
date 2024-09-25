package mod.chloeprime.gunsmithlib.api.util;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.resource.index.CommonGunIndex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record GunDescriptor(
        ItemStack gunStack,
        IGun gunItem,
        ResourceLocation gunId,
        CommonGunIndex index
) {
}
