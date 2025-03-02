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

class Stronger : VisibleAbility, AttributeModifierAbility {
    override fun getKey(): Key {
        return Key.key("fantasyorigins:stronger")
    }

    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your vampiric nature makes you stronger than a regular human, making your physical attacks deal far more damage.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Stronger", LineType.TITLE)
    }

    override fun getAttribute(): Attribute {
        return OriginsReborn.NMSInvoker.attackDamageAttribute
    }

    override fun getAmount(): Double {
        return 1.8
    }

    override fun getOperation(): AttributeModifier.Operation {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1
    }
}
