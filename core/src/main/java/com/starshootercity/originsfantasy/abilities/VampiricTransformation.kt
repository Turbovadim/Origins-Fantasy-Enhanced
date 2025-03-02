package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper
import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.events.PlayerSwapOriginEvent.SwapReason
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.getInstance
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import java.util.*

class VampiricTransformation : VisibleAbility, Listener {
    override fun getKey(): Key {
        return Key.key("fantasyorigins:vampiric_transformation")
    }

    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            if (getInstance().getConfig()
                    .getDouble("vampire-transform-chance", 1.0) >= 1
            ) "You can transform other players into vampires by killing them." else "You sometimes transform other players into vampires by killing them.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Vampiric Transformation", LineType.TITLE)
    }

    private val random = Random()

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val deceased = event.entity
        if (deceased !is Player) return
        val killer = deceased.killer ?: return
        AbilityRegister.runForAbility(killer, key) {
            val chance = getInstance().config.getDouble("vampire-transform-chance", 1.0)
            if (random.nextDouble() <= chance) {
                OriginSwapper.setOrigin(
                    killer,
                    OriginSwapper.getOrigin(killer, "origin"),
                    SwapReason.DIED,
                    false,
                    "origin"
                )
                killer.sendMessage(
                    Component.text("You have transformed into a Vampire!").color(NamedTextColor.RED)
                )
            }
        }
    }
}
