package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.NotePlayEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class NoteBlockPower : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You gain strength and speed when a nearby Note Block is played.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Musically Attuned", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:note_block_power")
    }

    @EventHandler
    fun onNotePlay(event: NotePlayEvent) {
        event.block.location.getNearbyEntities(32.0, 32.0, 32.0)
            .filterIsInstance<Player>()
            .forEach { player ->
                AbilityRegister.runForAbility(player, key) {
                    player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 600, 1))
                    player.addPotionEffect(PotionEffect(OriginsReborn.NMSInvoker.strengthEffect, 600, 1))
                }
            }
    }
}
