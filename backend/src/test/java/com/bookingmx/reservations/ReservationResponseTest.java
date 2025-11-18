package com.bookingmx.reservations;

import com.bookingmx.reservations.dto.ReservationResponse;
import com.bookingmx.reservations.model.ReservationStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ReservationResponse DTO
 */
class ReservationResponseTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate checkIn = LocalDate.of(2025, 12, 1);
        LocalDate checkOut = LocalDate.of(2025, 12, 5);

        ReservationResponse response = new ReservationResponse(
                1L,
                "Valeria",
                "Hotel MX",
                checkIn,
                checkOut,
                ReservationStatus.ACTIVE
        );

        assertEquals(1L, response.getId());
        assertEquals("Valeria", response.getGuestName());
        assertEquals("Hotel MX", response.getHotelName());
        assertEquals(checkIn, response.getCheckIn());
        assertEquals(checkOut, response.getCheckOut());
        assertEquals(ReservationStatus.ACTIVE, response.getStatus());
    }

    @Test
    void testWithCanceledStatus() {
        ReservationResponse response = new ReservationResponse(
                2L,
                "Carlos",
                "Hotel ABC",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3),
                ReservationStatus.CANCELED
        );

        assertEquals(ReservationStatus.CANCELED, response.getStatus());
    }

    @Test
    void testAllFields() {
        LocalDate in = LocalDate.of(2025, 6, 15);
        LocalDate out = LocalDate.of(2025, 6, 20);

        ReservationResponse dto = new ReservationResponse(
                99L, "Guest Name", "Hotel Name", in, out, ReservationStatus.ACTIVE
        );

        assertNotNull(dto.getId());
        assertNotNull(dto.getGuestName());
        assertNotNull(dto.getHotelName());
        assertNotNull(dto.getCheckIn());
        assertNotNull(dto.getCheckOut());
        assertNotNull(dto.getStatus());
    }
}