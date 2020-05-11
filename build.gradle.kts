plugins {
    `java-library`
    `maven-publish`
}

group = "io.github.vincenzopalazzo"
version = "0.0.1-rc1"

repositories {
    jcenter()
}

dependencies {
    //implementation("io.github.vincenzopalazzo:material-ui-swing:1.1.1-rc2")
    testImplementation(files("$projectDir/devlib/LinkLabelUI.jar"))
    testImplementation(files("$projectDir/devlib/material-ui-swing-1.1.1-rc3.jar"))
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/OWNER/REPOSITORY")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register("gpr", MavenPublication::class) {
            from(components["java"])
        }
    }
}