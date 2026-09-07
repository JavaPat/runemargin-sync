# RuneMargin Sync

RuneMargin Sync is a free, privacy-first RuneLite companion for RuneMargin
Tracker. It records exact positive Grand Exchange fill deltas to a local JSONL
file. It never places offers, reads credentials, identifies the player, or sends
trade data to a server.

## What it records

For each fill event observed while the plugin is running:

- UTC timestamp and random event ID
- item ID and buy/sell direction
- newly filled quantity and exact GP delta
- derived average price and original offer price
- zero-based GE slot, cumulative quantity, total quantity, and offer state

The file is:

`<RuneLite directory>/runemargin-sync/trades-v1.jsonl`

On standard installations this is `.runelite/runemargin-sync/trades-v1.jsonl`
inside the user's profile. Each event is one JSON object on one line so
RuneMargin Tracker can safely read the file while RuneLite appends to it.

## Accuracy and limitations

The plugin listens only to RuneLite's `GrandExchangeOfferChanged` event. At
login, RuneLite emits empty slots before current offers, so the plugin treats
the first non-empty state as a baseline. It writes a record only when both the
cumulative quantity and spent/received GP increase for the same offer.

This makes recorded completed-fill totals exact and avoids replaying historical
offers. It cannot recover fills that happened while RuneLite or the plugin was
not running. The Grand Exchange order book remains private, so neither this
plugin nor RuneMargin Tracker can guarantee the price or timing of the next fill.

## Development

Requirements: Java 11. Open this directory as a Gradle project in IntelliJ IDEA;
the included official Gradle wrapper downloads the correct Gradle version. Or run:

```text
gradlew.bat test
gradlew.bat run
```

The `run` task starts a developer-mode RuneLite client. For a Jagex Account,
follow RuneLite's official "Using Jagex Accounts" development instructions.

In-game verification must be performed manually by the developer/user:

1. Enable RuneMargin Sync and place a small buy offer.
2. Check that a partial fill adds one line with the exact new quantity and GP.
3. Check that another partial fill adds only the new delta.
4. Complete or cancel the offer and confirm no duplicate is written.
5. Reuse the same slot and verify the new offer starts from a clean baseline.
6. Open RuneMargin Tracker 3.0 and confirm the fill appears under RuneLite Sync.

Do not automate game input while testing.

## Licence

BSD 2-Clause. See `LICENSE`.
