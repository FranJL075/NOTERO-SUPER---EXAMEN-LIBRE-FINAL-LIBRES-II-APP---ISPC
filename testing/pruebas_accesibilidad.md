# Pruebas de Accesibilidad – Ley 26.653

| ID | Funcionalidad | Paso a paso | Resultado esperado | Resultado obtenido | Riesgo |
|----|---------------|-------------|--------------------|--------------------|--------|
| AC1 | TalkBack – Home | Activar TalkBack → abrir app → enfocar FAB “Nueva lista”. | El lector anuncia: “Crear nueva lista, botón”. |  | Bajo |
| AC2 | TalkBack – Lista | En ListaActivity, enfocar botón “Aplicar Promo”. | Lector anuncia: “Aplicar descuento del negocio, botón”. |  | Bajo |
| AC3 | Contraste de alerta | Crear límite 100 $ y agregar productos hasta superar. | Texto Total se vuelve rojo (`holo_red_dark`) y mantiene contraste AA. |  | Medio |
| AC4 | Texto grande | Activar “Texto grande” → abrir ListaActivity. | Elementos se adaptan sin cortar ni solapar texto. |  | Bajo |
| AC5 | Navegación por teclado | Con teclado Bluetooth/Emulador Tab ↔️, recorrer Home. | Se puede llegar a cada botón (foco visible) y activar con Enter. |  | Bajo |

> Completar la columna “Resultado obtenido” durante la ejecución y adjuntar capturas en la Wiki.
