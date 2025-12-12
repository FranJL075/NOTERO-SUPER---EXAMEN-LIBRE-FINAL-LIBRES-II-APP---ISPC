# Buenas Prácticas de Código Seguro

## 1. Sanitización de Inputs

- **Backend (Django):** Utilizar validadores (`RegexValidator`) en los _serializers_ para aceptar solo caracteres permitidos.  
- **Android/React:** Aplicar expresiones regulares antes de enviar datos a la API. Evitar concatenaciones directas en queries.

## 2. Validación de archivo TXT

1. Límite 1 000 líneas y 1 MB de tamaño.  
2. Expresión regular `^[A-Za-z0-9]{3,128}$`.  
3. Rechazar líneas vacías o duplicadas.  
4. Registrar solo la cantidad de ítems válidos.

## 3. Evitar Filtración de Datos en Logs

- No registrar tokens, contraseñas ni datos personales.  
- Configurar `LOGGING` con filtro personalizado `SensitiveDataFilter`.  
- Rotación semanal y acceso restringido.

## 4. Manejo Seguro de JWT

- Vida útil máxima 60 min y _refresh_ 24 h.  
- Firmados con algoritmo **HS256** y clave en variable de entorno.  
- En el cliente, almacenar en memoria/`SecureStore` (Android) y no en _localStorage_.

## 5. Uso de Permisos en Backend

- `DEFAULT_PERMISSION_CLASSES = IsAuthenticated`.  
- Permisos por objeto (`IsOwner`).  
- Evitar filtraciones usando filtros `queryset = ... filter(usuario=self.request.user)`.
