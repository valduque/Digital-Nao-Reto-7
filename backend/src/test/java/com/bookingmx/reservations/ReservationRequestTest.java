package com.bookingmx.reservations;

import com.bookingmx.reservations.dto.ReservationRequest;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ReservationRequestTest {

    @Test
    void testDtoFields() {
        LocalDate in = LocalDate.now().plusDays(1);
        LocalDate out = LocalDate.now().plusDays(2);

        ReservationRequest dto = new ReservationRequest("Val", "MX", in, out);

        assertEquals("Val", dto.getGuestName());
        assertEquals("MX", dto.getHotelName());
        assertEquals(in, dto.getCheckIn());
        assertEquals(out, dto.getCheckOut());
    }
}
