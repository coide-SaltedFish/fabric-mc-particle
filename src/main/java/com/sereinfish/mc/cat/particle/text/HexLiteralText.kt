package com.sereinfish.mc.cat.particle.text

import net.minecraft.text.*
import net.minecraft.util.Formatting
import java.awt.Color

class HexLiteralText(string: String) {
    private val text = MutableText.of(LiteralTextContent(string))
    fun formatted(hex: String?): MutableText {
        hex?.let {
            if (hex.matches("#[0-9a-fA-f]{6}".toRegex())) {
                text.style = text.style.withColor(Color.decode(hex).rgb)
            }else if (Formatting.byName(hex) != null) {
                text.style = text.style.withFormatting(Formatting.byName(hex))
            }

        } ?: kotlin.run {
            text.style = text.style.withFormatting(Formatting.WHITE)
        }
        return text
    }
}