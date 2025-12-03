# PresuMarket – App de presupuestos de supermercado

Este repositorio contiene un **backend FastAPI + PostgreSQL** y la guía para levantar el front-end móvil con Flutter.

## Estructura
```
backend/            → código FastAPI
  ├─ database.py    → conexión SQLAlchemy
  ├─ models.py      → modelos ORM
  ├─ schemas.py     → Pydantic
  ├─ crud.py        → operaciones básicas
  ├─ main.py        → punto de entrada (uvicorn)
  └─ requirements.txt

db/schema.sql       → script SQL 3FN
```

## Instalación rápida del backend
```bash
python -m venv .venv && source .venv/bin/activate
pip install -r backend/requirements.txt
# Exporta la URL de tu base
export DATABASE_URL="postgresql+psycopg2://user:pass@localhost:5432/presupuesto_db"
# Crear tablas
python - <<'PY'
from backend.database import Base, engine
import backend.models  # noqa: F401 – registra modelos
Base.metadata.create_all(bind=engine)
PY
# Correr servidor
uvicorn backend.main:app --reload
```

## Siguiente paso (front-end)
1. `flutter create presu_market`
2. Agrega paquetes: `mobile_scanner`, `dio`, `flutter_secure_storage`, `provider`.
3. Implementa pantallas siguiendo los nombres y flujos del documento `road-map`.

---
Licencia MIT – 2025 
