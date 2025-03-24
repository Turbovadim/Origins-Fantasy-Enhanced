package com.starshootercity.originsfantasy

import com.starshootercity.OriginsAddon
import com.starshootercity.abilities.Ability
import com.starshootercity.originsfantasy.abilities.*
import org.bukkit.Bukkit
import org.endera.enderalib.utils.async.BukkitDispatcher

class OriginsFantasy : OriginsAddon() {
    override fun getNamespace(): String {
        return "fantasyorigins"
    }

    init {
        instance = this
    }

    override fun getAbilities(): List<Ability> {
        val abilities = mutableListOf(
                AllayMaster(),
                ArrowEffectBooster(),
                BardicIntuition(),
                BowBurst(),
                BreathStorer(),
                Chime(),
                DoubleHealth(),
                DragonFireball(),
                Elegy(),
                EndCrystalHealing(),
                EndBoost(),
                FortuneIncreaser(),
                IncreasedArrowDamage(),
                IncreasedArrowSpeed(),
                HeavyBlow(),
                HeavyBlow.IncreasedCooldown.increasedCooldown,
                HeavyBlow.IncreasedDamage.increasedDamage,
                EndBoost.EndHealth.endHealth,
                EndBoost.EndStrength.endStrength,
                IncreasedSpeed(),
                InfiniteHaste(),
                InfiniteNightVision(),
                OceanWish(),
                OceanWish.LandWeakness.landWeakness,
                OceanWish.LandHealth.landHealth,
                OceanWish.LandSlowness.landSlowness,
                MagicResistance(),
                MoonStrength(),
                NaturalArmor(),
                NoteBlockPower(),
                PerfectShot(),
                PermanentHorse(),
                PoorShot(),
                StrongSkin(),
                SuperJump(),
                OceansGrace(),
                OceansGrace.WaterHealth.waterHealth,
                OceansGrace.WaterStrength.waterStrength,
                VampiricTransformation(),
                DaylightSensitive(),
                WaterSensitive(),
                Leeching(),
                Stronger(),
                UndeadAlly()
            )
        if (nmsInvoker!!.getGenericScaleAttribute() != null) {
            abilities.add(LargeBody())
            abilities.add(SmallBody())
        }
        return abilities.toList()
    }

    override fun onRegister() {
        initializeNMSInvoker()
        saveDefaultConfig()
        bukkitDispatcher = BukkitDispatcher(this)

        if (!config.contains("arrow-speed-multiplier")) {
            config.set("arrow-speed-multiplier", 2)
            config.set("arrow-damage-increase", 3)
            config.setComments(
                "arrow-speed-multiplier",
                mutableListOf<String?>("Amount to multiply arrow speed by when using the Increased Arrow Speed ability")
            )
            config.setComments(
                "arrow-damage-increase",
                mutableListOf<String?>("Amount to increase arrow damage by when using the Increased Arrow Damage ability")
            )
            saveConfig()
        }
    }

    companion object {
        private lateinit var instance: OriginsFantasy

        lateinit var bukkitDispatcher: BukkitDispatcher

        @JvmStatic
        fun getInstance(): OriginsFantasy {
            return instance
        }

        private var nmsInvoker: FantasyNMSInvoker? = null

        private fun initializeNMSInvoker() {
            nmsInvoker = when (Bukkit.getMinecraftVersion()) {
                "1.19" -> FantasyNMSInvokerV1_19()
                "1.19.1" -> FantasyNMSInvokerV1_19_1()
                "1.19.2" -> FantasyNMSInvokerV1_19_2()
                "1.19.3" -> FantasyNMSInvokerV1_19_3()
                "1.19.4" -> FantasyNMSInvokerV1_19_4()
                "1.20" -> FantasyNMSInvokerV1_20()
                "1.20.1" -> FantasyNMSInvokerV1_20_1()
                "1.20.2" -> FantasyNMSInvokerV1_20_2()
                "1.20.3" -> FantasyNMSInvokerV1_20_3()
                "1.20.4" -> FantasyNMSInvokerV1_20_4()
                "1.20.5", "1.20.6" -> FantasyNMSInvokerV1_20_6()
                "1.21" -> FantasyNMSInvokerV1_21()
                "1.21.1" -> FantasyNMSInvokerV1_21_1()
                "1.21.2", "1.21.3" -> FantasyNMSInvokerV1_21_3()
                "1.21.4" -> FantasyNMSInvokerV1_21_4()
                else -> throw IllegalStateException("Unexpected version: " + Bukkit.getMinecraftVersion() + " only versions 1.19 - 1.21.4 are supported")
            }
            Bukkit.getPluginManager().registerEvents(nmsInvoker!!, getInstance())
        }

        @JvmStatic
        val NMSInvoker: FantasyNMSInvoker
            get() = nmsInvoker!!
    }
}