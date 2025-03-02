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

class DoubleHealth : VisibleAbility, AttributeModifierAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "As you're larger than humans, you have more health as your body protects you from damage.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Double Health", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:double_health")
    }

    override fun getAttribute(): Attribute {
        return OriginsReborn.NMSInvoker.maxHealthAttribute
    }

    override fun getAmount(): Double {
        return 20.0
    }

    override fun getOperation(): AttributeModifier.Operation {
        return AttributeModifier.Operation.ADD_NUMBER
    }
}
