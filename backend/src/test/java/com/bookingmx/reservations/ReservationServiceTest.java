package com.bookingmx.reservations;

import com.bookingmx.reservations.dto.ReservationRequest;
import com.bookingmx.reservations.exception.BadRequestException;
import com.bookingmx.reservations.exception.NotFoundException;
import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import com.bookingmx.reservations.repo.ReservationRepository;

import com.bookingmx.reservations.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ReservationRepository repo;

    @InjectMocks
    ReservationService service;

    // ============================================================
    // CREATE
    // ============================================================

    @Test
    void createReservation_success() {
        LocalDate in = LocalDate.now().plusDays(2);
        LocalDate out = LocalDate.now().plusDays(5);

        ReservationRequest req = new ReservationRequest(
                "Valeria", "Hotel Palace", in, out
        );

        Reservation saved = new Reservation(1L, "Valeria", "Hotel Palace", in, out);

        when(repo.save(any(Reservation.class))).thenReturn(saved);

        Reservation result = service.create(req);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Valeria", result.getGuestName());
        verify(repo).save(any(Reservation.class));
    }

    @Test
    void createReservation_invalidDates_throwsException() {
        ReservationRequest req = new ReservationRequest(
                "Valeria", "Hotel",
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(1) // out < in
        );

        assertThrows(BadRequestException.class, () -> service.create(req));
        verify(repo, never()).save(any());
    }

    @Test
    void createReservation_checkInPast_throwsException() {
        ReservationRequest req = new ReservationRequest(
                "Valeria", "Hotel",
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(3)
        );

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    @Test
    void createReservation_checkOutPast_throwsException() {
        ReservationRequest req = new ReservationRequest(
                "Valeria", "Hotel",
                LocalDate.now().plusDays(1),
                LocalDate.now().minusDays(2)
        );

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    @Test
    void createReservation_nullDates_throwsException() {
        ReservationRequest req = new ReservationRequest(
                "Valeria", "Hotel",
                null,
                LocalDate.now().plusDays(3)
        );

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Test
    void updateReservation_success() {
        LocalDate newIn = LocalDate.now().plusDays(3);
        LocalDate newOut = LocalDate.now().plusDays(7);

        Reservation existing = new Reservation(
                1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2)
        );

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ReservationRequest req = new ReservationRequest(
                "Vale Updated", "Hotel New", newIn, newOut
        );

        Reservation result = service.update(1L, req);

        assertEquals("Vale Updated", result.getGuestName());
        assertEquals("Hotel New", result.getHotelName());
        assertEquals(newIn, result.getCheckIn());
        assertEquals(newOut, result.getCheckOut());
        verify(repo).save(existing);
    }

    @Test
    void updateReservation_notFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        ReservationRequest req = new ReservationRequest(
                "X", "Y",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(5)
        );

        assertThrows(NotFoundException.class, () -> service.update(99L, req));
        verify(repo, never()).save(any());
    }

    @Test
    void updateReservation_canceledReservation_throwsException() {
        Reservation canceled = new Reservation(
                1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(4)
        );
        canceled.setStatus(ReservationStatus.CANCELED);

        when(repo.findById(1L)).thenReturn(Optional.of(canceled));

        ReservationRequest req = new ReservationRequest(
                "New", "New",
                LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(6)
        );

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
        verify(repo, never()).save(any());
    }

    @Test
    void updateReservation_invalidDates_throwsException() {
        Reservation existing = new Reservation(
                1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(4)
        );

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        ReservationRequest req = new ReservationRequest(
                "Val", "H",
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(1) // invalid (out < in)
        );

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
        verify(repo, never()).save(any());
    }

    // ============================================================
    // CANCEL
    // ============================================================

    @Test
    void cancelReservation_success() {
        Reservation r = new Reservation(
                1L, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(4)
        );

        when(repo.findById(1L)).thenReturn(Optional.of(r));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Reservation result = service.cancel(1L);

        assertEquals(ReservationStatus.CANCELED, result.getStatus());
        verify(repo).save(r);
    }

    @Test
    void cancelReservation_notFound() {
        when(repo.findById(88L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.cancel(88L));
        verify(repo, never()).save(any());
    }

    @Test
    void cancelReservation_alreadyCanceled() {
        Reservation r = new Reservation(
                1L, "Val", "Hotel MX",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(4)
        );
        r.setStatus(ReservationStatus.CANCELED);

        when(repo.findById(1L)).thenReturn(Optional.of(r));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Reservation result = service.cancel(1L);

        assertEquals(ReservationStatus.CANCELED, result.getStatus());
        verify(repo).save(r);
    }

    // ============================================================
    // LIST
    // ============================================================

    @Test
    void listReservations_success() {
        List<Reservation> list = Arrays.asList(
                new Reservation(
                        1L, "Valeria", "Hotel MX",
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(3)
                )
        );

        when(repo.findAll()).thenReturn(list);

        List<Reservation> result = service.list();

        assertEquals(1, result.size());
        verify(repo).findAll();
    }
}


