⚠️ _This README is a work in progress_ ⚠️

-----

# Countercheck

_An example test automation framework built with Java, Junit5, Spring, WebTestClient, & Playwright_

> "The reasoning of mortals is worthless, and our designs are likely to fail."
>
> _Wisdom 9:14_

## Introduction

Countercheck has a couple of intended uses:

1. 🏃‍➡️ A hit-the-ground-running drop-in solution for people needing a Java-based test automation framework
2. ⚙️ An example of how a framework can be structured that can be used as a reference when putting together your own
   solution

### Features

* Test execution managed by Maven, surefire, and JUnit5
* HTTP-based API testing using Spring's WebTestClient
* Browser testing using Playwright
* Multi-phase test execution
* Multi-environment execution
* Test data management
* Extensive reporting & crime scene management

### Design decisions

* Based on well established tech (maven, java, junit5, spring)
* Uses sensible defaults
* Use of dependency injection (framework broken down into separate concerns)
* Pushes best practice in test case design

## Toolchain requirements

Countercheck is set up to install its toolchain dependencies using SDKMAN. If you've got it installed simply run
`sdk env install` and you'll have everything you need.

Otherwise, install java &amp; maven however you like.

| Tool  | Default version | Minimum version |
|-------|-----------------|-----------------|
| Java  | 24              | 17              |
| Maven | 3.9.11          | 3.6             |

#### Changing toolchain versions

Set the following `pom.xml` values:

* `properties/java.version` to 17
* `requireJavaVersion/version` to 17
* `requireMavenVersion/version` to 3.6

Set the `.sdkmanrc` file contents to:

```properties
java=17.0.17-tem
maven=3.6.3
```

NB: You'll need to remove the `.mvn/jvm.config` file.

To run the example tests against Spring PetClinic you'll also need Docker installed.

## Usage

### Getting started

If you're running countercheck for the first time and want to see it in action, perform the following steps:

1. Ensure Docker is running
2. Run `$ mvn clean test -Dcountercheck.environment=local`
3. Run `$ mvn site`
4. Open `target/site/index.html`

## Phases

Countercheck execution takes a four-phase approach.

1. 🛫 **Pre-flight checks** - wait for the AUT to be in a state ready for testing (e.g. API health checks are responding)
2. 💨 **Smoke tests** - check whether the AUT is working enough to be worth running tests against (e.g. is login
   possible)
3. 🔨 **API tests** - test the APIs
4. 🙋‍♂️ **Acceptance tests** - test that the AUT works from a user's perspective

Each phase is made up of the following:

* A maven profile (configured in the `pom.xml` file under `/project`)
* A JUnit5 Suite class (located in `src/test/java/com/natritmeyer/countercheck/tests/suites`)
* A collection of JUnit5 test classes (located in `src/test/java/com/natritmeyer/countercheck/tests/[suite name]`)

### Execution order

The phases will execute in the order they're defined in the `pom.xml` file. This is standard maven functionality.

The current order is:

1. `preflightchecks`
2. `smoketests`
3. `apitests`
4. `acceptancetests`

Phases can be run individually, e.g:

`$ mvn -P apitests`

Phases can also be run in arbitrary collections, e.g:

`$ mvn -P preflightchecks,apitests`

### How to customise the phases

Customising phases is a matter of editing the `profiles` section of the `pom.xml`.

## Reporting

Countercheck comes with extensive reporting at both high and low levels.

#### 📊 High level reporting

Raw test execution reports are found post-execution in the `target` directory, e.g. `target/surefire-reports` or
`target/playwright-traces`; these are designed to be ingested and evaluated by your CI/CD tooling (e.g. Jenkins) for
build decisioning.

To generate pretty reports in the `target/site` directory, use the `mvn site` command after your build completes (often
performed in a pipeline script's 'after' block). The top level report that links the rest is `target/site/index.html`.

| Report              | Description                                                                              |
|---------------------|------------------------------------------------------------------------------------------
| 📊 Surefire         | Test results from `**/*Test.java` classes executed by `$ mvn test` (JUnit/cucumber etc)  |
| 📊 Failsafe         | Test results from `**/*IT.java` classes executed by `$ mvn verify`  (JUnit/cucumber etc) |
| 📝 Playwright trace | Test execution traces in `target/playwright-traces`
| 📹 Playwright video | Recorded test execution videos in `target/playwright-video`                              |

#### 🔬 Low-level reporting

Countercheck is made up of many components, each of which contributes low-level information, mainly to the console
output, that can help diagnosing difficult issues.

| Type                                                  | Component                        | Usage                                               |
|-------------------------------------------------------|----------------------------------|-----------------------------------------------------|
| 🖥️ Each line timestamped                             | `.mvn/maven.config` setting      | Diagnose timing issues                              |
| 🖥️ Java &amp; Maven version at top of console output | `.mvn/maven.config` setting      | Diagnose version mismatches                         |
| 🖥️ Planned phase execution                           | `maven-help-plugin` config       | Ensure expected phases are selected for execution   |
| 🖥️ Effective POM                                     | `maven-help-plugin` config       | Diagnose dependency &amp; plugin version mismatches |
| 🖥️ Dependency &amp; plugin versions                  | `versions-maven-plugin` config   | Ensure expected versions                            |
| 🖥️ Dependency tree                                   | `maven-dependency-plugin` config | Diagnose (transient dependency) version mismatch    |
| 🖥️ Available dependency &amp; plugin updates         | `versions-maven-plugin` config   | Be aware of required framework maintenance          |

## Test code quality components &amp; maintenance

Countercheck includes various tools that enable the measurement and enforcement of high quality test code. Some checks
fail the build, others don't.

| Tool             | Purpose                                        | Fails build |
|------------------|------------------------------------------------|-------------|
| 🔨Maven Enforcer | Ensures minimum Java/Maven versions            | ✅           |
| ✔️ Checkstyle    | Ensures `google_checks` code style conformance | ✅           |
| 🔍 PMD           | Static analysis                                | ❌           |
| 🔍 SpotBugs      | Static analysis                                | ❌           |

## Removing the demo

Countercheck ships with demo tests against Spring Petclinic. To remove the demo, delete the following:

* The `/docker-compose.yml` file
* The `manage-example-spring-petclinic-services` profile from the `pom.xml` file
* The domain classes from `src/test/java/com/natritmeyer/countercheck/support/api/domain`
* The request body classes from `src/test/java/com/natritmeyer/countercheck/support/api/requestbodies`
* The Page Object Model classes from `src/test/java/com/natritmeyer/countercheck/support/ui/pages`
* The `*Test.java` classes (but not the `*Suite.java` classes!) from `src/test/java/com/natritmeyer/countercheck/tests`
