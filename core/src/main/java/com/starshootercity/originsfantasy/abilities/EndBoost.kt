package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.Ability
import com.starshootercity.abilities.AttributeModifierAbility
import com.starshootercity.abilities.MultiAbility
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.World
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player

class EndBoost : VisibleAbility, MultiAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your natural habitat is the end, so you have more health and are stronger when you are there.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("End Inhabitant", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:end_boost")
    }

    override fun getAbilities(): MutableList<Ability> {
        return mutableListOf(EndStrength.Companion.endStrength, EndHealth.Companion.endHealth)
    }

    class EndStrength : AttributeModifierAbility {
        override fun getAttribute(): Attribute {
            return OriginsReborn.NMSInvoker.attackDamageAttribute
        }

        override fun getAmount(): Double {
            return 0.0
        }

        override fun getChangedAmount(player: Player): Double {
            return if (player.world.environment == World.Environment.THE_END) 1.6 else 0.0
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1
        }

        override fun getKey(): Key {
            return Key.key("fantasyorigins:end_strength")
        }

        companion object {
            var endStrength: EndStrength = EndStrength()
        }
    }

    class EndHealth : AttributeModifierAbility {
        override fun getKey(): Key {
            return Key.key("fantasyorigins:end_health")
        }

        override fun getAttribute(): Attribute {
            return OriginsReborn.NMSInvoker.maxHealthAttribute
        }

        override fun getAmount(): Double {
            return 0.0
        }

        override fun getChangedAmount(player: Player): Double {
            return (if (player.world.environment == World.Environment.THE_END) 20 else 0).toDouble()
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.ADD_NUMBER
        }

        companion object {
            var endHealth: EndHealth = EndHealth()
        }
    }
}
