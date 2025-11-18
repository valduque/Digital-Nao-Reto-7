package com.bookingmx.reservations.repo;

import com.bookingmx.reservations.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test para ReservationRepository (implementación manual sin JPA)
 */
class ReservationRepositoryTest {

    private ReservationRepository repo;

    @BeforeEach
    void setUp() {
        // Instanciamos directamente porque es una clase concreta
        repo = new ReservationRepository();
    }

    @Test
    void testSaveAndFind() {
        Reservation r = new Reservation(
                null,
                "Valeria",
                "Hotel MX",
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 14)
        );

        Reservation saved = repo.save(r);

        assertNotNull(saved.getId());
        assertEquals("Valeria", saved.getGuestName());

        Optional<Reservation> found = repo.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Valeria", found.get().getGuestName());
    }

    @Test
    void testFindAll() {
        Reservation r1 = new Reservation(null, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        Reservation r2 = new Reservation(null, "Carlos", "Hotel ABC",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(4));

        repo.save(r1);
        repo.save(r2);

        List<Reservation> all = repo.findAll();

        assertEquals(2, all.size());
    }

    @Test
    void testDelete() {
        Reservation r = new Reservation(null, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        Reservation saved = repo.save(r);
        Long id = saved.getId();

        // Verificar que existe
        assertTrue(repo.findById(id).isPresent());

        // Eliminar
        repo.delete(id);

        // Verificar que ya no existe
        assertTrue(repo.findById(id).isEmpty());
    }

    @Test
    void testUpdate() {
        Reservation r = new Reservation(null, "Valeria", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        Reservation saved = repo.save(r);
        Long id = saved.getId();

        // Actualizar
        saved.setGuestName("Valeria Updated");
        repo.save(saved);

        // Verificar actualización
        Optional<Reservation> updated = repo.findById(id);
        assertTrue(updated.isPresent());
        assertEquals("Valeria Updated", updated.get().getGuestName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Reservation> notFound = repo.findById(999L);
        assertTrue(notFound.isEmpty());
    }

    @Test
    void testAutoIncrementId() {
        Reservation r1 = new Reservation(null, "Guest1", "Hotel1",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        Reservation r2 = new Reservation(null, "Guest2", "Hotel2",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        Reservation saved1 = repo.save(r1);
        Reservation saved2 = repo.save(r2);

        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());
        assertNotEquals(saved1.getId(), saved2.getId());
        assertEquals(saved1.getId() + 1, saved2.getId());
    }
}