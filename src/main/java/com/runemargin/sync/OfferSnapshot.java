package com.runemargin.sync;

final class OfferSnapshot
{
    final int itemId;
    final int offerPrice;
    final int totalQuantity;
    final int quantitySold;
    final int spent;
    final boolean buy;
    final String state;

    OfferSnapshot(
        int itemId,
        int offerPrice,
        int totalQuantity,
        int quantitySold,
        int spent,
        boolean buy,
        String state)
    {
        this.itemId = itemId;
        this.offerPrice = offerPrice;
        this.totalQuantity = totalQuantity;
        this.quantitySold = quantitySold;
        this.spent = spent;
        this.buy = buy;
        this.state = state;
    }

    boolean sameOfferAs(OfferSnapshot other)
    {
        return other != null
            && itemId == other.itemId
            && offerPrice == other.offerPrice
            && totalQuantity == other.totalQuantity
            && buy == other.buy;
    }
}
