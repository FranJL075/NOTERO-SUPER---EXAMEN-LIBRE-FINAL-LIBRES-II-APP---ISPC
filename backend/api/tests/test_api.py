import json
from django.urls import reverse
from rest_framework.test import APIClient
from rest_framework import status
import pytest
from api.models import Producto, NegocioPromocionado

pytestmark = pytest.mark.django_db


@pytest.fixture
def client():
    return APIClient()


@pytest.fixture
def user(client):
    payload = {
        "email": "test@example.com",
        "password": "strongpass123",
        "nombre": "Tester",
    }
    client.post(reverse("usuario_registro"), payload)
    return payload


@pytest.fixture
def auth_headers(client, user):
    res = client.post(reverse("token_obtain_pair"), {"email": user["email"], "password": user["password"]})
    assert res.status_code == status.HTTP_200_OK
    token = res.data["access"]
    return {"HTTP_AUTHORIZATION": f"Bearer {token}"}


def test_create_product(client, auth_headers):
    payload = {"nombre": "Arroz", "codigo": "123456", "precio": "150.50"}
    res = client.post("/productos/", payload, **auth_headers)
    assert res.status_code == status.HTTP_201_CREATED
    assert Producto.objects.filter(codigo="123456").exists()


def test_full_flow_list_and_promo(client, auth_headers):
    # crear producto
    prod = {"nombre": "Leche", "codigo": "789", "precio": "200"}
    client.post("/productos/", prod, **auth_headers)

    # crear lista
    lista_res = client.post("/listas/", {"nombre": "Mi compra"}, **auth_headers)
    assert lista_res.status_code == 201
    lista_id = lista_res.data["id"]

    # agregar item bulk
    add_res = client.post(f"/listas/{lista_id}/add_items_bulk/", {"codigos": ["789"]}, **auth_headers)
    assert add_res.status_code == 200
    assert add_res.data["added"] == ["789"]

    # crear negocio y aplicar promo
    neg = NegocioPromocionado.objects.create(nombre="Super", descuento=10)
    # asociar negocio al backend lista (patch)
    client.patch(f"/listas/{lista_id}/", {"negocio": neg.id}, format="json", **auth_headers)

    promo_res = client.post(f"/listas/{lista_id}/apply_promo/", **auth_headers)
    assert promo_res.status_code == 200
    assert promo_res.data["descuento"] == "10"
