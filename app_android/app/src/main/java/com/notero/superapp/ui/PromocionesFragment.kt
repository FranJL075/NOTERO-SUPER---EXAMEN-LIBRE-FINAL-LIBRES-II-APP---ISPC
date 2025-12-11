package com.notero.superapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.notero.superapp.databinding.FragmentPromocionesBinding
import com.notero.superapp.ui.adapter.NegocioAdapter
import com.notero.superapp.ui.viewmodel.PromosViewModel
import com.notero.superapp.network.ApiService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.notero.superapp.util.collectIn
import android.widget.Toast

class PromocionesFragment : Fragment() {
    private var _binding: FragmentPromocionesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PromosViewModel by viewModels()
    private lateinit var adapter: NegocioAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPromocionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NegocioAdapter { negocio ->
            // por ahora aplicamos promo a lista id 1
            lifecycleScope.launch {
                try {
                    ApiService.instance.applyPromo(1)
                    Toast.makeText(requireContext(), "Descuento aplicado", Toast.LENGTH_SHORT).show()
                } catch (_: Exception) {
                    Toast.makeText(requireContext(), "Error aplicando descuento", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.recyclerNegocios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerNegocios.adapter = adapter

        viewModel.negocios.collectIn(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
