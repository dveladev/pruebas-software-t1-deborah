package com.pruebas.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Step definitions de RegistroCurso.feature
 * Estudiante: Deborah Elena Vela Villamonte
 * Código:     I202120894
 * Curso:      2424 - Pruebas de Software - T6EO
 *
 * REQUISITO: la aplicación debe estar levantada en el puerto 8080.
 */
public class RegistroCursoStepDefinitions {

    private String cuerpoJson;
    private Response response;

    @Dado("que tengo un curso llamado {string} con precio {double} y {int} vacantes")
    public void queTengoUnCurso(String nombre, Double precio, Integer vacantes) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        cuerpoJson = "{\"nombre\":\"" + nombre + "\","
                   + "\"precio\":" + precio + ","
                   + "\"vacantes\":" + vacantes + "}";
        System.out.println("Estudiante: Deborah Elena Vela Villamonte | Código: I202120894");
    }

    @Cuando("envío la solicitud de registro del curso")
    public void envioLaSolicitud() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(cuerpoJson)
                .when()
                .post("/api/cursos");
    }

    @Entonces("el sistema responde con código {int}")
    public void elSistemaResponde(Integer codigo) {
        assertEquals(codigo.intValue(), response.getStatusCode());
    }

    @Y("la respuesta devuelve el nombre {string}")
    public void laRespuestaDevuelveElNombre(String nombre) {
        assertEquals(nombre, response.jsonPath().getString("nombre"));
    }

    @Entonces("el sistema rechaza la solicitud con un código de error 4xx o 5xx")
    public void elSistemaRechaza() {
        int codigo = response.getStatusCode();
        assertTrue(codigo >= 400 && codigo <= 599,
                "Se esperaba un código 4xx o 5xx pero fue: " + codigo);
    }
}