package mod.chloeprime.gunsmithlib;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue CROSSFIRE_BUFF_POWER = BUILDER
            .comment("Amount of damage scale boost per level of Crossfire buff gives")
            .defineInRange("crossfire_buff_power", 0.3, Double.MIN_NORMAL, Double.MAX_VALUE);

    public static final ModConfigSpec.BooleanValue ENABLE_SPECIAL_HURT = BUILDER
            .comment("Enable calling hurt function with invokespecial on entities with a specific tag")
            .define("enable_special_hurt", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}
