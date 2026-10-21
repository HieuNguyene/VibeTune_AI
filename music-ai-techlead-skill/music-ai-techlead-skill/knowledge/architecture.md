# Architecture and Technology Decisions

## System overview

```text
Android App (Java/XML)
  ├── UI (Activity/Fragment)
  ├── ViewModel/Presenter (screen state)
  ├── Repository
  │    ├── Retrofit remote data source
  │    └── Room local data source
  └── Media3 playback module
       └── MediaSessionService + ExoPlayer

        HTTPS/HTTP in local development
                 |
                 v
Spring Boot REST API (modular monolith)
  ├── song
  ├── artist / genre
  ├── playlist
  ├── history
  ├── favorite
  ├── preference
  ├── recommendation
  ├── security
  └── common / exception
                 |
                 v
             PostgreSQL
```

Audio is played by the Android player from an authorized local file or authorized audio URL. Backend serves metadata and authorization-aware playback references, not the full stream by default.

## Technology choices

| Area | Decision | Notes |
|---|---|---|
| Android language/UI | Java + XML | Preserve user's existing Java foundation |
| Playback | AndroidX Media3 ExoPlayer | Do not write an audio engine from scratch |
| Background playback | MediaSession + MediaSessionService | Keep player lifecycle independent from UI |
| Networking | Retrofit + OkHttp | Use one JSON converter consistently |
| Local cache | Room | Add only for meaningful cache/offline use |
| Backend | Spring Boot 3.5.x, compatible Java 17 patch line | Select a currently supported patch; pin versions |
| Architecture | Modular monolith | No microservices for MVP |
| Persistence | PostgreSQL, Spring Data JPA/Hibernate | Use migrations |
| Validation | Jakarta Bean Validation | Validate request DTOs |
| Security | Spring Security | Add before exposing app to other users |
| Tests | JUnit 5, Mockito, API tests | Test business rules and authorization |
| Source control | Git + GitHub | Commit in small, explainable units |

Version note: verify official compatibility and support status when creating the project. Do not assume a particular patch is still the latest.

## Backend module pattern

Each feature should group related code, for example:

```text
song/
  SongController.java
  SongService.java
  SongRepository.java
  Song.java
  SongResponse.java
  SongMapper.java   # add only if mapping complexity justifies it
```

Responsibility:
- Controller: HTTP request/response, status codes.
- Service: business rules and orchestration.
- Repository: database access.
- Entity: persistence model.
- DTO: public API contract; do not expose entities as the API by default.
- Security/config/common: cross-cutting concerns.

## Android module pattern

```text
com.hieu.musicai/
  ui/
    home/
    player/
    playlist/
    recommendation/
  data/
    local/
    remote/
    repository/
  playback/
  model/
```

Keep playback service independent of Activity/Fragment. Keep network and database operations out of UI classes. If ViewModel is not yet familiar, begin with a simple Presenter or thin UI and refactor incrementally; do not introduce abstractions without a reason.

## Local development connectivity

- Spring Boot on host: commonly `localhost:8080`.
- Standard Android Emulator reaching host loopback: use `10.0.2.2` instead of emulator `localhost`.
- Physical Android device: use the host machine's reachable LAN IP and appropriate firewall/network settings.
- Cleartext HTTP may be blocked by Android configuration; allow it only for local development if necessary. Use HTTPS for deployed environments.
- Never commit production secrets. Keep local secrets in ignored config or environment variables.

## Non-functional requirements
- Playback does not depend on a visible Activity.
- UI stays responsive during network calls.
- Errors are surfaced clearly; retry only where appropriate.
- API requests are validated.
- Protected data is scoped to the authenticated user.
- Logs do not leak passwords, tokens, or sensitive user data.
