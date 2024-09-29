package mod.chloeprime.gunsmithlib.api.util;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @see Gunsmith#getGunInfo(ItemStack)
 */
public record GunInfo(
        ItemStack gunStack,
        IGun gunItem,
        ResourceLocation gunId,
        CommonGunIndex index
) {
    public int getTotalAmmo() {
        int mag = gunItem().getCurrentAmmoCount(gunStack());
        int barrel = index().getGunData().getBolt() == Bolt.OPEN_BOLT
                ? 0
                : gunItem().hasBulletInBarrel(gunStack()) ? 1 : 0;
        return mag + barrel;
    }

    public int getTotalMagazineSize() {
        int mag = AttachmentDataUtils.getAmmoCountWithAttachment(gunStack(), index().getGunData());
        int barrel = index().getGunData().getBolt() == Bolt.OPEN_BOLT
                ? 0
                : 1;
        return mag + barrel;
    }

    public int getDummyAmmoAmount() {
        return gunItem().getDummyAmmoAmount(gunStack());
    }

    public void setDummyAmmoAmount(int amount) {
        gunItem().setDummyAmmoAmount(gunStack(), amount);
    }

    public void addDummyAmmoAmount(int amount) {
        gunItem().addDummyAmmoAmount(gunStack(), amount);
    }

    public FireMode getFireMode() {
        return gunItem().getFireMode(gunStack());
    }

    public void setFireMode(@Nullable FireMode fireMode) {
        gunItem().setFireMode(gunStack(), fireMode);
    }
}
