package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.abilities.AttributeModifierAbility
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.NMSInvoker
import net.kyori.adventure.key.Key
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier

class LargeBody : VisibleAbility, AttributeModifierAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor("You're three blocks tall, taller than a regular human.", LineType.DESCRIPTION)
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Large Body", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:large_body")
    }

    override fun getAttribute(): Attribute {
        return NMSInvoker.getGenericScaleAttribute()
    }

    override fun getAmount(): Double {
        return 0.5
    }

    override fun getOperation(): AttributeModifier.Operation {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1
    }
}
