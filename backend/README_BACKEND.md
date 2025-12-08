# Backend (Django + DRF)

## Requisitos

- Python 3.10+
- pip

## Instalación

```bash
cd backend
python -m venv venv
source venv/bin/activate  # En Windows: venv\Scripts\activate
pip install -r requirements.txt
python manage.py migrate
python manage.py createsuperuser  # opcional
python manage.py runserver 0.0.0.0:8000
```

## Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /usuarios/registro/ | Registro de nuevos usuarios |
| POST | /token/ | Login (JWT obtain) |
| POST | /token/refresh/ | Refresh token |
| CRUD | /productos/ | Productos |
| CRUD | /listas/ | Listas del usuario |
| POST | /listas/{id}/add-items-bulk/ | Agregar productos por código |
| POST | /listas/{id}/check-price-changes/ | Ver cambios de precio |
| POST | /listas/{id}/apply-promo/ | Aplicar promoción |

## CORS

Está habilitado CORS sólo para `http://localhost:3000` y `http://10.0.2.2:3000`.

## Base de Datos

Se utiliza SQLite por defecto (`db.sqlite3`).
