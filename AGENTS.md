# Project Agents.md Guide for OpenAI Codex

This Agents.md file provides comprehensive guidance for OpenAI Codex and other AI agents working with this codebase.

## Project Structure for OpenAI Codex Navigation

- `src/main/java`: Java Classes
- `src/main/docker`: Dockerfiles
- `src/main/resources`: Application Configuration 
- `src/main/resources/templates`: Quote Templates
- `src/test/java`: Test Classes

## Building and Testing

The project can be compiled with 
```shell script
./mvnw compile 
```

Tests can be run with
```shell script
./mvnw verify 
```

## Tests
- Tests use JUnit5 and Rest-Assured
- @QuarkusIntegrationTest are "blockbox" tests from the outside via http
- @QuarkusTest is basically a Unit test that can access all container features including CDI injection

## Commit Messages and Pull Requests
- Follow the [Chris Beams](http://chris.beams.io/posts/git-commit/) style for
  commit messages.
- Every pull request should answer:
  - **What changed?**
  - **Why?**
  - **Breaking changes?**
- Comments should be complete sentences and end with a period.

## Context
Product Requirements Document: `PRD.md` in the repository root. 
The EquiScheduler is a comprehensive and user-friendly platform designed to streamline the scheduling and management of a riding school.