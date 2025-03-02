package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import kotlin.math.min

class Leeching : VisibleAbility, Listener {
    override fun getKey(): Key {
        return Key.key("fantasyorigins:leeching")
    }

    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Upon killing a mob or player, you sap a portion of its health, healing you.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Leeching", LineType.TITLE)
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val killer = event.entity.killer ?: return
        AbilityRegister.runForAbility(killer, key) {
            val killerMaxHealth = killer.getAttribute(OriginsReborn.NMSInvoker.maxHealthAttribute)?.value ?: return@runForAbility
            val mobMaxHealth = event.entity.getAttribute(OriginsReborn.NMSInvoker.maxHealthAttribute)?.value ?: return@runForAbility
            killer.health = min(killerMaxHealth, killer.health + (mobMaxHealth / 5))
        }
    }
}
