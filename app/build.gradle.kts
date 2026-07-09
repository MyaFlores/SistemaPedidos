plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.example.sistemapedidos.MainKt")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
