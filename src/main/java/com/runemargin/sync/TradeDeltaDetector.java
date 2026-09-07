package com.runemargin.sync;

final class TradeDeltaDetector
{
    private TradeDeltaDetector()
    {
    }

    static TradeDelta detect(OfferSnapshot previous, OfferSnapshot current)
    {
        if (previous == null || current == null || !current.sameOfferAs(previous))
        {
            return null;
        }

        int quantity = current.quantitySold - previous.quantitySold;
        int gp = current.spent - previous.spent;
        return quantity > 0 && gp > 0 ? new TradeDelta(quantity, gp) : null;
    }
}
