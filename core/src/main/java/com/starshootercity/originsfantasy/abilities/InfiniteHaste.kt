package com.starshootercity.originsfantasy.abilities

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect

class InfiniteHaste : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You're well trained in mining, so are much faster than a regular human.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Fast Miner", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:infinite_haste")
    }

    @EventHandler
    fun onServerTickEnd(event: ServerTickEndEvent) {
        if (event.tickNumber % 20 != 0) return

        val hasteEffectType = OriginsReborn.NMSInvoker.hasteEffect
        Bukkit.getOnlinePlayers().forEach { player ->
            runForAbility(player) {
                player.addPotionEffect(PotionEffect(hasteEffectType, 30, 1))
            }
        }
    }

}
