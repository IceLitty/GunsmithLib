package mod.chloeprime.gunsmithlib.proxies;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.loading.FMLLoader;
import org.jline.utils.Log;

public class ClientProxy {
    private static final boolean DEDICATED_SERVER = FMLLoader.getDist().isDedicatedServer();

    public static LogicalSide sideOf(Level level) {
        return level.isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER;
    }

    public static Vec3 bobCompensation(LogicalSide side, Vec3 vec) {
        if (DEDICATED_SERVER || side.isServer()) {
            return vec;
        } else {
            return ClientProxyImpl.bobCompensation(vec);
        }
    }
}
