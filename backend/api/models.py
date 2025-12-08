from django.conf import settings
from django.contrib.auth.models import AbstractUser
from django.db import models


class Usuario(AbstractUser):
    nombre = models.CharField(max_length=100)
    email = models.EmailField(unique=True)
    ubicacion = models.CharField(max_length=255, blank=True, null=True)

    USERNAME_FIELD = "email"
    REQUIRED_FIELDS = ["username"]

    def __str__(self):
        return self.email


class Producto(models.Model):
    nombre = models.CharField(max_length=255)
    codigo = models.CharField(max_length=128, unique=True)
    precio = models.DecimalField(max_digits=10, decimal_places=2)
    favorito = models.BooleanField(default=False)
    imagen_url = models.URLField(blank=True, null=True)
    ultima_actualizacion = models.DateTimeField(auto_now=True)

    def __str__(self):
        return f"{self.nombre} ({self.codigo})"


class NegocioPromocionado(models.Model):
    nombre = models.CharField(max_length=255)
    descuento = models.DecimalField(max_digits=5, decimal_places=2)
    presupuesto_base = models.DecimalField(max_digits=10, decimal_places=2, blank=True, null=True)
    direccion = models.CharField(max_length=255, blank=True, null=True)
    activo = models.BooleanField(default=True)

    def __str__(self):
        return self.nombre


class Lista(models.Model):
    nombre = models.CharField(max_length=255)
    limite_presupuesto = models.DecimalField(max_digits=10, decimal_places=2, blank=True, null=True)
    total = models.DecimalField(max_digits=10, decimal_places=2, default=0)
    fecha_creacion = models.DateTimeField(auto_now_add=True)
    usuario = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name="listas")
    es_promocion = models.BooleanField(default=False)
    negocio = models.ForeignKey(NegocioPromocionado, on_delete=models.SET_NULL, blank=True, null=True)

    def __str__(self):
        return f"{self.nombre} - {self.usuario.email}"


class DetalleLista(models.Model):
    lista = models.ForeignKey(Lista, on_delete=models.CASCADE, related_name="detalles")
    producto = models.ForeignKey(Producto, on_delete=models.CASCADE)
    cantidad = models.PositiveIntegerField()
    precio_unitario = models.DecimalField(max_digits=10, decimal_places=2)

    @property
    def subtotal(self):
        return self.cantidad * self.precio_unitario

    def __str__(self):
        return f"{self.producto.nombre} x{self.cantidad}"


class ListaPromocionada(models.Model):
    lista = models.ForeignKey(Lista, on_delete=models.CASCADE)
    negocio = models.ForeignKey(NegocioPromocionado, on_delete=models.CASCADE)

    class Meta:
        unique_together = ("lista", "negocio")
