package com.runemargin.sync;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TradeDeltaDetectorTest
{
    @Test
    public void detectsPartialFillDelta()
    {
        OfferSnapshot before = offer(0, 0);
        OfferSnapshot after = offer(25, 25_125);
        TradeDelta delta = TradeDeltaDetector.detect(before, after);

        assertEquals(25, delta.quantity);
        assertEquals(25_125, delta.gp);
    }

    @Test
    public void ignoresInitialSnapshot()
    {
        assertNull(TradeDeltaDetector.detect(null, offer(25, 25_125)));
    }

    @Test
    public void ignoresDuplicateEvent()
    {
        OfferSnapshot snapshot = offer(25, 25_125);
        assertNull(TradeDeltaDetector.detect(snapshot, snapshot));
    }

    @Test
    public void ignoresReusedSlotForDifferentOffer()
    {
        OfferSnapshot previous = offer(25, 25_125);
        OfferSnapshot replacement = new OfferSnapshot(556, 120, 500, 10, 1_200, true, "BUYING");
        assertNull(TradeDeltaDetector.detect(previous, replacement));
    }

    @Test
    public void supportsGpTotalsLargerThan32BitIntegers()
    {
        OfferSnapshot before = offer(1, 2_500_000_000L);
        OfferSnapshot after = offer(2, 5_000_000_000L);
        TradeDelta delta = TradeDeltaDetector.detect(before, after);

        assertEquals(1, delta.quantity);
        assertEquals(2_500_000_000L, delta.gp);
    }

    private static OfferSnapshot offer(int quantitySold, long spent)
    {
        return new OfferSnapshot(561, 1005, 100, quantitySold, spent, true, "BUYING");
    }
}
