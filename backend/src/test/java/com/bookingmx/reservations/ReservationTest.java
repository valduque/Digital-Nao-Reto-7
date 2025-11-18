package com.bookingmx.reservations;

import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para la entidad Reservation
 */
class ReservationTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate checkIn = LocalDate.of(2025, 12, 1);
        LocalDate checkOut = LocalDate.of(2025, 12, 5);

        Reservation r = new Reservation(1L, "Valeria", "Hotel MX", checkIn, checkOut);

        assertEquals(1L, r.getId());
        assertEquals("Valeria", r.getGuestName());
        assertEquals("Hotel MX", r.getHotelName());
        assertEquals(checkIn, r.getCheckIn());
        assertEquals(checkOut, r.getCheckOut());
        assertEquals(ReservationStatus.ACTIVE, r.getStatus());
    }

    @Test
    void testEmptyConstructor() {
        Reservation r = new Reservation();
        assertNull(r.getId());
        assertNull(r.getGuestName());
    }

    @Test
    void testSetters() {
        Reservation r = new Reservation();

        r.setId(10L);
        r.setGuestName("Carlos");
        r.setHotelName("Hotel ABC");
        r.setCheckIn(LocalDate.of(2025, 12, 10));
        r.setCheckOut(LocalDate.of(2025, 12, 15));
        r.setStatus(ReservationStatus.CANCELED);

        assertEquals(10L, r.getId());
        assertEquals("Carlos", r.getGuestName());
        assertEquals("Hotel ABC", r.getHotelName());
        assertEquals(LocalDate.of(2025, 12, 10), r.getCheckIn());
        assertEquals(LocalDate.of(2025, 12, 15), r.getCheckOut());
        assertEquals(ReservationStatus.CANCELED, r.getStatus());
    }

    @Test
    void testIsActive_whenActive() {
        Reservation r = new Reservation(1L, "Val", "Hotel",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertTrue(r.isActive());
    }

    @Test
    void testIsActive_whenCanceled() {
        Reservation r = new Reservation(1L, "Val", "Hotel",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        r.setStatus(ReservationStatus.CANCELED);

        assertFalse(r.isActive());
    }

    @Test
    void testEquals_sameId() {
        Reservation r1 = new Reservation(1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        Reservation r2 = new Reservation(1L, "Carlos", "Hotel ABC",
                LocalDate.now().plusDays(5), LocalDate.now().plusDays(7));

        assertEquals(r1, r2); // Mismo ID = iguales
    }

    @Test
    void testEquals_differentId() {
        Reservation r1 = new Reservation(1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        Reservation r2 = new Reservation(2L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertNotEquals(r1, r2); // Diferente ID = diferentes
    }

    @Test
    void testEquals_sameObject() {
        Reservation r1 = new Reservation(1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertEquals(r1, r1); // Mismo objeto
    }

    @Test
    void testEquals_null() {
        Reservation r1 = new Reservation(1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertNotEquals(r1, null);
    }

    @Test
    void testEquals_differentClass() {
        Reservation r1 = new Reservation(1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertNotEquals(r1, "String object");
    }

    @Test
    void testHashCode_sameId() {
        Reservation r1 = new Reservation(1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        Reservation r2 = new Reservation(1L, "Carlos", "Hotel ABC",
                LocalDate.now().plusDays(5), LocalDate.now().plusDays(7));

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testHashCode_differentId() {
        Reservation r1 = new Reservation(1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        Reservation r2 = new Reservation(2L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertNotEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testDefaultStatus() {
        Reservation r = new Reservation(null, "Val", "Hotel",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertEquals(ReservationStatus.ACTIVE, r.getStatus());
        assertTrue(r.isActive());
    }
}