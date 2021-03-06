plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.github.sherter.google-java-format") version "0.9"
}

group = project.property("GROUP_ID")!!
version = project.property("PROJECT_VERSION")!!

repositories {
    jcenter()
}

dependencies {
    testImplementation("io.github.material-ui-swing:LinkLabelUI:0.0.1-rc1")
    testImplementation("io.github.vincenzopalazzo:material-ui-swing:1.1.2-rc2")
}

/*
plugins.withType<JavaPlugin>().configureEach {
    configure<JavaPluginExtension> {
        modularity.inferModulePath.set(true)
    }
}

java {
    modularity.inferModulePath.set(true)
}
*/

//TODO I'm using this because I will create a Multi-Release JAR Files
//https://openjdk.java.net/jeps/238
tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to project.property("MODULE_NAME").toString())
    }
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

    withType<JavaCompile>().configureEach {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
        options.encoding = "ISO-8859-1"
    }

    withType<Jar>().configureEach {
        from("${rootDir}/LICENSE") {
            into("META-INF")
        }
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
                        username = System.getenv("MAVEN_USERNAME")
                        password = System.getenv("MAVEN_PASSWORD")
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
