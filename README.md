# Notero Super App – Proyecto Full-Stack Móvil y Web

Este repositorio contiene **tres módulos principales** (Android nativo, API Django y App React Native) junto con toda la documentación y scripts de base de datos necesarios.

## Estructura del repositorio

```text
android/             → Aplicación Android escrita en Kotlin (antes `app_android`)
backend/             → API REST Django + DRF
frontend/            → App móvil Expo React Native
Documentación/       → Documentos funcionales, pruebas, ciberseguridad, diagramas
   ├─ Arquitectura.md
   ├─ Riesgos_ISO27001.md
   ├─ Matriz_Pruebas.md
   └─ …
database/            → Scripts SQL (schema y datos de ejemplo)
```

## Requisitos rápidos

| Módulo   | Stack                              | Requisitos |
|----------|------------------------------------|------------|
| android  | Kotlin + Gradle 8                  | JDK 17, Android Studio Flamingo |
| backend  | Python 3.11 + Django 4.2           | `pip install -r backend/requirements.txt` |
| frontend | Expo SDK 50 (React Native 0.73)    | `npm install` con Node 18 |

## Primeros pasos

### 1. Backend
```bash
python -m venv .venv && source .venv/bin/activate
pip install -r backend/requirements.txt
# Variables de entorno (ejemplo para SQLite)
export DJANGO_SECRET_KEY="changeme"
export DATABASE_URL="sqlite:///db.sqlite3"
python backend/manage.py migrate
python backend/manage.py runserver
```

### 2. Frontend (Expo)
```bash
cd frontend
npm install
npm run android   # ó ios / web según tu plataforma
```

### 3. Android nativo
```bash
cd app_android
./gradlew :app:installDebug
```

### Funcionalidades clave de la App Android

* Escaneo de código de barras/QR para añadir productos.
* Construcción de listas de presupuesto con límite configurable (se colorea en rojo al excederlo).
* Marcado/Desmarcado de productos favoritos (pulsación larga – se muestra "★").
* Modo “Negocio promocionado”: aplica un descuento global a la lista.
* Almacenamiento offline con Room → las listas se guardan automáticamente y pueden consultarse sin conexión.
* Actualización de precios desde el backend al volver a abrir la lista.

## Documentación
Todos los documentos (pruebas, políticas de seguridad, diagramas de navegación, etc.) se encuentran en el directorio `Documentación/`.

## Licencia
MIT – 2025 

## Cómo ejecutar el backend

```bash
cd backend
python -m venv venv && source venv/bin/activate  # Windows: venv\Scripts\activate
pip install -r requirements.txt
python manage.py migrate
python manage.py runserver
```

## Cómo ejecutar la app Android
1. Abrir `app_android` con Android Studio.
2. Sincronizar Gradle.
3. Iniciar emulador o dispositivo físico.
4. Ejecutar.

La app intenta acceder al backend en `http://10.0.2.2:8000/` (emulador). Modificar en `ApiService.kt` si fuera necesario.

## Pruebas
- Backend: `pytest` dentro de `backend/`.
- Android: `./gradlew connectedAndroidTest`. 