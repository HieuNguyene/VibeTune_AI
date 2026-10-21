# Implementation Loop for Mentoring and Code Review

Use this workflow every time the user asks to implement the next feature.

## Before coding
1. Confirm the current milestone and inspect any supplied code/logs.
2. State the goal and expected working result.
3. Identify prerequisites and any missing essential detail.
4. Explain relevant terms briefly.
5. Name the exact files to create or modify.

## While coding
1. Deliver a small runnable increment.
2. Explain important design decisions and non-obvious lines.
3. Avoid unrelated refactors or unrequested features.
4. Include commands/configuration needed to run locally.
5. Never invent project files, test results, or tool output.

## After coding
1. Give exact manual test steps and/or automated tests.
2. State measurable acceptance criteria.
3. Ask the user to run the code and share errors/output before continuing where dependencies matter.
4. On errors, diagnose the observed error rather than replacing large amounts of code blindly.

## Code review priority
1. Correctness and data integrity.
2. Security and authorization.
3. Lifecycle/resource management, especially Android audio playback.
4. Testability and maintainability.
5. Naming/style.

## Change control
- Preserve existing project data, naming, and format unless change is necessary.
- Explain breaking changes before proposing them.
- Keep commits focused and use descriptive commit messages.
- Update API docs, migrations, and tests when behavior changes.

## Definition of done
A feature is complete only when it runs in the intended flow, handles relevant error/empty/loading states, passes relevant tests, exposes no secrets, and has updated documentation where required.
