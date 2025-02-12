/*
 *  * Copyright (c)  2021  Shabinder Singh
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  *  You should have received a copy of the GNU General Public License
 *  *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

plugins {
    kotlin("js")
}

group = "com.shabinder"
version = "0.1"

repositories {
    mavenCentral()
    //maven(url = "https://dl.bintray.com/kotlin/kotlin-js-wrappers")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js:1.5.21")
    implementation(Koin.core)
    implementation(Extras.kermit)
    implementation(Decompose.decompose)
    implementation(MVIKotlin.mvikotlin)
    implementation(MVIKotlin.coroutines)
    implementation(MVIKotlin.mvikotlinMain)
    implementation(MVIKotlin.mvikotlinLogging)
    implementation(project(":common:root"))
    implementation(project(":common:main"))
    implementation(project(":common:list"))
    implementation(project(":common:database"))
    implementation(project(":common:data-models"))
    implementation(project(":common:providers"))
    implementation(project(":common:core-components"))
    implementation(project(":common:dependency-injection"))
    implementation("co.touchlab:stately-common:1.1.7")
    implementation("dev.icerock.moko:parcelize:${Versions.mokoParcelize}")
    // implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2") {
    //  https://youtrack.jetbrains.com/issue/KTOR-2670
        @Suppress("DEPRECATION")
        isForce = true
    }

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1-native-mt") {
        @Suppress("DEPRECATION")
        isForce = true
    }

    with(KotlinJSWrappers) {
        implementation(enforcedPlatform(bom))
        implementation(kotlinReact)
        implementation(kotlinReactDom)
        implementation(kotlinStyled)
    }
}

kotlin {
    js(IR) {
        //useCommonJs()
        browser {
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
        binaries.executable()
    }
}