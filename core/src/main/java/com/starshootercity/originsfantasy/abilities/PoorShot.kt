package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.NMSInvoker
import net.kyori.adventure.key.Key
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent

class PoorShot : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your big hands are clumsy with a bow, so your arrows are slow and not very accurate.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Clumsy Shot", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:poor_shot")
    }

    @EventHandler
    fun onEntityShootBow(event: EntityShootBowEvent) {
        runForAbility(event.entity) {
            NMSInvoker.launchArrow(event.projectile, event.getEntity(), 0f, event.force, 2.4f)
        }
    }
}
