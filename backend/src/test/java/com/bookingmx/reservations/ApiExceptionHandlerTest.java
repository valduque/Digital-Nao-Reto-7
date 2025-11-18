package com.bookingmx.reservations;

import com.bookingmx.reservations.exception.ApiExceptionHandler;
import com.bookingmx.reservations.exception.BadRequestException;
import com.bookingmx.reservations.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ApiExceptionHandler
 */
class ApiExceptionHandlerTest {

    private ApiExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ApiExceptionHandler();
    }

    @Test
    void testBadRequestHandler() {
        BadRequestException ex = new BadRequestException("Invalid data");

        ResponseEntity<?> response = handler.badRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Invalid data", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void testNotFoundHandler() {
        NotFoundException ex = new NotFoundException("Resource not found");

        ResponseEntity<?> response = handler.notFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("Resource not found", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void testGenericExceptionHandler() {
        // Probamos con cualquier excepción genérica
        Exception ex = new RuntimeException("Some random error");

        ResponseEntity<?> response = handler.generic(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body);
        assertEquals(500, body.get("status"));
        // Tu handler siempre retorna "Unexpected error" para excepciones genéricas
        assertEquals("Unexpected error", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void testGenericExceptionWithNullPointer() {
        // Probar con NullPointerException
        NullPointerException ex = new NullPointerException("Null value");

        ResponseEntity<?> response = handler.generic(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertEquals("Unexpected error", body.get("message"));
    }

    @Test
    void testErrorBodyStructure() {
        BadRequestException ex = new BadRequestException("Test");
        ResponseEntity<?> response = handler.badRequest(ex);

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body);
        assertTrue(body.containsKey("timestamp"));
        assertTrue(body.containsKey("status"));
        assertTrue(body.containsKey("message"));
        assertEquals(3, body.size()); // Solo 3 campos
    }

    @Test
    void testTimestampIsValidISO8601() {
        BadRequestException ex = new BadRequestException("Test");
        ResponseEntity<?> response = handler.badRequest(ex);

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        String timestamp = (String) body.get("timestamp");
        assertNotNull(timestamp);
        // Verificar que el timestamp tiene formato ISO-8601
        assertTrue(timestamp.matches("\\d{4}-\\d{2}-\\d{2}T.*"));
    }

    @Test
    void testDifferentExceptionMessages() {
        // Test múltiples mensajes para BadRequest
        BadRequestException ex1 = new BadRequestException("Message 1");
        BadRequestException ex2 = new BadRequestException("Message 2");

        ResponseEntity<?> resp1 = handler.badRequest(ex1);
        ResponseEntity<?> resp2 = handler.badRequest(ex2);

        @SuppressWarnings("unchecked")
        Map<String, Object> body1 = (Map<String, Object>) resp1.getBody();
        @SuppressWarnings("unchecked")
        Map<String, Object> body2 = (Map<String, Object>) resp2.getBody();

        assertEquals("Message 1", body1.get("message"));
        assertEquals("Message 2", body2.get("message"));
    }
}