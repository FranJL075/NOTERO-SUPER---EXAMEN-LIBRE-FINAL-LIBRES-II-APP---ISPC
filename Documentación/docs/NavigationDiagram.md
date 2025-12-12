# Diagrama de navegación – NOTERO Super App (Android)

```mermaid
graph TD
    Splash((Splash)) --> Login((Login))
    Login -->|Login OK| Home((Home))
    Login --> Registro((Registro))
    Registro -->|Éxito| Login
    Home --> Listas[Listas]
    Listas --> ListaDetail[Detalle Lista]
    Home --> Promociones[Promociones]
    Home --> Perfil[Perfil]
    Home --> ImportTxt[Importar TXT]

    %% Caminos de retorno
    Listas --> Home
    ListaDetail --> Home
    Promociones --> Home
    Perfil --> Home
    ImportTxt --> Home
    Registro --> Home
```
