package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.AttributeModifierAbility
import com.starshootercity.abilities.VisibleAbility
import io.papermc.paper.world.MoonPhase
import net.kyori.adventure.key.Key
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player

class MoonStrength : VisibleAbility, AttributeModifierAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You're a worshipper of the moon, so on nights with a full moon you're stronger than normal.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Moon's Blessing", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:moon_strength")
    }

    override fun getAttribute(): Attribute {
        return OriginsReborn.NMSInvoker.getAttackDamageAttribute()
    }

    override fun getAmount(): Double {
        return 0.0
    }

    override fun getChangedAmount(player: Player): Double {
        if (!player.world.isDayTime && player.world.moonPhase == MoonPhase.FULL_MOON) {
            return 2.4
        }
        return 0.0
    }

    override fun getOperation(): AttributeModifier.Operation {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1
    }
}
