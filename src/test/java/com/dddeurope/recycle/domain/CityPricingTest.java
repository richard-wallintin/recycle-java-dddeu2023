package com.dddeurope.recycle.domain;

import com.dddeurope.recycle.events.FractionWasDropped;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CityPricingTest {

    @Test
    void staticPricing() {
        Price price = new CityPricing().calculatePrice(new FractionWasDropped("xxx", "Construction waste", 10));

        assertThat(price).isEqualTo(new Price(150));
    }

    @Test
    void southParkPricingWithoutExemptions() {
        var southPark = new CityPricing(new Price(18), new Price(12));

        Price price = southPark.calculatePrice(new FractionWasDropped("xxx", "Construction waste", 10));
        assertThat(price).isEqualTo(new Price(180));
    }

    @Test @Disabled
    void exemptionsAreDeductedFromTheWeight() {

        CityPricing pricing = new CityPricing();

        pricing.calculatePrice(new Amount("Construction Waste", 80));


    }
}