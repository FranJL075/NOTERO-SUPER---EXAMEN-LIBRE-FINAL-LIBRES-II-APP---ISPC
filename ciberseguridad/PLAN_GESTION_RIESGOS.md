# Plan de Gestión de Riesgos

La siguiente matriz identifica los activos críticos, amenazas asociadas y controles de mitigación implementados conforme al ciclo de gestión de riesgos de ISO 27005.

| Activo | Amenaza | Probabilidad (1-5) | Impacto (1-5) | Nivel de Riesgo (PxI) | Mitigación |
|--------|---------|--------------------|---------------|-----------------------|------------|
| Base de datos SQLite (`db.sqlite3`) | Robo de credenciales | 3 | 5 | 15 | • Hash PBKDF2, 2FA en Django admin  
• Política de rotación de claves |
| API Django | Inyección de código | 2 | 4 | 8 | • Serializers con validación  
• Sanitización y uso de ORM |
| Archivo `lista.txt` | Carga de datos maliciosos | 4 | 3 | 12 | • Regex de whitelisting  
• Límite de tamaño |
| JWT | Suplantación de identidad | 2 | 5 | 10 | • Vida corta 60 min  
• Refresh tokens  
• Clave secreta en `.env` |
| Logs | Divulgación de datos sensibles | 3 | 4 | 12 | • Filtro que anonimiza  
• Acceso restringido |
| Aplicación Android | Ingeniería inversa | 2 | 4 | 8 | • Offuscación (R8)  
• ProGuard rules |

> Nivel de Riesgo: 1-5 (Bajo 1-4, Medio 5-14, Alto ≥ 15)

## Proceso de Revisión

1. Evaluación de riesgos semestral.  
2. Registro en _ISMS Risk Log_.  
3. Aprobación por el Comité de Seguridad.
