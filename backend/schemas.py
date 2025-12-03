from datetime import datetime
from typing import List, Optional
from pydantic import BaseModel, EmailStr, Field

class UsuarioBase(BaseModel):
    nombre: str
    email: EmailStr
    ubicacion: Optional[str] = None

class UsuarioCreate(UsuarioBase):
    contrasena: str = Field(min_length=6)

class UsuarioRead(UsuarioBase):
    id_usuario: int
    class Config:
        orm_mode = True

class ProductoBase(BaseModel):
    nombre: str
    codigo: str
    precio: float

class ProductoCreate(ProductoBase):
    pass

class ProductoRead(ProductoBase):
    id_producto: int
    favorito: bool
    class Config:
        orm_mode = True

class DetalleListaCreate(BaseModel):
    id_producto: int
    cantidad: int

class DetalleListaRead(BaseModel):
    id_detalle: int
    producto: ProductoRead
    cantidad: int
    subtotal: float
    class Config:
        orm_mode = True

class ListaBase(BaseModel):
    nombre: str
    limite_presupuesto: Optional[float] = None

class ListaCreate(ListaBase):
    pass

class ListaRead(ListaBase):
    id_lista: int
    total: float
    fecha_creacion: datetime
    detalles: List[DetalleListaRead] = []
    class Config:
        orm_mode = True
