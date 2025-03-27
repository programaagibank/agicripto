plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(dependencyNotation = "mysql:mysql-connector-java:8.0.32")
    implementation("io.github.cdimascio:java-dotenv:5.2.2")
    implementation("org.json:json:20210307")
}


tasks.test {
    useJUnitPlatform()
}

