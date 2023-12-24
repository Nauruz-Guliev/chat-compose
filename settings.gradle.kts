pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://www.jitpack.io") }
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
