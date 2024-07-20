pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
            }
        }

        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("https://repo1.maven.org/maven2/")
        }
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }  // Add this line
        maven {
            url = uri("https://repo1.maven.org/maven2/")
        }
    }
}

rootProject.name = "InnovateX DTU"
include(":app")
 