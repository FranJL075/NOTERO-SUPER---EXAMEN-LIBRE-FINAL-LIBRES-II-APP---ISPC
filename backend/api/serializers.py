from rest_framework import serializers
from django.contrib.auth import get_user_model
from .models import Producto, Lista, DetalleLista, NegocioPromocionado

Usuario = get_user_model()


class UsuarioRegisterSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)

    class Meta:
        model = Usuario
        fields = ("id", "email", "password", "nombre", "ubicacion")

    def create(self, validated_data):
        password = validated_data.pop("password")
        user = Usuario(**validated_data)
        user.set_password(password)
        user.save()
        return user


class UsuarioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Usuario
        fields = ("id", "email", "nombre", "ubicacion")


class ProductoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Producto
        fields = "__all__"


class DetalleListaSerializer(serializers.ModelSerializer):
    producto = ProductoSerializer(read_only=True)
    producto_id = serializers.PrimaryKeyRelatedField(queryset=Producto.objects.all(), write_only=True, source="producto")

    class Meta:
        model = DetalleLista
        fields = ("id", "producto", "producto_id", "cantidad", "precio_unitario", "subtotal")
        read_only_fields = ("subtotal",)


class ListaSerializer(serializers.ModelSerializer):
    detalles = DetalleListaSerializer(many=True, read_only=True)

    class Meta:
        model = Lista
        fields = ("id", "nombre", "limite_presupuesto", "total", "fecha_creacion", "es_promocion", "negocio", "detalles")


class NegocioPromocionadoSerializer(serializers.ModelSerializer):
    class Meta:
        model = NegocioPromocionado
        fields = "__all__"
