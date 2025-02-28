package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.BurnInDaylight
import net.kyori.adventure.key.Key

class DaylightSensitive : BurnInDaylight() {
    override fun getKey(): Key {
        return Key.key("fantasyorigins:daylight_sensitive")
    }

    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your greatest weakness is daylight, which causes you to burst into flames.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Daylight Sensitive", LineType.TITLE)
    }

}
