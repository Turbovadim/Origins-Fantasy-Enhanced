package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.BooleanPDT
import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.getInstance
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent

class IncreasedArrowDamage : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor("All arrows you shoot deal increased damage.", LineType.DESCRIPTION)
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Piercing Shot", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:increased_arrow_damage")
    }

    private val key = NamespacedKey(getInstance(), "increased-arrow-damage-key")

    @EventHandler
    fun onEntityShootBow(event: EntityShootBowEvent) {
        AbilityRegister.runForAbility(event.entity, key) {
            event.projectile.persistentDataContainer.set(key, BooleanPDT.BOOLEAN, true)
        }
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.damager.persistentDataContainer.has(key)) {
            val extraDamage = getInstance().config.getInt("arrow-damage-increase", 3)
            event.damage += extraDamage
        }
    }
}
