plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.6"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14" apply false
    kotlin("jvm") version "2.1.10"
}

group = "com.starshootercity.originsfantasy"
version = "1.0.23"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(project(":core"))
    implementation(project(":version"))
    implementation(project(":1.21.1", "reobf"))
    implementation(project(":1.21.3", "reobf"))
    implementation(project(":1.21.4", "reobf"))
    implementation(project(":1.21", "reobf"))
    implementation(project(":1.20.6", "reobf"))
    implementation(project(":1.20.4", "reobf"))
    implementation(project(":1.20.3", "reobf"))
    implementation(project(":1.20.2", "reobf"))
    implementation(project(":1.20.1", "reobf"))
    implementation(project(":1.20", "reobf"))
    implementation(project(":1.19", "reobf"))
    implementation(project(":1.19.1", "reobf"))
    implementation(project(":1.19.2", "reobf"))
    implementation(project(":1.19.3", "reobf"))
    implementation(project(":1.19.4", "reobf"))
    implementation(kotlin("stdlib-jdk8"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

allprojects {
    tasks.withType<ProcessResources> {
        inputs.property("version", rootProject.version)
        filesMatching("**plugin.yml") {
            expand("version" to rootProject.version)
        }
    }
}

tasks.shadowJar {
    dependencies {
        exclude(dependency("com.github.Turbovadim:EnderaLib:1.4.2"))
        exclude {
            it.moduleGroup == "org.jetbrains.kotlin" || it.moduleGroup == "org.jetbrains.kotlinx"
        }
    }
}

tasks {
    compileJava {
        options.release.set(17)
    }
}

tasks.test {
    useJUnitPlatform()
}

