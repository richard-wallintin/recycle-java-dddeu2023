package com.dddeurope.recycle.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void asEuroDouble() {
        assertThat(new Price(0.75).asEuroDouble()).isEqualTo(0.75);
    }

    @Test
    void addingPricesWorks() {
        assertThat(new Price(10).add(new Price(3))).isEqualTo(new Price(13));
    }

    @Test
    void multiplyingPricesWorks() {
        assertThat(new Price(10).multiply(5)).isEqualTo(new Price(50));
    }
}