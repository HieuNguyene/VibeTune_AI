# Recommendation Engine: MVP Design

## Goal
Produce explainable song recommendations and personalized playlists without paid AI APIs, GPUs, or model training.

## Version 1: deterministic scoring
Signals:
- genre preference;
- artist preference;
- favorites and similarity to favorites;
- recent listening;
- skip behavior;
- recency and repetition penalties;
- diversity across artists/genres.

An initial illustrative formula:

`score = 2.0 * genreMatch + 1.5 * artistMatch + 1.0 * favoriteSignal + 0.5 * recencySignal - 2.0 * skipSignal`

Each signal must be normalized to a documented range. This is a starting hypothesis, not a validated optimal formula. Tune it against test profiles and feedback. Avoid double-counting correlated signals.

## Candidate pipeline
1. Fetch active, playable songs from the authorized catalog.
2. Apply hard filters (availability, excluded songs, user constraints).
3. Compute normalized preference signals.
4. Score and rank candidates.
5. Apply repetition penalties and lightweight diversity rules.
6. Return songs with a human-readable explanation.
7. Record impression/play/skip events only when needed and with clear data minimization.

## Cold start
- Ask the user to select a few genres/artists.
- If no preferences are available, use a clearly defined default/popular sample set.
- Do not pretend that a personalized recommendation is based on history when there is no history.

## Playlist generation
Inputs:
- requested name or intent;
- allowed genres/artists;
- target duration;
- maximum item count;
- excluded/recently played songs.

Algorithm:
1. Filter the catalog.
2. Rank by preference.
3. Select songs while preventing duplicates.
4. Add duration until reaching the target or item limit.
5. If the catalog is insufficient, return the available playlist and communicate the shortfall.
6. Return metadata and reasons, not fabricated songs or URLs.

A simple greedy algorithm is sufficient for the MVP. Exact duration matching is not guaranteed unless an optimization algorithm is deliberately implemented.

## Evaluation
- Unit tests for each signal and edge case.
- Precision@10 only when relevance labels or a credible evaluation proxy exist.
- Catalog coverage and recommendation diversity.
- Duplicate rate in generated playlists.
- API p95 latency on a documented local test dataset.
- Real listening/skip outcomes once real usage exists.
- For synthetic data, claim only that rules behave as expected; do not claim real-world recommendation quality.

## When to add ML or LLM
Only after the baseline works and there is a specific need:
- content-based vectors/embeddings for semantic similarity;
- an LLM to translate natural-language requests into structured filters, if affordable and safely proxied by the backend.
Never call an LLM for every Play action. Never place provider keys in the Android app. Validate model output and select songs only from the actual catalog.
