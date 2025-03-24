package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.NMSInvoker
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.max

class FortuneIncreaser : VisibleAbility, Listener {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your care and mastery in the art of extracting minerals results in a much higher yield from ores than other creatures.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Careful Miner", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:fortune_increaser")
    }

    private val blocks = mutableMapOf<Player, MutableList<ItemStack>>()

    @EventHandler(priority = EventPriority.LOWEST)
    fun onBlockDropItem(event: BlockDropItemEvent) {
        if (event.player.inventory.itemInMainHand.itemMeta == null) return

        runForAbility(event.player) {
            val storedItems = blocks[event.player]?.toMutableList() ?: mutableListOf()

            val droppedItems = event.items.map { it.itemStack }
            event.items.clear()

            droppedItems.forEach { dropped ->
                storedItems.forEachIndexed { index, stored ->
                    if (stored.amount > 0 && stored.type == dropped.type && stored.itemMeta == dropped.itemMeta) {
                        stored.amount = max(0, stored.amount - dropped.amount)
                        storedItems[index] = stored
                    }
                }
            }

            storedItems.addAll(droppedItems)
            storedItems.removeIf { it.amount <= 0 }

            val dropLocation = event.block.location.clone().add(0.5, 0.0, 0.5)
            storedItems.forEach { item ->
                event.items.add(event.block.world.dropItem(dropLocation, item))
            }
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val mainHandItem = event.player.inventory.itemInMainHand.clone()
        mainHandItem.itemMeta ?: return
        val fortune = NMSInvoker.getFortuneEnchantment()
        mainHandItem.addUnsafeEnchantment(fortune, mainHandItem.getEnchantmentLevel(fortune) + 2)
        blocks[event.player] = event.block.getDrops(mainHandItem, event.player).toMutableList()
    }
}
