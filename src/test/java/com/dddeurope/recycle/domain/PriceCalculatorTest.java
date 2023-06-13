package com.dddeurope.recycle.domain;

import com.dddeurope.recycle.events.FractionWasDropped;
import com.dddeurope.recycle.events.IdCardRegistered;
import com.dddeurope.recycle.events.IdCardScannedAtEntranceGate;
import com.dddeurope.recycle.events.IdCardScannedAtExitGate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceCalculatorTest {

    private final IdCardRegistered idCardRegistered = new IdCardRegistered("123", "John Doe", "an address", "a street");
    private final IdCardScannedAtEntranceGate idCardScannedAtEntranceGate = new IdCardScannedAtEntranceGate("123", "2023-01-01");
    private final IdCardScannedAtExitGate idCardScannedAtExitGate = new IdCardScannedAtExitGate("123");

    private final IdCardRegistered idCardRegisteredSP = new IdCardRegistered("456", "Randy Marsh", "an address", "South Park");
    private final IdCardScannedAtEntranceGate idCardScannedAtEntranceGateSP = new IdCardScannedAtEntranceGate("456", "2023-01-01");
    private final IdCardScannedAtExitGate idCardScannedAtExitGateSP = new IdCardScannedAtExitGate("456");


    @Test
    void visitWithoutDroppingWaste() {
        PriceCalculator calculator = new PriceCalculator(
                idCardRegistered,
                idCardScannedAtEntranceGate,
                idCardScannedAtExitGate
        );

        assertThat(calculator.calculatePrice("123")).isEqualTo(new Price(0));
    }

    @Test
    void visitWith71kgOfConstructionWaste() {
        PriceCalculator calculator = new PriceCalculator(
                idCardRegistered,
                idCardScannedAtEntranceGate,
                new FractionWasDropped("123", "Construction waste", 71),
                idCardScannedAtExitGate
        );

        assertThat(calculator.calculatePrice("123")).isEqualTo(new Price(10.65));
    }

    @Test
    void visitWithMultipleDifferentWasteTypes() {
        PriceCalculator calculator = new PriceCalculator(
                idCardRegistered,
                idCardScannedAtEntranceGate,
                new FractionWasDropped("123", "Construction waste", 51),
                new FractionWasDropped("123", "Green waste", 23),
                idCardScannedAtExitGate
        );

        assertThat(calculator.calculatePrice("123")).isEqualTo(new Price(9.72));
    }

    @Test
    void constructionWasteIsMoreExpensiveInSouthPark() {
        PriceCalculator calculator = new PriceCalculator(
                idCardRegisteredSP,
                idCardScannedAtEntranceGateSP,
                new FractionWasDropped("123", "Construction waste", 10),
                idCardScannedAtExitGateSP
        );

        assertThat(calculator.calculatePrice("123")).isEqualTo(new Price(1.8));
    }
}