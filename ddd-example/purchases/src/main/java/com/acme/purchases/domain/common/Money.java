package com.acme.purchases.domain.common;

import java.util.Objects;

public final class Money {
    private final long amount; // cents
    private final Currency currency;

    public Money(long amount, Currency currency) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        this.amount = amount;
        this.currency = Objects.requireNonNull(currency);
    }

    public static Money of(long amount, Currency currency) { return new Money(amount, currency); }

    public long amount() { return amount; }
    public Currency currency() { return currency; }

    public Money add(Money other) {
        ensureSameCurrency(other);
        return new Money(this.amount + other.amount, this.currency);
    }

    public Money multiply(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("quantity must be >= 0");
        return new Money(this.amount * quantity, this.currency);
    }

    public int compareTo(Money other) {
        ensureSameCurrency(other);
        return Long.compare(this.amount, other.amount);
    }

    private void ensureSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) throw new IllegalArgumentException("Currency mismatch");
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money m)) return false;
        return amount == m.amount && currency == m.currency;
    }

    @Override public int hashCode() { return Objects.hash(amount, currency); }

    @Override public String toString() { return currency + " " + amount; }
}
