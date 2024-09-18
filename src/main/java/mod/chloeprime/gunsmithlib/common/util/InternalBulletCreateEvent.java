package mod.chloeprime.gunsmithlib.common.util;

import mod.chloeprime.gunsmithlib.api.common.BulletCreateEvent;
import net.minecraftforge.eventbus.api.Event;

public final class InternalBulletCreateEvent extends Event {
    public InternalBulletCreateEvent(BulletCreateEvent impl) {
        this.impl = impl;
    }

    public BulletCreateEvent getImpl() {
        return impl;
    }

    private final BulletCreateEvent impl;
}
