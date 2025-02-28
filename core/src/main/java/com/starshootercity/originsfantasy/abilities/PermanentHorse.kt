package com.starshootercity.originsfantasy.abilities

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import com.starshootercity.OriginSwapper
import com.starshootercity.OriginSwapper.BooleanPDT
import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.AbilityRegister
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.events.PlayerSwapOriginEvent
import com.starshootercity.originsfantasy.FantasyEntityDismountEvent
import com.starshootercity.originsfantasy.FantasyEntityMountEvent
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.NMSInvoker
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.getInstance
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.Horse
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class PermanentHorse : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor("You are half horse, half human.", LineType.DESCRIPTION)
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Half Horse", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:permanent_horse")
    }

    @EventHandler
    fun onEntityDismount(event: FantasyEntityDismountEvent) {
        if (event.getEntity().isDead) return
        AbilityRegister.runForAbility(event.getEntity(), key, Runnable {
            event.setCancelled(true)
            val vehicle = event.dismounted.vehicle
            vehicle?.removePassenger(event.dismounted)
        })
    }

    @EventHandler
    fun onPlayerTeleport(event: PlayerTeleportEvent) {
        val player = event.player
        if (player.persistentDataContainer.has(teleportingKey)) return

        AbilityRegister.runForAbility(player, key) {
            player.vehicle?.let { vehicle ->
                vehicle.removePassenger(player)
                player.persistentDataContainer.set(teleportingKey, PersistentDataType.BOOLEAN, true)
                event.isCancelled = true

                Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance()) {
                    player.teleport(event.to, event.cause)
                    vehicle.teleport(event.to, event.cause)
                    vehicle.addPassenger(player)
                    player.persistentDataContainer.remove(teleportingKey)
                }
            }
        }
    }

    @EventHandler
    fun onEntityMount(event: FantasyEntityMountEvent) {
        val entity = event.entity
        AbilityRegister.runForAbility(entity, key) {
            val mountOwner = event.mount.persistentDataContainer.getOrDefault(key, PersistentDataType.STRING, "")
            if (mountOwner != entity.uniqueId.toString()) {
                event.isCancelled = true
                if (event.mount !is LivingEntity) {
                    entity.vehicle?.let { vehicle ->
                        event.mount.addPassenger(vehicle)
                    }
                }
            }
        }
    }

    private val key = NamespacedKey(getInstance(), "mount-key")
    private val teleportingKey = NamespacedKey(getInstance(), "teleporting")

    @EventHandler
    fun onServerTickEnd(event: ServerTickEndEvent) {
        if (event.tickNumber % 20 != 0) return

        Bukkit.getOnlinePlayers()
            .filter { !it.isDead }
            .forEach { player ->
                AbilityRegister.runForAbility(player, key) {
                    if (player.persistentDataContainer.has(teleportingKey) || player.vehicle != null) return@runForAbility

                    val horse = player.world.spawnEntity(player.location, EntityType.HORSE) as Horse
                    val jumpAttr = horse.getAttribute(NMSInvoker.getGenericJumpStrengthAttribute())
                    val speedAttr = horse.getAttribute(OriginsReborn.NMSInvoker.movementSpeedAttribute)

                    OriginSwapper.getOrigin(player, "origin")?.let { origin ->
                        if (origin.hasAbility(Key.key("fantasyorigins:super_jump"))) {
                            jumpAttr?.baseValue = 1.0
                        }
                        if (origin.hasAbility(Key.key("fantasyorigins:increased_speed"))) {
                            speedAttr?.baseValue = 0.4
                        }
                    }

                    horse.persistentDataContainer.set(key, PersistentDataType.STRING, player.uniqueId.toString())
                    horse.isTamed = true
                    horse.style = Horse.Style.NONE

                    val saddle = ItemStack(Material.SADDLE).apply {
                        itemMeta = itemMeta?.apply {
                            persistentDataContainer.set(key, BooleanPDT.BOOLEAN, true)
                        }
                    }
                    horse.inventory.saddle = saddle
                    horse.addPassenger(player)
                }
            }
    }


    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.getCurrentItem() == null) return
        if (event.getCurrentItem()!!.itemMeta == null) return
        if (event.getCurrentItem()!!.itemMeta.persistentDataContainer.has(key)) event.isCancelled = true
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        AbilityRegister.runForAbility(event.getEntity(), key) {
            val vehicle = event.getEntity().vehicle
            if (vehicle != null && vehicle.persistentDataContainer.has(key)) vehicle.remove()
        }
    }

    @EventHandler
    fun onPlayerSwapOrigin(event: PlayerSwapOriginEvent) {
        val vehicle = event.getPlayer().vehicle
        if (vehicle != null && vehicle.persistentDataContainer.has(key)) vehicle.remove()
    }

    @EventHandler
    fun onPlayerBedEnter(event: PlayerBedEnterEvent) {
        AbilityRegister.runForAbility(event.getPlayer(), key) {
            event.setUseBed(Event.Result.DENY)
        }
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        if (event.getEntity().persistentDataContainer.has(key)) {
            for (entity in event.getEntity().passengers) {
                if (entity is LivingEntity) {
                    NMSInvoker.transferDamageEvent(entity, event)
                }
            }
            event.isCancelled = true
        }
    }
}
