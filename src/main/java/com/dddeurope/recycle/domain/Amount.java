package com.dddeurope.recycle.domain;

import com.dddeurope.recycle.events.FractionWasDropped;

public record Amount(String fractionType, int weight) {
    public Amount(FractionWasDropped droppedFraction) {
        this(droppedFraction.fractionType(), droppedFraction.weight());
    }
}
