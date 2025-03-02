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
import org.bukkit.event.entity.EntityPotionEffectEvent
import org.bukkit.potion.PotionEffectType

class MagicResistance : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor("You have an immunity to poison and harming potion effects.", LineType.DESCRIPTION)
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Iron Stomach", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:magic_resistance")
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        AbilityRegister.runForAbility(event.entity, key) {
            if (event.cause == EntityDamageEvent.DamageCause.MAGIC) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onEntityPotionEffect(event: EntityPotionEffectEvent) {
        event.newEffect ?: return
        AbilityRegister.runForAbility(event.entity, key) {
            if (event.newEffect?.type == PotionEffectType.POISON) {
                event.isCancelled = true
            }
        }
    }
}
