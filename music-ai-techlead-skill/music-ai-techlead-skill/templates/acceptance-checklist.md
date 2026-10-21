# Music AI Acceptance Checklist

## Product
- [ ] Play, pause, seek, next, previous work.
- [ ] Background playback and notification controls work.
- [ ] Catalog search and pagination work.
- [ ] Playlists can be created and managed.
- [ ] Favorites persist.
- [ ] Listening history is recorded accurately.
- [ ] User can set genre/artist preferences.
- [ ] Recommendations follow documented ranking rules.
- [ ] Generated playlists contain no duplicates.
- [ ] Insufficient catalog is handled clearly.

## Backend
- [ ] DTO validation is present.
- [ ] HTTP status codes are appropriate.
- [ ] Error responses are consistent and do not expose stack traces.
- [ ] Database migrations are versioned.
- [ ] User data is scoped to authenticated identity.
- [ ] No plaintext passwords or committed secrets.
- [ ] Core service logic has automated tests.

## Android
- [ ] UI does not block on network calls.
- [ ] Loading, empty, offline, and error states are handled.
- [ ] Player lifecycle is independent from Activity visibility.
- [ ] Playback resources are released correctly.
- [ ] Local cache behavior is documented and tested.
- [ ] Local and deployed base URLs are configured separately.

## Recommendation
- [ ] Signals and score ranges are documented.
- [ ] Cold-start behavior is defined.
- [ ] Duplicate/repetition handling is tested.
- [ ] Synthetic evaluation is not misrepresented as real-world quality.
- [ ] Recommendation response includes a truthful explanation.

## Delivery
- [ ] README includes prerequisites and local setup.
- [ ] ERD and API contract are current.
- [ ] Tests can be run from documented commands.
- [ ] Demo shows the complete primary user flow.
- [ ] Audio source permissions are documented.
- [ ] No paid dependency is required to run the MVP locally.
