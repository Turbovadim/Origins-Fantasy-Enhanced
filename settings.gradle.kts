pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.10"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "Origins-Fantasy"
include("version")
include("core")
include("1.21.1")
include("1.21.3")
include("1.21.4")
include("1.21")
include("1.20.6")
include("1.20.4")
include("1.20.3")
include("1.20.2")
include("1.20.1")
include("1.20")
include("1.19")
include("1.19.1")
include("1.19.2")
include("1.19.3")
include("1.19.4")
