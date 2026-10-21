# 14-Week Roadmap and Sprint Plan

Assumption: 10–15 hours/week, Java foundation available, Android playback and Spring integration still require learning. Treat estimates as planning ranges, not deadlines.

## Sprint 1 — Weeks 1–2: Foundation
- Create Git repository, README, `.gitignore`.
- Create Android Java/XML project.
- Create Spring Boot project with Maven.
- Configure PostgreSQL local.
- Draw ERD and agree API contract.
- Gather 20–50 legally usable sample tracks.
Acceptance: Android app launches; backend starts and connects to database; schema and architecture notes exist.

## Sprint 2 — Weeks 3–4: Basic playback
- Build Home and Now Playing screens.
- Integrate Media3 ExoPlayer.
- Implement play/pause/seek/next/previous.
- Handle buffering and playback errors.
- Begin MediaSessionService.
Acceptance: sample tracks play correctly; UI remains responsive; no accidental duplicate player instances.

## Sprint 3 — Weeks 5–6: Background playback and catalog
- Complete notification/system controls and lifecycle behavior.
- Implement song entities, repositories, migrations, and catalog API.
- Add search and pagination.
- Connect Android through Retrofit.
Acceptance: playback background scenarios pass; catalog is loaded from backend.

## Sprint 4 — Weeks 7–8: Playlists and history
- Create/manage playlists and playlist items.
- Implement favorites and listening history.
- Add Room cache only where useful.
- Unit-test core services.
Acceptance: playlist operations persist; history events are correct and not duplicated by repeated callbacks.

## Sprint 5 — Weeks 9–10: Recommendation Engine
- Capture genre preferences.
- Implement deterministic scoring and ranking.
- Handle cold start and repeated/skip behavior.
- Build recommendation endpoint.
- Test synthetic user profiles.
Acceptance: recommendations are deterministic for a fixed input, respect filters, and have test coverage.

## Sprint 6 — Weeks 11–12: Personalized playlists
- Accept target genres and duration.
- Select songs, prevent duplicates, and calculate duration.
- Explain recommendations in UI.
- Test insufficient-catalog cases.
Acceptance: playlist respects filters and item limits; reports when there are not enough tracks.

## Sprint 7 — Weeks 13–14: Hardening and portfolio
- Regression tests and error handling.
- Complete authentication/authorization before sharing with others.
- Test network failures and playback recovery.
- Finish README, ERD, API docs, screenshots, and demo video.
Acceptance: reproducible local setup, core tests pass, no known critical data-access/security issue, end-to-end demo works.

## Sprint rules
- Limit each sprint to 3–5 major tasks.
- Reserve about 20% of time for tests, bug fixes, and documentation.
- Do not add new features while a core playback or data-security bug remains.
- If behind, cut optional scope before cutting playback tests or authorization checks.
