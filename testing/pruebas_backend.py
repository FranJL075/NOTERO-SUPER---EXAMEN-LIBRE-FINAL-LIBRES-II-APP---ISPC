"""Pytest suite para el backend de NOTERO SUPER.
Estas pruebas requieren pytest-django y la variable de entorno DJANGO_SETTINGS_MODULE
apuntando a 'backend.config.settings'. Se asume que migrations están aplicadas.
Correr con:
    pytest -q testing/pruebas_backend.py
"""

import json
import pytest
from decimal import Decimal
from django.urls import reverse
from rest_framework.test import APIClient
from api.models import Producto, NegocioPromocionado, Lista

@pytest.fixture
def api_client(db):
    """Cliente autenticado que devuelve token al iniciar sesión."""
    return APIClient()

@pytest.fixture
def user_credentials():
    return {"email": "test@example.com", "password": "Segura123", "username": "test", "nombre": "Tester"}

@pytest.fixture
def registered_user(db, django_user_model, user_credentials):
    return django_user_model.objects.create_user(**user_credentials)

@pytest.fixture
def auth_client(api_client, registered_user, user_credentials):
    # login to get JWT
    response = api_client.post("/token/", {"email": user_credentials["email"], "password": user_credentials["password"]})
    assert response.status_code == 200
    token = response.data["access"]
    api_client.credentials(HTTP_AUTHORIZATION=f"Bearer {token}")
    return api_client

#############################
# CASOS DE PRUEBA
#############################

def test_registro_usuario(api_client, user_credentials):
    """Se registra un usuario y se espera HTTP 201."""
    resp = api_client.post("/usuarios/registro/", user_credentials)
    assert resp.status_code == 201
    assert resp.data["email"] == user_credentials["email"]


def test_login_jwt(api_client, registered_user, user_credentials):
    """Se loguea y recibe token JWT válido."""
    resp = api_client.post("/token/", {"email": user_credentials["email"], "password": user_credentials["password"]})
    assert resp.status_code == 200
    assert "access" in resp.data
    assert "refresh" in resp.data


def test_crear_lista(auth_client):
    """Crea una lista y verifica campos básicos."""
    resp = auth_client.post("/listas/", {"nombre": "Super"})
    assert resp.status_code == 201
    assert resp.data["nombre"] == "Super"


def test_agregar_items_bulk(auth_client):
    """Crea productos, los agrega masivamente y verifica resultado."""
    # Creamos productos
    codigos = [f"PRD{i:03d}" for i in range(1, 4)]
    for c in codigos:
        Producto.objects.create(nombre=f"Prod {c}", codigo=c, precio=Decimal("10.00"))
    # Creamos lista
    lista_id = auth_client.post("/listas/", {"nombre": "Compra"}).data["id"]
    # Agregamos productos
    resp = auth_client.post(f"/listas/{lista_id}/add_items_bulk/", {"codigos": codigos})
    assert resp.status_code == 200
    assert set(resp.data["added"]) == set(codigos)
    assert resp.data["invalid"] == []


def test_detectar_codigos_inexistentes(auth_client):
    """Envía códigos no existentes y espera que aparezcan en 'invalid'."""
    lista_id = auth_client.post("/listas/", {"nombre": "Compra 2"}).data["id"]
    resp = auth_client.post(f"/listas/{lista_id}/add_items_bulk/", {"codigos": ["NOEXISTE"]})
    assert resp.status_code == 200
    assert resp.data["added"] == []
    assert "NOEXISTE" in resp.data["invalid"]


def test_aplicar_descuento(auth_client):
    """Aplica descuento del NegocioPromocionado."""
    producto = Producto.objects.create(nombre="Leche", codigo="LEC001", precio=Decimal("100.00"))
    negocio = NegocioPromocionado.objects.create(nombre="Super 10%", descuento=Decimal("10.00"))
    lista_id = auth_client.post("/listas/", {"nombre": "Promo", "negocio": negocio.id}).data["id"]
    # Agregar item
    auth_client.post(f"/listas/{lista_id}/add_items_bulk/", {"codigos": ["LEC001"]})
    resp = auth_client.post(f"/listas/{lista_id}/apply_promo/")
    assert resp.status_code == 200
    assert resp.data["descuento"] == "10.00"


def test_detectar_cambios_precio(auth_client):
    """Crea producto, cambia su precio y detecta cambio."""
    prod = Producto.objects.create(nombre="Pan", codigo="PAN001", precio=Decimal("50.00"))
    lista_id = auth_client.post("/listas/", {"nombre": "Panera"}).data["id"]
    auth_client.post(f"/listas/{lista_id}/add_items_bulk/", {"codigos": ["PAN001"]})
    # Cambio de precio
    prod.precio = Decimal("60.00")
    prod.save()
    resp = auth_client.post(f"/listas/{lista_id}/check_price_changes/")
    assert resp.status_code == 200
    cambios = resp.data["cambios"]
    assert any(c["producto"] == "PAN001" for c in cambios)
