plugins {
    java
    application
}

group = "example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.7.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

application {
    mainClass.set("Main")
}