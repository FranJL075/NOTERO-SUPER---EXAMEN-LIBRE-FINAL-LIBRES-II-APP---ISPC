from django.contrib import admin
from django.urls import path, include
from rest_framework import routers
from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView
from api.views import (
    UsuarioRegisterView,
    UsuarioMeView,
    ProductoViewSet,
    ListaViewSet,
    NegocioPromocionadoViewSet,
)

router = routers.DefaultRouter()
router.register(r"productos", ProductoViewSet)
router.register(r"listas", ListaViewSet, basename="listas")
router.register(r"negocios", NegocioPromocionadoViewSet)

urlpatterns = [
    path("admin/", admin.site.urls),
    path("usuarios/registro/", UsuarioRegisterView.as_view(), name="usuario_registro"),
    path("usuarios/yo/", UsuarioMeView.as_view(), name="usuario_me"),
    path("token/", TokenObtainPairView.as_view(), name="token_obtain_pair"),
    path("token/refresh/", TokenRefreshView.as_view(), name="token_refresh"),
    path("", include(router.urls)),
]
