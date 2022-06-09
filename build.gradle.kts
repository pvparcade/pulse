plugins {
    `java-library`
    id("signing")
    id("maven-publish")
}

group = "dev.pvparcade"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    implementation("redis.clients:jedis:4.2.3")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "blueoxygen"

            val releasesRepoUrl = uri("https://repo.blueoxygen.net/releases")
            val snapshotsRepoUrl = uri("https://repo.blueoxygen.net/snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}