package com.bookingmx.reservations;

import com.bookingmx.reservations.model.ReservationStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para el enum ReservationStatus
 */
class ReservationStatusTest {

    @Test
    void testEnumValues() {
        ReservationStatus[] values = ReservationStatus.values();

        assertEquals(2, values.length);
        assertEquals(ReservationStatus.ACTIVE, values[0]);
        assertEquals(ReservationStatus.CANCELED, values[1]);
    }

    @Test
    void testValueOf() {
        assertEquals(ReservationStatus.ACTIVE, ReservationStatus.valueOf("ACTIVE"));
        assertEquals(ReservationStatus.CANCELED, ReservationStatus.valueOf("CANCELED"));
    }

    @Test
    void testEnumEquality() {
        ReservationStatus active1 = ReservationStatus.ACTIVE;
        ReservationStatus active2 = ReservationStatus.ACTIVE;
        ReservationStatus canceled = ReservationStatus.CANCELED;

        assertEquals(active1, active2);
        assertNotEquals(active1, canceled);
    }

    @Test
    void testEnumToString() {
        assertEquals("ACTIVE", ReservationStatus.ACTIVE.toString());
        assertEquals("CANCELED", ReservationStatus.CANCELED.toString());
    }
}