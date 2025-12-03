from sqlalchemy.orm import Session
from passlib.context import CryptContext
from . import models, schemas

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

def get_password_hash(password: str) -> str:
    return pwd_context.hash(password)

def verify_password(password: str, hashed: str) -> bool:
    return pwd_context.verify(password, hashed)

def create_user(db: Session, user: schemas.UsuarioCreate) -> models.Usuario:
    db_user = models.Usuario(
        nombre=user.nombre,
        email=user.email,
        contrasena_hash=get_password_hash(user.contrasena),
        ubicacion=user.ubicacion,
    )
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    return db_user

def get_user_by_email(db: Session, email: str):
    return db.query(models.Usuario).filter(models.Usuario.email == email).first()

def create_producto(db: Session, prod: schemas.ProductoCreate) -> models.Producto:
    db_prod = models.Producto(**prod.model_dump())
    db.add(db_prod)
    db.commit()
    db.refresh(db_prod)
    return db_prod

def create_lista(db: Session, lista: schemas.ListaCreate, user_id: int) -> models.Lista:
    db_lista = models.Lista(nombre=lista.nombre, limite_presupuesto=lista.limite_presupuesto, id_usuario=user_id)
    db.add(db_lista)
    db.commit()
    db.refresh(db_lista)
    return db_lista

def add_item(db: Session, lista_id: int, detalle: schemas.DetalleListaCreate):
    prod = db.query(models.Producto).get(detalle.id_producto)
    subtotal = prod.precio * detalle.cantidad
    det = models.DetalleLista(id_lista=lista_id, id_producto=prod.id_producto, cantidad=detalle.cantidad, subtotal=subtotal)
    db.add(det)
    db.commit()
    db.refresh(det)
    _recalcular_total(db, lista_id)
    return det

def _recalcular_total(db: Session, lista_id: int):
    total = db.query(models.DetalleLista).filter_by(id_lista=lista_id).with_entities(models.DetalleLista.subtotal).all()
    suma = sum(t[0] for t in total)
    db.query(models.Lista).filter_by(id_lista=lista_id).update({"total": suma})
    db.commit()
