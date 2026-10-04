package com.runemargin.sync;

final class TradeDelta
{
    final int quantity;
    final long gp;

    TradeDelta(int quantity, long gp)
    {
        this.quantity = quantity;
        this.gp = gp;
    }
}
