package com.dddeurope.recycle.domain;

public record Price(int cents) {
    public Price(double amountInEuros) {
        this((int) (amountInEuros * 100));
    }

    public double asEuroDouble() {
        return ((double) cents) / 100;
    }

    public Price add(Price price) {
        return new Price(this.cents + price.cents);
    }

    public Price multiply(int factor) {
        return new Price(cents * factor);
    }
}
