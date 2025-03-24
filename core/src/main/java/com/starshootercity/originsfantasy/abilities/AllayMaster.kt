package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.NMSInvoker
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.entity.Allay
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot

class AllayMaster : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your musical aura allows you to breed allays without playing music.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Allay Master", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:allay_master")
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val player = event.player
        runForAbility(player) {
            val allay = event.rightClicked as? Allay ?: return@runForAbility
            val item = player.inventory.getItem(event.hand)
            if (item.type != Material.AMETHYST_SHARD) return@runForAbility
            if (!NMSInvoker.duplicateAllay(allay)) return@runForAbility

            event.isCancelled = true
            item.amount--
            when (event.hand) {
                EquipmentSlot.HAND -> player.swingMainHand()
                else -> player.swingOffHand()
            }
            player.inventory.setItem(event.hand, item)
        }
    }
}
