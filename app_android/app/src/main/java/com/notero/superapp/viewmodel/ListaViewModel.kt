package com.notero.superapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notero.superapp.data.ListRepository
import com.notero.superapp.model.DetalleLista
import com.notero.superapp.model.Lista
import kotlinx.coroutines.launch

class ListaViewModel : ViewModel() {

    private val _listaActual = MutableLiveData<Lista?>()
    val listaActual: LiveData<Lista?> get() = _listaActual

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun crearLista(nombre: String, limite: Float?) {
        viewModelScope.launch {
            try {
                val nueva = ListRepository.crearLista(nombre, limite)
                _listaActual.value = nueva
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun cargarLista(id: Int) {
        viewModelScope.launch {
            try {
                val dto = ListRepository.obtenerLista(id)
                val detalles = dto.detalles.map {
                    val prod = com.notero.superapp.model.Producto(
                        id = it.producto.id,
                        nombre = it.producto.name,
                        codigo = "",
                        precio = it.producto.price.toFloat(),
                    )
                    com.notero.superapp.model.DetalleLista(
                        id = it.id,
                        producto = prod,
                        cantidad = it.cantidad,
                        precioUnitario = it.precio_unitario.toFloat()
                    )
                }.toMutableList()
                val lista = com.notero.superapp.model.Lista(
                    id = dto.id,
                    nombre = dto.nombre,
                    limitePresupuesto = dto.limite_presupuesto?.toFloat() ?: 0f,
                    detalles = detalles
                )
                _listaActual.value = lista
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun agregarPorCodigo(codigo: String) {
        val lista = _listaActual.value ?: return
        viewModelScope.launch {
            try {
                val prod = ListRepository.fetchProductoPorCodigo(codigo)
                if (prod != null) {
                    val detalle = DetalleLista(producto = prod)
                    lista.agregarProducto(detalle)
                    _listaActual.value = lista.copy()
                } else {
                    _error.value = "Producto no encontrado"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun eliminar(detalle: DetalleLista) {
        val lista = _listaActual.value ?: return
        lista.eliminarProducto(detalle)
        _listaActual.value = lista.copy()
    }

    fun establecerLimite(nuevo: Float) {
        val lista = _listaActual.value ?: return
        lista.establecerLimite(nuevo)
        _listaActual.value = lista.copy()
    }

    fun aplicarPromo() {
        val lista = _listaActual.value ?: return
        viewModelScope.launch {
            try {
                ListRepository.applyPromo(lista.id)
                // En vez de esperar la respuesta exacta, recargaríamos desde API
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun guardarLista() {
        val lista = _listaActual.value ?: return
        viewModelScope.launch {
            try {
                ListRepository.guardarLista(lista)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun seleccionarNegocio(negocioId: Int) {
        val lista = _listaActual.value ?: return
        viewModelScope.launch {
            try {
                ListRepository.asignarNegocio(lista.id, negocioId)
                // opcionalmente refrescar lista
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
