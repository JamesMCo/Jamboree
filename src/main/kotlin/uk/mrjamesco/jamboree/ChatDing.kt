package uk.mrjamesco.jamboree

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import uk.mrjamesco.jamboree.Jamboree.Companion.logger

object ChatDing {
    enum class NotificationSound {
        Banjo {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_BANJO.value()
        },
        BassDrum {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM.value()
        },
        Bass {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_BASS.value()
        },
        Bell {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_BELL.value()
        },
        Bit {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_BIT.value()
        },
        Chime {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value()
        },
        CowBell {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL.value()
        },
        Didgeridoo {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO.value()
        },
        Flute {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_FLUTE.value()
        },
        Guitar {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_GUITAR.value()
        },
        Harp {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_HARP.value()
        },
        Hat {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_HAT.value()
        },
        Pling {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_PLING.value()
        },
        Snare {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_SNARE.value()
        },
        Vibraphone {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE.value()
        },
        Xylophone {
            override val sound = SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE.value()
        };

        abstract val sound: SoundEvent
    }

    fun registerListeners() {
        logger.info("Registering ChatDing listeners")
        ClientReceiveMessageEvents.CHAT.register { message, _, _, _, _ -> if (Config.ChatDing.enabled) testMessage(message.string, "CHAT") }
        ClientReceiveMessageEvents.GAME.register { message, _ -> if (Config.ChatDing.enabled) testMessage(message.string, "GAME") }
    }

    fun testMessage(message: String, messageType: String) {
        message.lowercase().let { lowercase ->
            for (candidate: String in Config.ChatDing.filters) {
                if (candidate in lowercase) {
                    logger.info("Found \"$candidate\" in $messageType message")
                    MinecraftClient.getInstance().player?.playSound(Config.ChatDing.sound, 1.0f, 1.0f)
                }
            }
        }
    }
}
