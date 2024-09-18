package mod.chloeprime.gunsmithlib;

import com.mojang.logging.LogUtils;
import mod.chloeprime.gunsmithlib.common.util.PercentBasedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.function.Consumer;

@Mod(GunsmithLib.MOD_ID)
public class GunsmithLib {

    public static final String MOD_ID = "gunsmithlib";

    private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public GunsmithLib() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        Attributes.REGISTRY.register(bus);
        bus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static class Attributes {
        private static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MOD_ID);
        public static final RegistryObject<Attribute> BULLET_DAMAGE = create("bullet_damage", 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        public static final RegistryObject<Attribute> BULLET_SPEED = create("bullet_speed", 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        public static final RegistryObject<Attribute> H_RECOIL = REGISTRY.register("horz_recoil",
                () -> new PercentBasedAttribute(
                        createLangKey("horz_recoil"),
                        1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY
                ).setSyncable(true)
        );

        public static final RegistryObject<Attribute> V_RECOIL = REGISTRY.register("vert_recoil",
                () -> new PercentBasedAttribute(
                        createLangKey("vert_recoil"),
                        1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY
                ).setSyncable(true)
        );


        @SuppressWarnings("SameParameterValue")
        private static RegistryObject<Attribute> create(String name, double defaultValue, double min, double max) {
            return create(name, defaultValue, min, max, _a -> {});
        }

        private static RegistryObject<Attribute> create(String name, double defaultValue, double min, double max, Consumer<Attribute> customizer) {
            return REGISTRY.register(name, () -> {
                var attribute = new RangedAttribute(createLangKey(name), defaultValue, min, max);
                customizer.accept(attribute);
                return attribute;
            });
        }

        private static String createLangKey(String name) {
            return "attribute.name.%s.%s".formatted(MOD_ID, name);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
}
