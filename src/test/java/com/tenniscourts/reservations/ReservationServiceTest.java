package com.tenniscourts.reservations;

import com.tenniscourts.schedules.Schedule;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(10));
    }

    @Test
    public void getRefundValue3QuarterRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(1);

        schedule.setStartDateTime(startDateTime);

        Assert.assertTrue(reservationService.getRefundValue(Reservation.builder()
                .schedule(schedule)
                .value(new BigDecimal(10L))
                .build()).compareTo(new BigDecimal(7.5)) == 0);
    }

    @Test
    public void getRefundValueHalfRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(11);

        schedule.setStartDateTime(startDateTime);

        Assert.assertTrue(reservationService.getRefundValue(Reservation.builder()
                .schedule(schedule)
                .value(new BigDecimal(10L))
                .build()).compareTo(new BigDecimal(5)) == 0);
    }

    @Test
    public void getRefundValueQuarterRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusMinutes(10);

        schedule.setStartDateTime(startDateTime);

        Assert.assertTrue(reservationService.getRefundValue(Reservation.builder()
                .schedule(schedule)
                .value(new BigDecimal(10L))
                .build()).compareTo(new BigDecimal(2.5)) == 0);
    }

    @Test
    public void getRefundValueZeroRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().minusHours(11);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(0, reservationService.getRefundValue(Reservation.builder()
                .schedule(schedule)
                .value(new BigDecimal(10L))
                .build()).compareTo(new BigDecimal(0)));
    }

}