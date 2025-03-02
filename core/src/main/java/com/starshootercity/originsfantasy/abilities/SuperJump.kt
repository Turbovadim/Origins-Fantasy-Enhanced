package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key

class SuperJump : VisibleAbility {
    override fun getKey(): Key {
        return Key.key("fantasyorigins:super_jump")
    }

    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You've trained for your whole life, so can jump much higher than a regular horse.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Powerful Jump", LineType.TITLE)
    }
}
