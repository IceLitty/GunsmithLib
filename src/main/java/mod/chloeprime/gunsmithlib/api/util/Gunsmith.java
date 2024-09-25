package mod.chloeprime.gunsmithlib.api.util;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class Gunsmith {
    public static Optional<GunDescriptor> getGunInfo(ItemStack gun) {
        var kun = IGun.getIGunOrNull(gun);
        if (kun == null) {
            return Optional.empty();
        }
        var gunId = kun.getGunId(gun);
        return TimelessAPI.getCommonGunIndex(gunId).map(index -> new GunDescriptor(gun, kun, gunId, index));
    }
}
