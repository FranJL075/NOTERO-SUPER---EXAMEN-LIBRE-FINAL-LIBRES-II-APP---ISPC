# Arquitectura General

## 1. Diagrama de Clases (PlantUML)

```plantuml
@startuml
class Usuario {
  +id: Int
  +email: String
  +password: String
  +nombre: String
  +ubicacion: String
}
class Producto {
  +id: Int
  +nombre: String
  +codigo: String
  +precio: Decimal
  +favorito: Boolean
}
class Lista {
  +id: Int
  +nombre: String
  +limitePresupuesto: Decimal
  +total: Decimal
  +fechaCreacion: Date
  +esPromocion: Boolean
}
class DetalleLista {
  +id: Int
  +cantidad: Int
  +precio_unitario: Decimal
  +subtotal(): Decimal
}
class NegocioPromocionado {
  +id: Int
  +nombre: String
  +descuento: Decimal
  +presupuestoBase: Decimal
  +direccion: String
}
Usuario "1" -- "*" Lista
Lista "1" -- "*" DetalleLista
Producto "1" -- "*" DetalleLista
NegocioPromocionado "1" -- "*" Lista
@enduml
```

## 2. Modelo Entidad–Relación (texto)
- Usuario 1..* Lista
- Lista 1..* DetalleLista
- Producto M..N Lista (a través de DetalleLista)
- NegocioPromocionado 1..* Lista

## 3. Flujo de Navegación Android
```
Splash -> (token?) -> Login -> Home (BottomNav)
   Home.tabs:
     - ListaFragment -> DetalleLista
     - PromocionesFragment
     - PerfilFragment
```
