package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.getInstance
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent

class IncreasedArrowSpeed : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Arrows you shoot are twice as fast than ones shot by a regular human.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Masterful Speed", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:increased_arrow_speed")
    }

    @EventHandler
    fun onEntityShootBow(event: EntityShootBowEvent) {
        val v = getInstance().getConfig().getInt("arrow-speed-multiplier", 2)
        AbilityRegister.runForAbility(
            event.getEntity(),
            key,
            Runnable {
                Bukkit.getScheduler().scheduleSyncDelayedTask(
                    getInstance(),
                    Runnable { event.projectile.velocity = event.projectile.velocity.multiply(v) })
            })
    }
}
