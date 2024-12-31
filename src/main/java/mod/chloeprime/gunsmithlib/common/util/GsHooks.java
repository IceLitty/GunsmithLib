package mod.chloeprime.gunsmithlib.common.util;

import com.tacz.guns.api.item.IGun;
import mod.chloeprime.gunsmithlib.api.common.GunReloadFeedEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class GsHooks {
    public static void onReloadFeed(IGun gun, LivingEntity shooter, ItemStack gunItem, boolean loadBarrel, Runnable canceller) {
        var gunInfo = GsHelper.unpack(gun, gunItem);
        var preEvent = gunInfo.map(gi -> new GunReloadFeedEvent.Pre(shooter, gi, loadBarrel));
        var canceled = preEvent.filter(MinecraftForge.EVENT_BUS::post).isPresent();
        if (canceled) {
            canceller.run();
            return;
        }

        var postEvent = gunInfo.map(gi -> new GunReloadFeedEvent.Post(shooter, gi, loadBarrel));
        postEvent.ifPresent(MinecraftForge.EVENT_BUS::post);
    }
}
