from fastapi import FastAPI, Depends, HTTPException, status
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy.orm import Session
from .database import Base, engine, get_db
from . import models, schemas, crud

Base.metadata.create_all(bind=engine)

app = FastAPI(title="PresuMarket API")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.post("/auth/register", response_model=schemas.UsuarioRead)
def register(usuario: schemas.UsuarioCreate, db: Session = Depends(get_db)):
    if crud.get_user_by_email(db, usuario.email):
        raise HTTPException(status_code=400, detail="Email ya registrado")
    return crud.create_user(db, usuario)

@app.post("/productos", response_model=schemas.ProductoRead)
def nuevo_producto(prod: schemas.ProductoCreate, db: Session = Depends(get_db)):
    return crud.create_producto(db, prod)

@app.get("/productos/{codigo}", response_model=schemas.ProductoRead)
def buscar_producto(codigo: str, db: Session = Depends(get_db)):
    prod = db.query(models.Producto).filter(models.Producto.codigo == codigo).first()
    if not prod:
        raise HTTPException(status_code=404, detail="Producto no encontrado")
    return prod

@app.post("/listas", response_model=schemas.ListaRead)
def crear_lista(lista: schemas.ListaCreate, user_id: int, db: Session = Depends(get_db)):
    return crud.create_lista(db, lista, user_id)

@app.post("/listas/{lista_id}/items", response_model=schemas.DetalleListaRead)
def agregar_item(lista_id: int, item: schemas.DetalleListaCreate, db: Session = Depends(get_db)):
    return crud.add_item(db, lista_id, item)
