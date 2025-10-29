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
2. ⚙️ An example of how a framework can be structured that can be used as a reference when putting together your own solution

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

## Installation

You'll need a recent version of Java and Maven. There's sdkman config that'll install Java 25 and Maven 3.9.11.

To run the example tests against Spring PetClinic you'll also need Docker installed.

## Usage

## Opinionated configurations

Countercheck is an opinionated framework; it enforces best practices where it can. However, should you want some flexibility these settings can be easily disabled/reconfigured.

* 📄 **Editorconfig** - Countercheck includes an `.editorconfig` file that your IDE should pick up and use. It'll set your line endings to be LF, and your indentation to 2-space for Java and XML (i.e. your pom.xml) files.

### Maven's `validate` phase

Before tests are executed countercheck will run a collection of checks, and will fail the build should the checks fail.

* 📄 **Checkstyle** - Checkstyle will verify whether your code meets the `google_checks.xml` style rules and fails the build on any warning. The configuration can be found in the `maven-checkstyle-plugin` section of the `pom.xml` file.
* 📄 **Toolchain versions** - The `maven-enforcer-plugin` is configured to check for minimum versions of java and maven.

## Diagnostics output

###

## Phases

Countercheck execution takes a four-phase approach.

1. 🛫 **Pre-flight checks** - wait for the AUT to be in a state ready for testing (e.g. API health checks are responding)
2. 💨 **Smoke tests** - check whether the AUT is working enough to be worth running tests against (e.g. is login possible)
3. 🔨 **API tests** - test the APIs
4. 🙋‍♂️ **Acceptance tests** - test that the AUT works from a user's perspective

Each phase is made up of the following:

* A maven profile (configured in the `pom.xml` file under `/project`)
* A JUnit5 Suite class (located in `src/test/java/com/natritmeyer/countercheck/tests/suites`)
* A collection of JUnit5 test classes (located in `src/test/java/com/natritmeyer/countercheck/tests/[suite name]`)

### Execution order

The phases will execute in the order they're defined in the `pom.xml` file. This is standard maven functionality.

### How to customise the phases

Adding your own phase is trivial.

## Reporting

Output from the various countercheck components is dialled up to

* Every line of console output from maven is timestamped (see `/.mvn/maven.config`)

## Removing the demo

Countercheck is shipped with demo tests against Spring Petclinic. To remove that stuff you need to delete:

* The `/docker-compose.yml` file
* The `manage-example-spring-petclinic-services` profile from the `pom.xml` file
* The domain classes from `src/test/java/com/natritmeyer/countercheck/support/api/domain`
* The request body classes from `src/test/java/com/natritmeyer/countercheck/support/api/requestbodies`
* The Page Object Model classes from `src/test/java/com/natritmeyer/countercheck/support/ui/pages`
* The `*Test.java` classes (but not the `*Suite.java` classes!) from `src/test/java/com/natritmeyer/countercheck/tests`
