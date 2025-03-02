package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.NMSInvoker
import net.kyori.adventure.key.Key
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent

class PerfectShot : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You've trained with a bow for many years, and you shoot your arrows perfectly straight.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Skilled Archer", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:perfect_shot")
    }

    @EventHandler
    fun onEntityShootBow(event: EntityShootBowEvent) {
        AbilityRegister.runForAbility(
            event.getEntity(),
            key
        ) {
            NMSInvoker.launchArrow(event.projectile, event.getEntity(), 0f, event.force, 0f)
        }
    }
}
