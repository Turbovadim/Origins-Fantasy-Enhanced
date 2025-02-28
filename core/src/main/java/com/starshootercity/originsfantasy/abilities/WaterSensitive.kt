package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.WaterVulnerability
import net.kyori.adventure.key.Key

class WaterSensitive : WaterVulnerability() {
    override fun getKey(): Key {
        return Key.key("fantasyorigins:water_sensitive")
    }

    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor("You are hurt by water as its current drains your power.", LineType.DESCRIPTION)
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Water Sensitive", LineType.TITLE)
    }

}
