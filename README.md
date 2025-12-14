<img width="500" height="500" alt="Logo LiSTOP!" src="https://github.com/user-attachments/assets/756b7443-9692-43dd-95e4-1ad6ac47eb4a" />

# 🛒 LiSTOP! – Aplicación móvil para presupuestos de supermercado

LiSTOP! es una aplicación móvil nativa para Android diseñada para ayudarte a organizar tus compras del supermercado, controlar tus gastos, crear presupuestos inteligentes y escanear productos mediante códigos de barras o QR.

Es rápida, intuitiva, accesible y pensada para resolver un problema real: **evitar sorpresas al llegar a la caja**, permitiéndote saber con exactitud cuánto vas gastando.

---

## 🚀 Características principales

### 🛍️ Gestión inteligente de listas
- Crear listas de compras personalizadas.
- Agregar productos ingresando el código o escaneándolo.
- Modificar cantidades.
- Eliminar productos.
- Marcar productos como favoritos/prioritarios.
- Guardar listas en tu perfil.

### 📸 Escaneo de productos
- Escaneo de código de barras o QR.
- Lectura rápida y segura.
- Identificación automática del producto (si existe en la base de datos).

### 💰 Control de presupuesto
- Establecer un límite de gasto.
- Totales y subtotales calculados en tiempo real.
- Alertas al superar el presupuesto fijado.

### 🏷️ Modo “Negocio Promocionado”
Un segundo modo especial donde:
- Se aplican descuentos automáticamente.
- Se muestra el “ahorro total”.
- Se establece un presupuesto base según el comercio.
- Permite crear listas promocionadas con sus beneficios correspondientes.

### 👤 Perfil y configuración
- Registro e inicio de sesión mediante Firebase Authentication.
- Guardado de listas previas.
- Configuración de ubicación.
- Edición de datos del usuario.
- Cerrar sesión.

---

## 🧩 Tecnologías utilizadas

- **Android Studio**
- **Java**
- **XML (UI layouts)**
- **Firebase Authentication**
- **Firebase Database / Firestore**
- **SQLite (persistencia offline)**
- **ML Kit / ZXing** para escaneo de códigos
- **GitHub** para control de versiones

---

## 📡 Funciona offline
LiSTOP! utiliza SQLite para trabajar sin conexión.  
Cuando vuelve el Internet, sincroniza automáticamente con Firebase.

---

## 📱 Navegación de la App

1. Pantalla de Inicio  
2. Login / Registro  
3. Página Principal  
   - Mis Listas  
   - Negocio Promocionado  
   - Perfil  
4. Gestión de Listas  
5. Perfil y configuración  

El esquema visual completo se encuentra en la documentación IEEE-830.

---

## 📄 Documentación del Proyecto

En este repositorio encontrarás:

### 📘 **IEEE-830 completo**
Incluye requisitos funcionales, no funcionales, diagramas, flujo de navegación y anexos.

### 🧪 **Documento de Testing**
- Plan de pruebas  
- Casos de prueba  
- Matriz de trazabilidad  
- Pruebas de accesibilidad (Ley 26.653)  
- Pruebas del módulo Negocio Promocionado  

### 📚 **Documentos adicionales**
- Arquitectura del sistema  
- Estructura de la base de datos  
- Manual de usuario (si aplica)

> Todos disponibles en la carpeta `/docs`.

---

## 🎥 Videos del Proyecto

*(Reemplazar los enlaces con los correspondientes cuando los tengas)*

- Video técnico del código → [link]  
- Video de accesibilidad → [link]  
- Video de navegación → [link]  
- Explicación general del proyecto → [link]  

---

## 📥 Descargas

- **APK – LiSTOP! v1.0** → [link]  
- **ZIP del proyecto exportado** → [link]  
- **Drive con la documentación completa** → [link]

---

## 📦 Instalación

1. Descargar el archivo `LiSTOP! v1.0.apk`
2. Activar “Instalar apps de orígenes desconocidos”
3. Instalar en dispositivo Android 7+  
4. Crear cuenta o iniciar sesión  
5. ¡Listo para usar!
