package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class StrongSkin : VisibleAbility, Listener {
    override fun getKey(): Key {
        return Key.key("fantasyorigins:strong_skin")
    }

    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You've got naturally thicker skin than regular humans, so arrows do a lot less damage.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Thick Skin", LineType.TITLE)
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        if (event.cause == EntityDamageEvent.DamageCause.PROJECTILE) {
            AbilityRegister.runForAbility(
                event.getEntity(),
                key
            ) {
                event.setDamage(event.damage / 4)
            }
        }
    }
}
