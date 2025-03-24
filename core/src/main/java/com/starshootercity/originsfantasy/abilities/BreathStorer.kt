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
import org.bukkit.inventory.ItemStack

class BreathStorer : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "By right clicking using an empty bottle, you can store your own Dragon's Breath.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Dragon's Breath", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:breath_storer")
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return
        if (!event.action.isRightClick) return

        runForAbility(player) {
            if (item.type == Material.GLASS_BOTTLE) {
                item.amount--
                player.inventory.addItem(ItemStack(Material.DRAGON_BREATH))
                    .values.forEach { leftover ->
                        player.world.dropItemNaturally(player.location, leftover)
                    }
            }
        }
    }
}
