# Documentación del Frontend Android (Notero Super App)

## Arquitectura
Aplicación **Multi-Activity** basada en **MVVM ligero** (ViewModel opcional), consumo de backend mediante **Retrofit** y manejo de estado global mínimo vía `AuthInterceptor`. La caché local se resuelve opcionalmente con **Room**.

```
Splash → Login → Home
                 ↙︎  ↓  ↘︎
            Listas Promos Perfil
              ↓        ↓
      ListaDetail  ImportTxt
```

* Cada Activity es responsable de su IU y usa `Intent.putExtra()` para traspaso de datos.
* La navegación asegura siempre un camino de retorno a **HomeActivity**, evitando callejones.

## Activities y flujos
| Activity | Propósito | Entrada | Salida |
|----------|-----------|---------|--------|
| SplashActivity | Reproduce video introductorio y redirige a Login | — | LoginActivity |
| LoginActivity | Autenticación del usuario | Credentials | HomeActivity |
| RegistroActivity | Registro de nuevos usuarios | Datos de registro | LoginActivity |
| HomeActivity | Hub principal | — | Listas / Promos / Perfil / ImportTxt |
| ListasActivity | Listas históricas | — | ListaDetailActivity (listId) |
| ListaDetailActivity | Detalle de lista + imágenes + advertencias de presupuesto | listId | — |
| ImportTxtActivity | Selección de `lista.txt`, parsing, llamada `add-items-bulk` | — | Toast + navegación |
| PromocionesActivity | Catálogo de promociones en tiempo real | — | — |
| PerfilActivity | Datos del perfil y logout | — | — |

## Conexión a Backend
* **Retrofit** con `GsonConverterFactory`.
* `AuthInterceptor` agrega header `Authorization: Bearer <JWT>` en cada request.
* Endpoints principales: `/auth/login`, `/auth/register`, `/add-items-bulk`.

## Evidencias
Añadir capturas y GIFs aquí.
```

