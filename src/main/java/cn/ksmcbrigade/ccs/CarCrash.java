package cn.ksmcbrigade.ccs;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ccs")
public class CarCrash {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public CarCrash() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,Config.CONFIG_SPEC);
        LOGGER.info("CarCrash mod loaded.");
    }


}
