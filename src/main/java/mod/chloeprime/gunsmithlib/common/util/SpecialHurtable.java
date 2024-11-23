package mod.chloeprime.gunsmithlib.common.util;

import mod.chloeprime.gunsmithlib.GunsmithLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashSet;
import java.util.Set;

public interface SpecialHurtable {
    Set<EntityType<?>> UNSUPPORTED_TYPES = new LinkedHashSet<>();

    TagKey<EntityType<?>> USE_SPECIAL_HURT = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(GunsmithLib.MOD_ID, "use_special_hurt"));

    default void gunsmith$addUnsupportedTypeForSpecialHurt(EntityType<?> type, Throwable cause) {
        UNSUPPORTED_TYPES.add(type);
        if (GunsmithLib.LOGGER.isWarnEnabled()) {
            GunsmithLib.LOGGER.warn("Tag %s is unsupported on entity type %s because of %s".formatted(
                    USE_SPECIAL_HURT.location(),
                    ForgeRegistries.ENTITY_TYPES.getKey(type),
                    cause.getClass().getSimpleName()
            ), cause);
        }
    }
    boolean gunsmith$usingSpecialHurt();
    void gunsmith$beginSpecialHurt();
    void gunsmith$endSpecialHurt();
    HurtFunction1 getSpecialHurtFunction1();
    HurtFunction2 getSpecialHurtFunction2();
    FloatConsumer getSpecialSetHealthFunction();
}
