package uk.mrjamesco.jamboree

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Jamboree: ModInitializer {
    override fun onInitialize() {
        logger.info("Initialising Jamboree v${FabricLoader.getInstance().getModContainer("jamboree").get().metadata.version}")
        Config.init()
        ChatDing.registerListeners()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger("Jamboree")
    }
}
