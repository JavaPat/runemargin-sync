# RuneLite Plugin Hub submission checklist

1. Create a public GitHub repository named `runemargin-sync`.
2. Copy this project into the repository and commit the source files. Do not
   commit `.gradle`, `build`, `out`, IDE files, or generated JARs.
3. Confirm Java 11 is selected and run `gradle test`.
4. Run `gradle run`, sign into the development client using RuneLite's official
   Jagex Account instructions, and complete the manual cases in `README.md`.
5. Add a short public support URL to `runelite-plugin.properties` if available.
6. Submit the repository to `runelite/plugin-hub` using the current Plugin Hub
   instructions and its plugin verification process.

Suggested reviewer summary:

> RuneMargin Sync records exact positive quantity/GP deltas from RuneLite's
> GrandExchangeOfferChanged event to an append-only JSONL file under
> RuneLite.RUNELITE_DIR/runemargin-sync. The file contains no account identity
> and no data leaves the PC. Initial and replacement offers establish a
> baseline, preventing login replays and duplicate fills. The companion desktop
> app uses these verified personal observations alongside public OSRS Wiki
> prices; the plugin does not automate or alter gameplay.

Review notes:

- Java 11 target and standard Plugin Hub metadata
- no external server, sockets, telemetry, credentials, reflection, injection,
  game input, overlays, or third-party dependency
- all plugin file I/O stays under `RuneLite.RUNELITE_DIR/runemargin-sync`
- injected RuneLite Gson serializes each record
- BSD 2-Clause licence
- unit tests cover partial fills, duplicate events, initial state, and slot reuse

Acceptance is ultimately decided by RuneLite's reviewers. A similar trade-history
feature exists in Flipping Utilities, so be prepared to explain the distinct,
local interoperability purpose or offer the sync format upstream if reviewers
prefer avoiding plugin fragmentation.
