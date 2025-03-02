package com.starshootercity.originsfantasy.abilities

import com.starshootercity.OriginSwapper.LineData
import com.starshootercity.OriginSwapper.LineData.LineComponent
import com.starshootercity.OriginSwapper.LineData.LineComponent.LineType
import com.starshootercity.OriginsReborn
import com.starshootercity.abilities.Ability
import com.starshootercity.abilities.AttributeModifierAbility
import com.starshootercity.abilities.MultiAbility
import com.starshootercity.abilities.VisibleAbility
import com.starshootercity.originsfantasy.OriginsFantasy.Companion.NMSInvoker
import net.kyori.adventure.key.Key
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier

class HeavyBlow : VisibleAbility, MultiAbility {
    override fun getDescription(): MutableList<LineComponent?> {
        return LineData.makeLineFor(
            "Your attacks are stronger than humans, but you have a longer attack cooldown.",
            LineType.DESCRIPTION
        )
    }

    override fun getTitle(): MutableList<LineComponent?> {
        return LineData.makeLineFor("Heavy Blow", LineType.TITLE)
    }

    override fun getKey(): Key {
        return Key.key("fantasyorigins:heavy_blow")
    }

    override fun getAbilities(): MutableList<Ability> {
        return mutableListOf(
            IncreasedDamage.Companion.increasedDamage,
            IncreasedCooldown.Companion.increasedCooldown
        )
    }


    class IncreasedDamage : AttributeModifierAbility {
        override fun getAttribute(): Attribute {
            return OriginsReborn.NMSInvoker.attackDamageAttribute
        }

        override fun getAmount(): Double {
            return 1.2
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1
        }

        override fun getKey(): Key {
            return Key.key("fantasyorigins:increased_damage")
        }

        companion object {
            var increasedDamage: IncreasedDamage = IncreasedDamage()
        }
    }

    class IncreasedCooldown : AttributeModifierAbility {
        override fun getAttribute(): Attribute {
            return NMSInvoker.getAttackSpeedAttribute()
        }

        override fun getAmount(): Double {
            return -0.4
        }

        override fun getOperation(): AttributeModifier.Operation {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1
        }

        override fun getKey(): Key {
            return Key.key("fantasyorigins:increased_cooldown")
        }

        companion object {
            var increasedCooldown: IncreasedCooldown = IncreasedCooldown()
        }
    }
}
