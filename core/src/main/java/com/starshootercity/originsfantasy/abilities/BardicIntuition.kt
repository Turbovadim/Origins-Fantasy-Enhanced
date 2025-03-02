package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.Tag
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class BardicIntuition : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your musical energy will sometimes cause a creeper to drop a music disc, even without a skeleton.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Bardic Intuition", LineType.TITLE)
    }

    private val random = Random()

    override fun getKey(): Key {
        return Key.key("fantasyorigins:bardic_intuition")
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        if (entity.type != EntityType.CREEPER) return
        val killer = entity.killer ?: return
        if (random.nextDouble() > 0.25) return

        val discs = Tag.ITEMS_CREEPER_DROP_MUSIC_DISCS.getValues().toList()
        val disc = discs[random.nextInt(discs.size)]

        AbilityRegister.runForAbility(killer, key) {
            event.drops.add(ItemStack(disc))
        }
    }
}
