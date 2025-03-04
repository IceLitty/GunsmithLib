package mod.chloeprime.gunsmithlib;

import com.mojang.logging.LogUtils;
import mod.chloeprime.gunsmithlib.api.common.GunAttributes;
import mod.chloeprime.gunsmithlib.common.util.AttackDamageMobEffect;
import mod.chloeprime.gunsmithlib.common.util.PercentBasedAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;

import java.awt.*;
import java.util.function.Consumer;

@Mod(GunsmithLib.MOD_ID)
public class GunsmithLib {

    public static final String MOD_ID = "gunsmithlib";
    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public GunsmithLib(net.neoforged.fml.ModContainer modContainer, IEventBus bus) {

        Attributes.REGISTRY.register(bus);
        MobEffects.REGISTRY.register(bus);
        bus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static class Attributes {
        private static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MOD_ID);
        public static final DeferredHolder<Attribute, RangedAttribute> BULLET_DAMAGE = create("bullet_damage", 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        public static final DeferredHolder<Attribute, RangedAttribute> BULLET_SPEED = create("bullet_speed", 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        public static final DeferredHolder<Attribute, PercentBasedAttribute> H_RECOIL = REGISTRY.register("horz_recoil",
                () -> {
                    PercentBasedAttribute attr = new PercentBasedAttribute(
                            createLangKey("horz_recoil"),
                            1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY
                    );
                    attr.setSyncable(true);
                    return attr;
                }
        );

        public static final DeferredHolder<Attribute, PercentBasedAttribute> V_RECOIL = REGISTRY.register("vert_recoil",
                () -> {
                    PercentBasedAttribute attr = new PercentBasedAttribute(
                            createLangKey("vert_recoil"),
                            1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY
                    );
                    attr.setSyncable(true);
                    return attr;
                }
        );


        @SuppressWarnings("SameParameterValue")
        private static DeferredHolder<Attribute, RangedAttribute> create(String name, double defaultValue, double min, double max) {
            return create(name, defaultValue, min, max, _a -> {});
        }

        private static DeferredHolder<Attribute, RangedAttribute> create(String name, double defaultValue, double min, double max, Consumer<Attribute> customizer) {
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

    public static class MobEffects {
        private static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MOD_ID);
        public static final DeferredHolder<MobEffect, MobEffect> GUN_DAMAGE = REGISTRY.register("crossfire", () -> new AttackDamageMobEffect(MobEffectCategory.BENEFICIAL, Color.LIGHT_GRAY, Config.CROSSFIRE_BUFF_POWER::get)
                .addAttributeModifier(GunAttributes.BULLET_DAMAGE, ResourceLocation.fromNamespaceAndPath(MOD_ID, "crossfire"), 0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
}
