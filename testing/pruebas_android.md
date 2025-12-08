# Pruebas Android (Aplicación Kotlin)

Este documento propone una batería de pruebas para la aplicación Android utilizando **Espresso** y pruebas unitarias con **JUnit4/Mockito**.

## 1. UI Tests ‑ Espresso

| Caso | Pantalla | Pasos | Resultado Esperado |
|------|----------|-------|--------------------|
| UI-01 | *SplashActivity* | Lanzar app | Se navega a *LoginActivity* tras 3 s |
| UI-02 | *LoginActivity* | Ingresar credenciales válidas y presionar «Ingresar» | Se muestra *HomeActivity* |
| UI-03 | *LoginActivity* | Credenciales inválidas | Se muestra `Snackbar` con texto «Credenciales incorrectas» |
| UI-04 | *HomeActivity* | Presionar ícono de perfil | Se navega a *PerfilActivity* |
| UI-05 | *ListasActivity* | Click FAB «+» | Se abre *ListaDetailActivity* en modo creación |
| UI-06 | *ImportTxtActivity* | Seleccionar archivo `.txt` malformado | Se muestra alerta «Formato no admitido» |
| UI-07 | *PromocionesActivity* | Seleccionar promoción | Se aplica descuento y se actualiza total |

> Todas las pruebas deberían ejecutarse con `ActivityScenarioRule` y *IdlingResources* para sincronizar llamadas a la API.

## 2. Unit Tests

### 2.1 Validación de formularios
* `LoginValidator.validate(email, password)`
  * Email vacío → `false`
  * Password < 8 chars → `false`
  * Formato email incorrecto → `false`
  * Datos correctos → `true`

### 2.2 Parseo del TXT de productos
`TxtParser.parse(InputStream)`
* Línea bien formada → `Producto` añadido
* Línea con código inexistente → entrada descartada y se registra en `errors`
* Archivo vacío → lista vacía y `errors` vacío
* Archivo > 10 MB → lanza `FileTooLargeException`

### 2.3 Cálculos de totales
`Lista.calculateTotal()`
* 3 items precio 100 c/u → total 300
* Aplicar descuento 10 % → total 270
* Cantidad 0 en item → no afecta total

### 2.4 Límites y bordes
* Máximo 1000 items por lista → excepción al superar
* Código de producto 128 car máx → error si excede
* Precio negativo → `IllegalArgumentException`
