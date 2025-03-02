package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.AttributeModifierAbility
import com.starshootercity.abilities.MultiAbility
import com.starshootercity.abilities.VisibleAbility
import net.kyori.adventure.key.Key
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player

class OceansGrace : VisibleAbility, MultiAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "You are a part of the water, so you have extra health and deal extra damage when in water or rain.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Ocean's Grace", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:oceans_grace")
    }

    override fun getAbilities() = mutableListOf(WaterStrength.Companion.waterStrength, WaterHealth.Companion.waterHealth)

    class WaterHealth : AttributeModifierAbility {
        override fun getAttribute(): Attribute {
            return OriginsReborn.NMSInvoker.maxHealthAttribute
        }

        override fun getAmount(): Double {
            return 0.0
        }

        override fun getChangedAmount(player: Player): Double {
            if (player.isInWaterOrRainOrBubbleColumn) {
                return 4.0
            }
            return 0.0
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.ADD_NUMBER
        }

        override fun getKey(): Key {
            return Key.key("fantasyorigins:water_health")
        }

        companion object {
            var waterHealth: WaterHealth = WaterHealth()
        }
    }

    class WaterStrength : AttributeModifierAbility {
        override fun getKey(): Key {
            return Key.key("fantasyorigins:water_strength")
        }

        override fun getAttribute(): Attribute {
            return OriginsReborn.NMSInvoker.attackDamageAttribute
        }

        override fun getAmount(): Double {
            return 0.0
        }

        override fun getChangedAmount(player: Player): Double {
            if (player.isInWaterOrRainOrBubbleColumn) {
                return 1.4
            }
            return 0.0
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1
        }

        companion object {
            var waterStrength: WaterStrength = WaterStrength()
        }
    }
}
