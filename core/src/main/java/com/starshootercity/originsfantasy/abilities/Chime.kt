package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Chime : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You can absorb the chime of amethyst shards to regenerate health.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Chime", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:chime")
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!event.action.isRightClick) return
        val item = event.item ?: return
        if (item.type != Material.AMETHYST_SHARD) return

        val player = event.player
        runForAbility(player) {
            item.amount--
            player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 900, 1))
            event.hand?.let { hand ->
                when (hand) {
                    EquipmentSlot.HAND -> player.swingMainHand()
                    else -> player.swingOffHand()
                }
            }
        }
    }
}
