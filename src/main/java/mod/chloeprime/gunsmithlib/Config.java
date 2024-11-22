package mod.chloeprime.gunsmithlib;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.DoubleValue CROSSFIRE_BUFF_POWER = BUILDER
            .comment("Amount of damage scale boost per level of Crossfire buff gives")
            .defineInRange("crossfire_buff_power", 0.3, Double.MIN_NORMAL, Double.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();
}
