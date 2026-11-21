package com.pruebas.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

/**
 * Pruebas de integración de /api/cursos - Rest Assured
 * Estudiante: Deborah Elena Vela Villamonte
 * Código:     I202120894
 * Curso:      2424 - Pruebas de Software - T6EO
 *
 * REQUISITO: la aplicación debe estar levantada en el puerto 8080.
 */
class CursoApiTest {

    @BeforeAll
    static void configuracion() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        System.out.println("Estudiante: Deborah Elena Vela Villamonte | Código: I202120894");
    }

    // ---------- Escenario 1, consultar todos los cursos ----------
    @Test
    @DisplayName("Escenario 1: GET /api/cursos devuelve 200 y al menos 3 cursos")
    void consultarTodosLosCursos() {
        given()
        .when()
            .get("/api/cursos")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(3));
    }

    // ---------- Escenario 2, consultar el curso con id 1 ----------
    @Test
    @DisplayName("Escenario 2: GET /api/cursos/1 devuelve 200, nombre y vacantes > 0")
    void consultarCursoPorId() {
        given()
        .when()
            .get("/api/cursos/1")
        .then()
            .statusCode(200)
            .body("nombre", equalTo("Pruebas de Software"))
            .body("vacantes", greaterThan(0));
    }
}