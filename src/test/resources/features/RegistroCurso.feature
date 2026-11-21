# language: es
Característica: Registro de cursos
  Como administrador de la institución
  Quiero registrar cursos en el sistema
  Para que los estudiantes puedan inscribirse

  Escenario: Registro de curso válido
    Dado que tengo un curso llamado "Selenium Avanzado" con precio 400.0 y 20 vacantes
    Cuando envío la solicitud de registro del curso
    Entonces el sistema responde con código 200
    Y la respuesta devuelve el nombre "Selenium Avanzado"

  Escenario: Registro de curso con precio inválido
    Dado que tengo un curso llamado "Curso Gratis" con precio 0.0 y 10 vacantes
    Cuando envío la solicitud de registro del curso
    Entonces el sistema rechaza la solicitud con un código de error 4xx o 5xx