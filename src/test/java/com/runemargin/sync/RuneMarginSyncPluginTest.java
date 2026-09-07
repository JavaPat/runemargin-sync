package com.runemargin.sync;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RuneMarginSyncPluginTest
{
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(RuneMarginSyncPlugin.class);
        RuneLite.main(args);
    }
}
