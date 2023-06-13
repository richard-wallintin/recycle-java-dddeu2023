package com.dddeurope.recycle.domain;

import com.dddeurope.recycle.events.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriceCalculator {
    public PriceCalculator(Event... events) {
        this(Arrays.stream(events).toList());
    }

    public PriceCalculator(List<Event> events) {
        for (Event event : events) handle(event);
    }

    private final List<FractionWasDropped> fractionsDropped = new ArrayList<>();
    private String city;

    private void handle(Event event) {
        if (event instanceof IdCardRegistered cardRegistered) {
            // do something with cardRegistered
            city = cardRegistered.city();
        }
        if (event instanceof IdCardScannedAtEntranceGate cardScanned) {
            // do something with cardScanned
        }
        if (event instanceof FractionWasDropped fractionDropped) {
            fractionsDropped.add(fractionDropped);
        }
        if (event instanceof IdCardScannedAtExitGate cardScanned) {
            // do something with cardRegistered
        }
    }

    public Price calculatePrice(String cardId) {
        CityPricing pricing;
        if (city.equals("South Park")) {
            pricing = new CityPricing(new Price(18), new Price(12));
        } else {
            pricing = new CityPricing();
        }

        return fractionsDropped.stream().map(droppedFraction ->
                pricing.calculatePrice(droppedFraction)).reduce(Price::add).orElse(new Price(0));
    }

}
