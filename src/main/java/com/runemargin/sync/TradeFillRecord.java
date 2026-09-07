package com.runemargin.sync;

final class TradeFillRecord
{
    final int schemaVersion = 1;
    final String eventId;
    final String timestampUtc;
    final int itemId;
    final boolean buy;
    final int deltaQuantity;
    final int deltaGp;
    final long averagePrice;
    final int offerPrice;
    final int slot;
    final int cumulativeQuantity;
    final int totalQuantity;
    final String state;

    TradeFillRecord(
        String eventId,
        String timestampUtc,
        int itemId,
        boolean buy,
        int deltaQuantity,
        int deltaGp,
        long averagePrice,
        int offerPrice,
        int slot,
        int cumulativeQuantity,
        int totalQuantity,
        String state)
    {
        this.eventId = eventId;
        this.timestampUtc = timestampUtc;
        this.itemId = itemId;
        this.buy = buy;
        this.deltaQuantity = deltaQuantity;
        this.deltaGp = deltaGp;
        this.averagePrice = averagePrice;
        this.offerPrice = offerPrice;
        this.slot = slot;
        this.cumulativeQuantity = cumulativeQuantity;
        this.totalQuantity = totalQuantity;
        this.state = state;
    }
}
