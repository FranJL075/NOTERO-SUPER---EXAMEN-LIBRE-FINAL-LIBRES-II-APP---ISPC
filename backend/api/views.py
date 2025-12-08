from rest_framework import viewsets, status
from rest_framework.decorators import action
from rest_framework.response import Response
import re  # Sanitización de entradas
from django.contrib.auth import get_user_model
from rest_framework.generics import CreateAPIView

from .models import Producto, Lista, DetalleLista, NegocioPromocionado
from .serializers import (
    UsuarioRegisterSerializer,
    ProductoSerializer,
    ListaSerializer,
    NegocioPromocionadoSerializer,
)
from .permissions import IsOwner

Usuario = get_user_model()


class UsuarioRegisterView(CreateAPIView):
    serializer_class = UsuarioRegisterSerializer
    permission_classes = []


class ProductoViewSet(viewsets.ModelViewSet):
    queryset = Producto.objects.all()
    serializer_class = ProductoSerializer


class ListaViewSet(viewsets.ModelViewSet):
    serializer_class = ListaSerializer
    permission_classes = [IsOwner]

    def get_queryset(self):
        return Lista.objects.filter(usuario=self.request.user)

    def perform_create(self, serializer):
        serializer.save(usuario=self.request.user)

    @action(detail=True, methods=["post"])
    def add_items_bulk(self, request, pk=None):
        lista = self.get_object()
        raw_codigos = request.data.get("codigos", [])
        # Sanitizamos cada código permitiendo solo alfanuméricos 3-128 caracteres
        pattern = re.compile(r"^[A-Za-z0-9]{3,128}$")
        codigos = [c.strip() for c in raw_codigos if pattern.fullmatch(str(c).strip())]
        added = []
        invalid = list(set(raw_codigos) - set(codigos))
        for codigo in codigos:
            try:
                producto = Producto.objects.get(codigo=codigo)
                DetalleLista.objects.create(
                    lista=lista,
                    producto=producto,
                    cantidad=1,
                    precio_unitario=producto.precio,
                )
                added.append(codigo)
            except Producto.DoesNotExist:
                pass
        return Response({"added": added, "invalid": invalid})

    @action(detail=True, methods=["post"])
    def check_price_changes(self, request, pk=None):
        lista = self.get_object()
        cambios = []
        for detalle in lista.detalles.all():
            if detalle.precio_unitario != detalle.producto.precio:
                cambios.append(
                    {
                        "producto": detalle.producto.codigo,
                        "antes": str(detalle.precio_unitario),
                        "ahora": str(detalle.producto.precio),
                    }
                )
        return Response({"cambios": cambios})

    @action(detail=True, methods=["post"])
    def apply_promo(self, request, pk=None):
        lista = self.get_object()
        if not lista.negocio or not lista.negocio.activo:
            return Response({"detail": "La lista no tiene un negocio asociado o está inactivo."}, status=status.HTTP_400_BAD_REQUEST)
        descuento = lista.negocio.descuento
        total = 0
        for detalle in lista.detalles.all():
            detalle.precio_unitario = detalle.precio_unitario * (1 - descuento / 100)
            detalle.save()
            total += detalle.subtotal
        lista.total = total
        lista.save()
        return Response({"total_descuento": total, "descuento": str(descuento)})


class NegocioPromocionadoViewSet(viewsets.ModelViewSet):
    queryset = NegocioPromocionado.objects.all()
    serializer_class = NegocioPromocionadoSerializer
