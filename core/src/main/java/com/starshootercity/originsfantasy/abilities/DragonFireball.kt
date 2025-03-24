package com.starshootercity.originsfantasy.abilities

import com.destroystokyo.paper.MaterialTags
import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.getInstance
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.entity.DragonFireball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class DragonFireball : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You can right click whilst holding a sword to launch a dragon's fireball, with a cooldown of 30 seconds.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Dragon's Fireball", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:dragon_fireball")
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        runForAbility(player) {
            if (!event.action.isRightClick) return@runForAbility
            if (event.clickedBlock != null) return@runForAbility
            if (item == null) return@runForAbility
            if (!MaterialTags.SWORDS.isTagged(item.type)) return@runForAbility
            if (player.getCooldown(item.type) > 0) return@runForAbility

            MaterialTags.SWORDS.values.forEach { player.setCooldown(it, 600) }

            val fireball = player.launchProjectile<DragonFireball>(DragonFireball::class.java)
            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance()) {
                fireball.velocity = player.location.direction.multiply(1.2)
            }
        }
    }
}
