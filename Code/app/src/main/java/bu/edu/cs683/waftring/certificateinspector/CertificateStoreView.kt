package bu.edu.cs683.waftring.certificateinspector

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bu.edu.cs683.waftring.certificateinspector.databinding.FragmentCertificateStoreBinding

class CertificateStoreView : Fragment() {
    private var _binding: FragmentCertificateStoreBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 1
    private val TAG = "CSV"
    private lateinit var spnStore : Spinner
    private var lastStore : String = "N/A"

    private lateinit var viewModel: CertificateStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MDebug.enter()
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        MDebug.exit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MDebug.enter()
        _binding = FragmentCertificateStoreBinding.inflate(inflater, container, false)
        MDebug.exit()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MDebug.enter()
        viewModel = CertificateStoreViewModel()
        super.onViewCreated(view, savedInstanceState)
        binding.rvCertList?.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = CertificateRecyclerViewAdapter(viewModel.getCertsInStore(lastStore))
        }

        spnStore = view.findViewById<Spinner>(R.id.spnStore)

        var adapter = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, viewModel.getCertStoreNames())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnStore.adapter = adapter
        spnStore.setSelection(0)
        spnStore.onItemSelectedListener = object : OnItemSelectedListener {
            @Override
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                MDebug.enter()
                Log.i(TAG, "index: " + p2 + " id: " + p3)
                MDebug.exit()
            }

            @Override
            override fun onNothingSelected(p0: AdapterView<*>?) {
                MDebug.enter()
                TODO("Not yet implemented")
                MDebug.exit()
            }
        }
        MDebug.exit()
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        @JvmStatic
        fun newInstance(columnCount : Int) =
            CertificateStoreView().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}