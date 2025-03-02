package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key

class IncreasedSpeed : VisibleAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "From years of training for race after race, you're much faster than any normal horse.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Dashmaster", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:increased_speed")
    }
}
