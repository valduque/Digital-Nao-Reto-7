# BookingMx - Testing & Documentation Implementation
📋 Project Overview
This repository contains the implementation of comprehensive testing and documentation standards for the BookingMx platform, focusing on two key modules:

Nearby Cities Graph (JavaScript/React)
Reservations Module (Java/Spring)

Project Duration: November 6-14, 2025
Team: Teresa (Front-end), Karen (Back-end/Testing Lead)

🎯 Objectives

Implement unit testing for JavaScript and Java modules
Achieve minimum 90% code coverage
Establish automated CI/CD pipeline with quality gates
Create comprehensive technical documentation
Define development workflow with testing best practices

🚀 Getting Started
Prerequisites

Node.js >= 16.x
npm >= 8.x
Java >= 11
Maven >= 3.6 or Gradle >= 7.x
Git

Installation
Frontend Setup
bashcd frontend
npm install
npm test
npm run test:coverage
Backend Setup
Using Maven:
bashcd backend
mvn clean install
mvn test
mvn jacoco:report
Using Gradle:
bashcd backend
./gradlew build
./gradlew test
./gradlew jacocoTestReport

🧪 Testing
Running Tests
Frontend (Jest)
bash# Run all tests
npm test

# Run tests in watch mode
npm run test:watch

# Generate coverage report
npm run test:coverage

# Run specific test file
npm test NearbyCitiesGraph.test.js
Backend (JUnit)
bash# Maven
mvn test
mvn jacoco:report

# Gradle
./gradlew test
./gradlew jacocoTestReport
Coverage Requirements

Minimum Coverage: 90%
Coverage Tools: Jest (Frontend), JaCoCo (Backend)
Reports Location: coverage-reports/


📊 CI/CD Pipeline
Pipeline Stages

Build - Compile and build the application
Unit Tests - Run all unit tests
Coverage Check - Verify 90% coverage threshold
Code Quality - Static analysis and linting
Deploy - Deploy to staging/production (on main branch)

Branch Protection Rules

All tests must pass before merging
Minimum 90% code coverage required
At least 1 code review approval required
No direct commits to main branch
