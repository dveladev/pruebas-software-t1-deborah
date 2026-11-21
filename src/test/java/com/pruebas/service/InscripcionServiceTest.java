package com.pruebas.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebas.model.Inscripcion;
import com.pruebas.model.services.InscripcionService;
import com.pruebas.repository.InscripcionRepository;

/**
 * Pruebas unitarias de InscripcionService - JUnit 5 + Mockito
 * Estudiante: Deborah Elena Vela Villamonte
 * Código:     I202120894
 * Curso:      2424 - Pruebas de Software - T6EO
 */
@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    @Mock
    private InscripcionRepository repository;

    @InjectMocks
    private InscripcionService service;

    @BeforeAll
    static void datosEstudiante() {
        System.out.println("Estudiante: Deborah Elena Vela Villamonte | Código: I202120894");
    }

    // ---------- Caso 1, inscribir correctamente ----------
    @Test
    @DisplayName("Caso 1: Inscribir estudiante I2024077 en el curso 3 correctamente")
    void inscribirEstudianteCorrectamente() {
        Inscripcion entrada = new Inscripcion();
        entrada.setCodigoEstudiante("I2024077");
        entrada.setCursoId(3L);

        Inscripcion guardada = new Inscripcion();
        guardada.setId(10L);
        guardada.setCodigoEstudiante("I2024077");
        guardada.setCursoId(3L);

        when(repository.existsByCodigoEstudianteAndCursoId("I2024077", 3L)).thenReturn(false);
        when(repository.save(entrada)).thenReturn(guardada);

        Inscripcion resultado = service.inscribir(entrada);

        assertNotNull(resultado);
        assertEquals("I2024077", resultado.getCodigoEstudiante());
        assertEquals(3L, resultado.getCursoId());
        assertEquals(10L, resultado.getId());
        verify(repository, times(1)).save(entrada);
    }

    // ---------- Caso 2, estudiante ya inscrito ----------
    @Test
    @DisplayName("Caso 2: Inscribir estudiante ya inscrito lanza excepción")
    void inscribirEstudianteYaInscrito() {
        Inscripcion entrada = new Inscripcion();
        entrada.setCodigoEstudiante("I2024001");
        entrada.setCursoId(1L);

        when(repository.existsByCodigoEstudianteAndCursoId("I2024001", 1L)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.inscribir(entrada));

        assertEquals("Estudiante ya inscrito", ex.getMessage());
        verify(repository, never()).save(any(Inscripcion.class));
    }

    // ---------- Caso 3, cancelar inscripción existente ----------
    @Test
    @DisplayName("Caso 3: Cancelar una inscripción existente (id 1)")
    void cancelarInscripcionExistente() {
        Inscripcion existente = new Inscripcion();
        existente.setId(1L);
        existente.setCodigoEstudiante("I2024001");
        existente.setCursoId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        assertDoesNotThrow(() -> service.cancelar(1L));
        verify(repository, times(1)).delete(existente);
    }
}