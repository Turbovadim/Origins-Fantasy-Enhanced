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

class NaturalArmor : VisibleAbility, AttributeModifierAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your tough and strong body gives you some natural defense against attacks.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Natural Armor", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:natural_armor")
    }

    override fun getAttribute(): Attribute {
        return OriginsReborn.NMSInvoker.armorAttribute
    }

    override fun getAmount(): Double {
        return 6.0
    }

    override fun getOperation(): AttributeModifier.Operation {
        return AttributeModifier.Operation.ADD_NUMBER
    }
}
