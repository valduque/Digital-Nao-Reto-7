package com.bookingmx.reservations;

import com.bookingmx.reservations.controller.ReservationController;
import com.bookingmx.reservations.dto.ReservationRequest;
import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import com.bookingmx.reservations.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationService service;

    @InjectMocks
    private ReservationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testList() throws Exception {
        Reservation r = new Reservation(
                1L,
                "Valeria",
                "Hotel MX",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3)
        );
        r.setStatus(ReservationStatus.ACTIVE);

        when(service.list()).thenReturn(List.of(r));

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].guestName").value("Valeria"))
                .andExpect(jsonPath("$[0].hotelName").value("Hotel MX"))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }

    @Test
    void testCreate() throws Exception {
        ReservationRequest req = new ReservationRequest(
                "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3)
        );

        Reservation saved = new Reservation(
                1L,
                req.getGuestName(),
                req.getHotelName(),
                req.getCheckIn(),
                req.getCheckOut()
        );
        saved.setStatus(ReservationStatus.ACTIVE);

        when(service.create(any())).thenReturn(saved);


        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "guestName": "Valeria",
                                  "hotelName": "Hotel MX",
                                  "checkIn": "%s",
                                  "checkOut": "%s"
                                }
                                """.formatted(
                                LocalDate.now().plusDays(1),
                                LocalDate.now().plusDays(3)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName").value("Valeria"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void testUpdate() throws Exception {
        ReservationRequest req = new ReservationRequest(
                "Val",
                "Nuevo Hotel",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(4)
        );

        Reservation updated = new Reservation(
                1L,
                req.getGuestName(),
                req.getHotelName(),
                req.getCheckIn(),
                req.getCheckOut()
        );
        updated.setStatus(ReservationStatus.ACTIVE);

        when(service.update(eq(1L), any())).thenReturn(updated);


        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "guestName": "Val",
                                  "hotelName": "Nuevo Hotel",
                                  "checkIn": "%s",
                                  "checkOut": "%s"
                                }
                                """.formatted(
                                LocalDate.now().plusDays(2),
                                LocalDate.now().plusDays(4)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName").value("Val"))
                .andExpect(jsonPath("$.hotelName").value("Nuevo Hotel"));
    }

    @Test
    void testCancel() throws Exception {
        Reservation canceled = new Reservation(
                1L,
                "Valeria",
                "Hotel MX",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3)
        );
        canceled.setStatus(ReservationStatus.CANCELED);

        when(service.cancel(1L)).thenReturn(canceled);

        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELED"));
    }
}
