# RuneMargin local fill format v1

Path: `.runelite/runemargin-sync/trades-v1.jsonl`

One UTF-8 JSON object is appended per observed fill delta. Consumers must ignore
unknown fields, ignore unsupported `schemaVersion` values, deduplicate by
`eventId`, and tolerate an incomplete final line while the producer is writing.

| Field | Type | Meaning |
|---|---:|---|
| `schemaVersion` | integer | Always `1` for this contract |
| `eventId` | string | Random UUID used for deduplication |
| `timestampUtc` | string | ISO-8601 UTC observation time |
| `itemId` | integer | OSRS item ID |
| `buy` | boolean | `true` for buy, `false` for sell |
| `deltaQuantity` | integer | Newly completed item units |
| `deltaGp` | integer | Exact newly spent or received GP |
| `averagePrice` | integer | Rounded `deltaGp / deltaQuantity` for display |
| `offerPrice` | integer | Price entered on the GE offer |
| `slot` | integer | Zero-based GE slot from RuneLite |
| `cumulativeQuantity` | integer | Total filled units after this event |
| `totalQuantity` | integer | Requested offer quantity |
| `state` | string | RuneLite GE state at observation time |

`deltaGp` and `deltaQuantity` are authoritative. Consumers should calculate a
volume-weighted price from their sums rather than averaging `averagePrice`.
