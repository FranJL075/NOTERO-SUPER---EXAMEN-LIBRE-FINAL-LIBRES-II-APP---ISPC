package com.notero.superapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.notero.superapp.databinding.FragmentListaBinding
import com.google.zxing.integration.android.IntentIntegrator
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.notero.superapp.ui.adapter.DetalleAdapter
import com.notero.superapp.ui.viewmodel.ListaViewModel
import androidx.lifecycle.lifecycleScope
import com.notero.superapp.util.collectIn

class ListaFragment : Fragment() {
    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListaViewModel by viewModels()
    private lateinit var adapter: DetalleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DetalleAdapter(mutableListOf())
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@ListaFragment.adapter
        }

        viewModel.lista.collectIn(viewLifecycleOwner) { lista ->
            lista?.let { adapter.update(it.detalles) }
        }

        // cargar lista id=1 temporal
        viewModel.load(1)

        binding.fabAdd.setOnClickListener {
            IntentIntegrator.forSupportFragment(this).apply {
                setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                setPrompt("Escanee un producto")
                setBeepEnabled(false)
            }.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            Toast.makeText(requireContext(), "Código: ${result.contents}", Toast.LENGTH_SHORT).show()
            viewModel.agregarCodigo(result.contents)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
