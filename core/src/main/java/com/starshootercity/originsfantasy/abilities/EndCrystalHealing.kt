package com.starshootercity.originsfantasy.abilities

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.Ability.AsyncAbilityRunner
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.entity.EnderCrystal
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.endera.enderalib.utils.async.ioDispatcher
import kotlin.math.min

class EndCrystalHealing : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor("You can regenerate health from nearby End Crystals.", LineType.DESCRIPTION)
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Crystal Healer", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:end_crystal_healing")
    }

    companion object {
        const val SEARCH_RADIUS = 48.0
        const val MAX_DISTANCE_SQ = 144.0
    }

    @EventHandler
    fun onServerTickEnd(event: ServerTickEndEvent) {
        if (event.tickNumber % 5 != 0) return
        CoroutineScope(ioDispatcher).launch {
            Bukkit.getOnlinePlayers().toList().forEach { player ->
                val playerLoc = player.location
                runForAbilityAsync(
                    player,
                    AsyncAbilityRunner {
                        withContext(OriginsFantasy.bukkitDispatcher) {
                            player.getNearbyEntities(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS)
                                .filterIsInstance<EnderCrystal>()
                                .filter { it.location.distanceSquared(playerLoc) <= MAX_DISTANCE_SQ }
                                .forEach { crystal ->
                                    crystal.beamTarget = playerLoc.clone().apply { y -= 1.0 }
                                    player.health = min(20.0, player.health + 1)
                                }
                        }
                    },
                    AsyncAbilityRunner {
                        withContext(OriginsFantasy.bukkitDispatcher) {
                            player.getNearbyEntities(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS)
                                .filterIsInstance<EnderCrystal>()
                                .forEach { it.beamTarget = null }
                        }
                    }
                )
            }
        }
    }


}
