pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://www.jitpack.io")
            url = uri("https://kotlin.bintray.com/kotlinx")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "chat-compose"
include(":app")
include(":feature:authentication")
include(":core-ui")
include(":navigation")
include(":feature:authentication-api")
include(":feature:chat")
include(":feature:chat-api")
include(":core-data")
include(":feature:profile")
include(":feature:profile-api")
include(":core-testing")
include(":feature:user-search")
include(":feature:user-search-api")
include(":feature:image-picker")
