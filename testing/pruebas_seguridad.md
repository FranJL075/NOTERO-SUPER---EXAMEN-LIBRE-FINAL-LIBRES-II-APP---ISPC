# Pruebas de Seguridad

Este documento describe escenarios de pruebas de seguridad para NOTERO SUPER.

| ID | Escenario | Herramienta | Resultado Esperado |
|----|-----------|-------------|--------------------|
| SEC-01 | Fuerza bruta contra `/token/` | `hydra`, `wfuzz` | Cuenta se bloquea tras *N* intentos o respuesta con *HTTP 401* constante |
| SEC-02 | Archivo `.txt` corrupto en *ImportTxtActivity* | Custom script | App rechaza archivo y registra error sin caerse |
| SEC-03 | JWT inválido/expirado en peticiones | Postman/intercept | API responde *HTTP 401* "Token is invalid or expired" |
| SEC-04 | SQL Injection en campo `nombre` de registro | `sqlmap` | Sin inyección; ORM parametriza consultas y devuelve *HTTP 400* si caracteres maliciosos |

> Todas las pruebas deben ejecutarse en un entorno aislado (`docker-compose`) y los resultados documentarse con capturas.
