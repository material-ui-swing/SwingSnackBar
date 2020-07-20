plugins {
    `java-library`
    `maven-publish`
    signing
}

group = "io.github.material-ui-swing"
version = project.property("PROJECT_VERSION")!!

repositories {
    jcenter()
    maven("https://repo.maven.apache.org/maven2/")
}

dependencies {
    //implementation("io.github.vincenzopalazzo:material-ui-swing:1.1.1-rc2")
    testImplementation(files("$projectDir/devlib/LinkLabelUI.jar"))
    testImplementation(files("$projectDir/devlib/material-ui-swing-1.1.1-rc3.jar"))
}

tasks{
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    create<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        from(sourceSets["main"].allSource)
    }
}

publishing {
    publications {
        create<MavenPublication>("SwingSnackBar") {
            from(components["java"])

            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            repositories {
                maven {
                    credentials {
                        username = project.property("sonatypeUsername").toString()
                        password = project.property("sonatypePassword").toString()
                    }
                    val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                    val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
                    url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                }
            }

            pom {
                name.set("SwingSnackBar")
                description.set("Simple component as Android SnackBar for Swing Applications")
                url.set("https://github.com/material-ui-swing/SwingSnackBar")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/material-ui-swing/SwingSnackBar/blob/master/LICENSE.md")
                    }
                }

                developers {
                    developer {
                        id.set("vincenzopalazzo")
                        name.set("Vincenzo Palazzo")
                        email.set("vincenzopalazzodev@gmail.com")
                        url.set("https://github.com/vincenzopalazzo")
                        roles.addAll("developer")
                        timezone.set("Europe/Rome")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/material-ui-swing/SwingSnackBar.git")
                    developerConnection.set("scm:git:ssh://github.com:material-ui-swing/SwingSnackBar.git")
                    url.set("https://github.com/material-ui-swing/SwingSnackBar.git")
                }
            }
        }
    }
}

signing {
    isRequired = true
    sign(tasks["sourcesJar"], tasks["javadocJar"])
    sign(publishing.publications["SwingSnackBar"])
}
