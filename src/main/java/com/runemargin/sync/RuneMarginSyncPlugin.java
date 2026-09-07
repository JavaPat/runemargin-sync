package com.runemargin.sync;

import com.google.gson.Gson;
import com.google.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.UUID;
import net.runelite.api.GrandExchangeOffer;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.api.events.GrandExchangeOfferChanged;
import net.runelite.client.RuneLite;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PluginDescriptor(
    name = "RuneMargin Sync",
    description = "Records exact Grand Exchange fill totals locally for RuneMargin Tracker",
    tags = {"grand exchange", "ge", "prices", "flipping", "tracker", "runemargin"}
)
public class RuneMarginSyncPlugin extends Plugin
{
    private static final Logger LOG = LoggerFactory.getLogger(RuneMarginSyncPlugin.class);
    private static final int SLOT_COUNT = 8;
    private static final String DIRECTORY_NAME = "runemargin-sync";
    private static final String HISTORY_FILE_NAME = "trades-v1.jsonl";

    @Inject
    private Gson gson;

    private final OfferSnapshot[] previousOffers = new OfferSnapshot[SLOT_COUNT];
    private Path historyFile;

    @Override
    protected void startUp()
    {
        Path directory = RuneLite.RUNELITE_DIR.toPath().resolve(DIRECTORY_NAME);
        historyFile = directory.resolve(HISTORY_FILE_NAME);
        try
        {
            Files.createDirectories(directory);
        }
        catch (IOException exception)
        {
            LOG.warn("Unable to create the RuneMargin Sync directory", exception);
        }
    }

    @Override
    protected void shutDown()
    {
        for (int slot = 0; slot < previousOffers.length; slot++)
        {
            previousOffers[slot] = null;
        }
    }

    @Subscribe
    public void onGrandExchangeOfferChanged(GrandExchangeOfferChanged event)
    {
        int slot = event.getSlot();
        if (slot < 0 || slot >= SLOT_COUNT)
        {
            return;
        }

        GrandExchangeOffer offer = event.getOffer();
        if (offer == null || offer.getState() == GrandExchangeOfferState.EMPTY)
        {
            previousOffers[slot] = null;
            return;
        }

        OfferSnapshot current = snapshot(offer);
        TradeDelta delta = TradeDeltaDetector.detect(previousOffers[slot], current);
        previousOffers[slot] = current;
        if (delta == null)
        {
            return;
        }

        long averagePrice = Math.round((double) delta.gp / delta.quantity);
        TradeFillRecord record = new TradeFillRecord(
            UUID.randomUUID().toString(),
            Instant.now().toString(),
            current.itemId,
            current.buy,
            delta.quantity,
            delta.gp,
            averagePrice,
            current.offerPrice,
            slot,
            current.quantitySold,
            current.totalQuantity,
            current.state);
        append(record);
    }

    private static OfferSnapshot snapshot(GrandExchangeOffer offer)
    {
        GrandExchangeOfferState state = offer.getState();
        boolean buy = state == GrandExchangeOfferState.BUYING
            || state == GrandExchangeOfferState.BOUGHT
            || state == GrandExchangeOfferState.CANCELLED_BUY;
        return new OfferSnapshot(
            offer.getItemId(),
            offer.getPrice(),
            offer.getTotalQuantity(),
            offer.getQuantitySold(),
            offer.getSpent(),
            buy,
            state.name());
    }

    private void append(TradeFillRecord record)
    {
        if (historyFile == null)
        {
            return;
        }

        String line = gson.toJson(record) + System.lineSeparator();
        try
        {
            Files.write(
                historyFile,
                line.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND);
        }
        catch (IOException exception)
        {
            LOG.warn("Unable to append a RuneMargin Grand Exchange fill", exception);
        }
    }
}
