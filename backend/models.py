from sqlalchemy import Column, Integer, String, Numeric, Boolean, DateTime, ForeignKey, UniqueConstraint, Table
from sqlalchemy.orm import relationship
from datetime import datetime
from .database import Base

class Usuario(Base):
    __tablename__ = "usuarios"

    id_usuario = Column(Integer, primary_key=True, index=True)
    nombre = Column(String(80), nullable=False)
    email = Column(String(120), unique=True, nullable=False, index=True)
    contrasena_hash = Column(String(255), nullable=False)
    ubicacion = Column(String(150))

    listas = relationship("Lista", back_populates="usuario", cascade="all, delete-orphan")

class Producto(Base):
    __tablename__ = "productos"

    id_producto = Column(Integer, primary_key=True, index=True)
    nombre = Column(String(120), nullable=False)
    codigo = Column(String(64), unique=True, nullable=False, index=True)
    precio = Column(Numeric(10, 2), nullable=False)
    favorito = Column(Boolean, default=False)

    detalles = relationship("DetalleLista", back_populates="producto")

class Lista(Base):
    __tablename__ = "listas"

    id_lista = Column(Integer, primary_key=True, index=True)
    nombre = Column(String(120), nullable=False)
    limite_presupuesto = Column(Numeric(10, 2))
    total = Column(Numeric(10, 2), default=0)
    fecha_creacion = Column(DateTime, default=datetime.utcnow)

    id_usuario = Column(Integer, ForeignKey("usuarios.id_usuario"))
    usuario = relationship("Usuario", back_populates="listas")

    detalles = relationship("DetalleLista", back_populates="lista", cascade="all, delete-orphan")
    promociones = relationship("ListaPromocionada", back_populates="lista", cascade="all, delete-orphan")

class DetalleLista(Base):
    __tablename__ = "detalles_lista"

    id_detalle = Column(Integer, primary_key=True)
    id_lista = Column(Integer, ForeignKey("listas.id_lista"))
    id_producto = Column(Integer, ForeignKey("productos.id_producto"))
    cantidad = Column(Integer, nullable=False)
    subtotal = Column(Numeric(10, 2), nullable=False)

    lista = relationship("Lista", back_populates="detalles")
    producto = relationship("Producto", back_populates="detalles")

    __table_args__ = (
        UniqueConstraint("id_lista", "id_producto", name="uix_lista_producto"),
    )

class NegocioPromocionado(Base):
    __tablename__ = "negocios_promocionados"

    id_negocio = Column(Integer, primary_key=True, index=True)
    nombre = Column(String(120), nullable=False)
    descuento = Column(Numeric(5, 2), nullable=False)
    presupuesto_base = Column(Numeric(10, 2))
    direccion = Column(String(150))

    listas_promocionadas = relationship("ListaPromocionada", back_populates="negocio", cascade="all, delete-orphan")

class ListaPromocionada(Base):
    __tablename__ = "listas_promocionadas"

    id_lista = Column(Integer, ForeignKey("listas.id_lista"), primary_key=True)
    id_negocio = Column(Integer, ForeignKey("negocios_promocionados.id_negocio"), primary_key=True)

    lista = relationship("Lista", back_populates="promociones")
    negocio = relationship("NegocioPromocionado", back_populates="listas_promocionadas")
