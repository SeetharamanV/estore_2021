import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	kotlin("plugin.jpa") version "1.3.72"
	jacoco
	groovy
}

group = "com.seetharamanv"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-core")
	implementation("io.micrometer:micrometer-registry-prometheus")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("org.codehaus.groovy:groovy:3.0.5")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.spockframework:spock-core:2.0-M3-groovy-3.0")
	testImplementation("org.spockframework:spock-spring:2.0-M3-groovy-3.0")
	testImplementation("org.jeasy:easy-random-core:4.2.0")
}
tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
sourceSets {
	create("integrationTest") {
		withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
			kotlin.srcDir("src/main/kotlin")
			resources.srcDir("src/main/resources")
			compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
			runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
		}
	}
}
task<Test>("integrationTest") {
	description = "Runs the integration tests"
	group = "verification"
	testClassesDirs = sourceSets["integrationTest"].output.classesDirs
	classpath = sourceSets["integrationTest"].runtimeClasspath
	mustRunAfter(tasks["test"])
	useJUnitPlatform()
}
tasks.test {
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
}
jacoco {
	toolVersion = "0.8.5"
	reportsDir = file("$buildDir/customJacocoReportDir")
}
tasks.jacocoTestReport {
	reports {
		xml.isEnabled = false
		csv.isEnabled = false
		html.destination = file("${buildDir}/jacocoHtml")
	}
	classDirectories.setFrom(
		sourceSets.main.get().output.asFileTree.matching {
			exclude("**/configs/**","**/entities/**","**/exceptions/**","**/models/**","**/preloads/**","**/repositories/**","**/EstoresApplication.kt","**/EstoresApplicationKt","com/seetharamanv/estores/EstoresApplication.kt")
		}
	)
}
tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			enabled = true
			limit {
				minimum = "0.6".toBigDecimal()
			}
		}
		rule {
			enabled = true
			element = "CLASS"
			includes = listOf("org.gradle.*")

			limit {
				counter = "LINE"
				value = "TOTALCOUNT"
				maximum = "0.6".toBigDecimal()
			}
		}
	}
	doFirst {
		println("View code coverage at:${buildDir}/jacocoHtml/index.html")
	}
}
tasks.check{
	dependsOn(tasks["integrationTest"])
	dependsOn(tasks.jacocoTestCoverageVerification)
	dependsOn(tasks.jacocoTestReport)
}
//tasks.withType<AbstractArchiveTask> {
//	setProperty("archiveFileName", "estores.jar")
//}