⚠️ _This README is a work in progress_ ⚠️

-----

# Countercheck
_A test automation framework built with Java, Junit5, Spring, WebTestClient, & Playwright_

> "The reasoning of mortals is worthless, and our designs are likely to fail."
>
> _Wisdom 9:14_

## Introduction

Countercheck is intended to be a hit-the-ground-running drop-in solution for people needing a java-based test automation framework.

### Features

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

## Installation

## Usage

## Configuration

###

## Phases

Countercheck execution takes a four-phase approach.

1. **Pre-flight checks** - wait for the AUT to be in a state ready for testing (e.g. API health checks are responding)
2. **Smoke tests** - check whether the AUT is working enough to be worth running tests against (e.g. is login possible)
3. **API tests** - test the APIs
4. **Acceptance tests** - test that the AUT works from a user's perspective

Each phase is made up of the following:

* A maven profile (configured in the `pom.xml` file under `/project)
* A JUnit5 Suite
* A collection of JUnit5 test classes

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
