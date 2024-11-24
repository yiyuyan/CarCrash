package cn.ksmcbrigade.ccs;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue ENABLED_MESSAGE = BUILDER.define("playerMessage",false);
    public static final ForgeConfigSpec.ConfigValue<Float> HEAL = BUILDER.define("hurtHealth",6F);
    public static final ForgeConfigSpec.ConfigValue<Float> MOVE = BUILDER.define("hurtPush",0.15F);
    public static final ForgeConfigSpec.ConfigValue<String> MESSAGE = BUILDER.define("hurtMessage","FAQ!");
    public static final ForgeConfigSpec CONFIG_SPEC = BUILDER.build();
}
