package com.starshootercity.originsfantasy.abilities

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class InfiniteNightVision : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor("You can see in the dark after generations of evolution.", LineType.DESCRIPTION)
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Dark Eyes", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:infinite_night_vision")
    }

    private val nightVisionEffect = PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0)

    @EventHandler
    fun onServerTickEnd(event: ServerTickEndEvent) {
        if (event.tickNumber % 15 != 0) return

        Bukkit.getOnlinePlayers().forEach { player ->
            AbilityRegister.runForAbility(player, key) {
                player.addPotionEffect(nightVisionEffect)
            }
        }
    }
}
