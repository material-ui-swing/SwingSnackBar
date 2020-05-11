

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