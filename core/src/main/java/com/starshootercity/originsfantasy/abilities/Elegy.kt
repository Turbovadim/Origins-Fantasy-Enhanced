package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.AttributeModifierAbility
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player

class Elegy : VisibleAbility, AttributeModifierAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor("You become stronger when at less than 3 hearts.", LineType.DESCRIPTION)
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Elegy", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:elegy")
    }

    override fun getAttribute(): Attribute {
        return OriginsReborn.NMSInvoker.attackDamageAttribute
    }

    override fun getAmount(): Double {
        return 0.0
    }

    override fun getOperation(): AttributeModifier.Operation {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1
    }

    override fun getChangedAmount(player: Player): Double {
        return (if (player.health <= 6) 2 else 0).toDouble()
    }
}
