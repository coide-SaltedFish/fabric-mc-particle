package com.sereinfish.mc.cat.particle;

import com.sereinfish.mc.cat.particle.command.ModCommand;
import com.sereinfish.mc.cat.particle.event.ModEvents;
import com.sereinfish.mc.cat.particle.untils.Utils;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Start implements ModInitializer {
    public static final Logger logger = LogManager.getLogger("SereinFishParticleMod");

    @Override
    public void onInitialize() {
        Utils.init();
        ModCommand.init();
        ModEvents.init();
        ModEvents.botInit();

        logger.info("SereinFish Particle Mod init! - " + Utils.version);
    }
}
