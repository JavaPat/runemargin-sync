# Updating RuneMargin Sync in the Plugin Hub

This v1.1 source updates RuneMargin Sync for RuneLite API update 241. RuneLite
changed Grand Exchange offer price and spent/received GP values from `int` to
`long`; the plugin now uses the current 64-bit types throughout.

## Before submitting

1. Open this folder as a Gradle project in IntelliJ IDEA.
2. Allow Gradle to finish downloading dependencies.
3. Open the Gradle tool window and run `test`.
4. Run the development client and verify a small completed Grand Exchange trade
   creates a new line in `.runelite/runemargin-sync/trades-v1.jsonl`.
5. Commit and push these files to the `main` branch of
   `https://github.com/JavaPat/runemargin-sync`.

## Plugin Hub update

1. Copy the full 40-character hash of the new `runemargin-sync` commit.
2. In your fork of `runelite/plugin-hub`, update only the `commit=` line in the
   existing `plugins/runemargin-sync` pointer file.
3. Commit that pointer change on a new branch.
4. Open a pull request to `runelite/plugin-hub:master` titled
   `Update RuneMargin Sync for latest RuneLite API`.
5. Wait for the Plugin Hub build check and reviewer approval.

Suggested pull-request description:

> Updates RuneMargin Sync for RuneLite API update 241. Grand Exchange offer
> price and spent/received GP values now use long throughout, matching the
> current RuneLite API and preventing overflow for high-value trades. Plugin
> behaviour, local-only storage and JSONL schema remain unchanged.
