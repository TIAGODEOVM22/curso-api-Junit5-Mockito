package br.com.tiago.api.resources.excepetions;

import br.com.tiago.api.services.exceptions.DataIntegratyViolationException;
import br.com.tiago.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExcepetionHandlerTest {

    @InjectMocks
    private ResourceExcepetionHandler excepetionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void whenobjectNotFoundExceptionThenReturnResponseEntity() {

        ResponseEntity<StandardError> response = excepetionHandler
                .objectNotFound(new ObjectNotFoundException("Objeto não encontrado"),
                        new MockHttpServletRequest());

        assertNotNull((response));
        assertNotNull((response.getBody()));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Objeto não encontrado", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());

        /*não será igual ao path que vem no meu response*/
        assertNotEquals("/usuario/2", response.getBody().getPath());

        /*não será igual ao timestamp do meu response*/
        assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());

    }

    @Test
    void dataIntegratyViolationException() {

        ResponseEntity<StandardError> response = excepetionHandler
                .dataIntegratyViolationException(new DataIntegratyViolationException("Email ja cadastrado"),
                        new MockHttpServletRequest());

        assertNotNull((response));
        assertNotNull((response.getBody()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Email ja cadastrado", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());


    }
}