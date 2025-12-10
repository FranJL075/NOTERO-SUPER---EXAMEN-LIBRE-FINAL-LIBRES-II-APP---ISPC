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

class ListaFragment : Fragment() {
    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            // TODO: llamar API para agregar producto a la lista
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
