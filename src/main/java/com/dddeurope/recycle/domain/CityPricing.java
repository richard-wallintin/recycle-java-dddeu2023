package com.dddeurope.recycle.domain;

import com.dddeurope.recycle.events.FractionWasDropped;

public class CityPricing {

    private final Price constructionWaste;
    private final Price greenWaste;

    public CityPricing() {
        this(new Price(15), new Price(9));
    }

    public CityPricing(Price constructionWaste, Price greenWaste) {

        this.constructionWaste = constructionWaste;
        this.greenWaste = greenWaste;
    }

    public Price calculatePrice(FractionWasDropped droppedFraction) {
        return calculatePrice(new Amount(droppedFraction));
    }

    public Price calculatePrice(Amount waste) {
        if (waste.fractionType().equals("Green waste")) {
            return greenWaste.multiply(waste.weight());
        } else {
            return constructionWaste.multiply(waste.weight());
        }
    }
}
