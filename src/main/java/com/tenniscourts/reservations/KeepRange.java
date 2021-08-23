package com.tenniscourts.reservations;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum KeepRange {

    OVER_2400(24 * 60, new BigDecimal(1)),
    BETWEEN_1200_2359(12 * 60, new BigDecimal("0.75")),
    BETWEEN_0200_1159(2 * 60, new BigDecimal("0.5")),
    BETWEEN_0001_0159(0, new BigDecimal("0.25")),
    BELOW_ZERO(0, BigDecimal.ZERO),
    ;

    private final int startMinute;
    private final BigDecimal percentageToRefund;

}