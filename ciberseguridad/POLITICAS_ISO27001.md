# Políticas de Seguridad de la Información

**Basado en las normas ISO/IEC 27001:2022 e ISO/IEC 27002:2022**

## A.9 – Gestión de accesos

1. **Autenticación basada en JWT**  
   • Los usuarios deben autenticarse mediante tokens JWT firmados con clave secreta robusta.  
   • La expiración máxima del token será de 60 minutos.  
   • Los tokens se transmiten exclusivamente a través de HTTPS y se almacenan en memoria (no en _localStorage_).

2. **Gestión de contraseñas**  
   • Las contraseñas se almacenan con hashing con _salt_ utilizando PBKDF2 (configuración por defecto de Django).  
   • Se exige un mínimo de 12 caracteres con complejidad.

3. **Principio de privilegio mínimo**  
   • Todos los endpoints usan la clase de permiso `IsAuthenticated` por defecto y controles de objeto (`IsOwner`).

---

## A.12 – Seguridad operacional

1. **Registros (Logs)**  
   • Se activa un _logger_ dedicado que **anonimiza** campos sensibles (`Authorization`, `password`, `token`).  
   • Se conservan 90 días y se rotan semanalmente.

2. **Validaciones y Sanitización**  
   • Cada entrada de usuario (códigos, txt importado, formularios web) se valida contra expresiones regulares.  
   • Se emplea la librería `bleach` en el frontend para neutralizar XSS.

---

## A.14 – Seguridad en desarrollo y soporte

1. **Controles de entrada**  
   • Se definen validadores en _serializers_ y en el `ImportTxtActivity` para permitir únicamente `[A-Za-z0-9]`.  
   • Se rechazan líneas vacías o sobre-dimensionadas.

2. **API Segura**  
   • Se usa `rest_framework_simplejwt`.  
   • Todas las rutas bajo `/api/` emplean HTTPS y CORS restringido.

---

## A.18 – Cumplimiento

1. **Protección de datos personales (Ley 25.326/UE GDPR)**  
   • Los datos recopilados se limitan a correo, nombre y ubicación, necesarios para la operación.  
   • Los usuarios pueden ejercer derechos de acceso y borrado vía `DELETE /api/user/{id}`.

2. **Licencias de software**  
   • Se mantiene `requirements.txt` y `package.json` con licencias compatibles MIT/Apache-2.0.

---

## Riesgos y Mitigaciones – _lista.txt_

| Riesgo | Descripción | Probabilidad | Impacto | Mitigación |
|--------|-------------|--------------|---------|-----------|
| Inyección de comandos | El archivo `lista.txt` podría contener caracteres de escape o scripts | Media | Alta | Sanitizar cada línea con RegEx `^[A-Za-z0-9]{3,128}$` y rechazar entradas inválidas |
| Datos malformados | Códigos incompletos o muy largos rompen la lógica | Alta | Media | Validar longitud (3-128) y formato antes de procesar |
| Filtración de datos en logs | Los códigos podrían incluir datos sensibles | Baja | Alta | Registrar solo el conteo de ítems, nunca el contenido |
| Denegación de servicio | `lista.txt` extremadamente grande (>10 k líneas) | Baja | Alta | Limitar a 1 000 líneas y 1 MB por importación |
