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

class OceanWish : VisibleAbility, MultiAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your natural habitat is the ocean, so you're much weaker when you're not in the water.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Ocean Wish", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:ocean_wish")
    }

    override fun getAbilities() = mutableListOf(
            LandSlowness.Companion.landSlowness,
            LandHealth.Companion.landHealth,
            LandWeakness.Companion.landWeakness
        )

    class LandWeakness : AttributeModifierAbility {
        override fun getAttribute(): Attribute {
            return OriginsReborn.NMSInvoker.attackDamageAttribute
        }

        override fun getAmount(): Double {
            return 0.0
        }

        override fun getChangedAmount(player: Player?): Double {
            return -0.4
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1
        }

        override fun getKey(): Key {
            return Key.key("fantasyorigins:land_weakness")
        }

        companion object {
            var landWeakness: LandWeakness = LandWeakness()
        }
    }

    class LandHealth : AttributeModifierAbility {
        override fun getKey(): Key {
            return Key.key("fantasyorigins:land_health")
        }

        override fun getAttribute(): Attribute {
            return OriginsReborn.NMSInvoker.maxHealthAttribute
        }

        override fun getAmount(): Double {
            return 0.0
        }

        override fun getChangedAmount(player: Player): Double {
            if (player.isInWaterOrRainOrBubbleColumn) {
                return 0.0
            }
            return -12.0
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.ADD_NUMBER
        }

        companion object {
            var landHealth: LandHealth = LandHealth()
        }
    }

    class LandSlowness : AttributeModifierAbility {
        override fun getKey(): Key {
            return Key.key("fantasyorigins:land_slowness")
        }

        override fun getAttribute(): Attribute {
            return OriginsReborn.NMSInvoker.movementSpeedAttribute
        }

        override fun getAmount(): Double {
            return 0.0
        }

        override fun getChangedAmount(player: Player): Double {
            if (player.isInWaterOrRainOrBubbleColumn) {
                return 0.0
            }
            return -0.4
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1
        }

        companion object {
            var landSlowness: LandSlowness = LandSlowness()
        }
    }
}
