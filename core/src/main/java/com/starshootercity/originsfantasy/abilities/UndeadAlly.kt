package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import io.papermc.paper.tag.EntityTags
import net.kyori.adventure.key.Key
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityTargetLivingEntityEvent

class UndeadAlly : VisibleAbility, Listener {
    override fun getKey(): Key {
        return Key.key("fantasyorigins:undead_ally")
    }

    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "As an undead monster, other undead creatures will not attack you unprovoked.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Undead Ally", LineType.TITLE)
    }

    @EventHandler
    fun onEntityTargetLivingEntity(event: EntityTargetLivingEntityEvent) {
        if (!EntityTags.UNDEADS.isTagged(event.entityType)) return
        val target = event.target as? Player ?: return
        AbilityRegister.runForAbility(target, key) {
            val attacked = attackedEntities.getOrPut(target) { mutableListOf() }
            if (!attacked.contains(event.entity)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        val player = when (val damager = event.damager) {
            is Player -> damager
            is Projectile -> damager.shooter as? Player ?: return
            else -> return
        }
        attackedEntities.getOrPut(player) { mutableListOf() }.add(event.entity)
    }
    private val attackedEntities = mutableMapOf<Player, MutableList<Entity>>()
}
