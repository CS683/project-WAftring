package bu.edu.cs683.waftring.certificateinspector.fragments

import android.content.Context
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
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bu.edu.cs683.waftring.certificateinspector.MDebug
import bu.edu.cs683.waftring.certificateinspector.R
import bu.edu.cs683.waftring.certificateinspector.adapter.CertificateRecyclerViewAdapter
import bu.edu.cs683.waftring.certificateinspector.databinding.FragmentCertificateStoreBinding
import bu.edu.cs683.waftring.certificateinspector.datalayer.CertificateDetail
import bu.edu.cs683.waftring.certificateinspector.viewmodel.CertificateStoreViewModel
import bu.edu.cs683.waftring.certificateinspector.viewmodel.CurCertificateStoreViewModel
import bu.edu.cs683.waftring.certificateinspector.viewmodel.CurCertificateViewModel
import java.lang.RuntimeException

class CertificateStoreFragment : Fragment() {
    private var _binding: FragmentCertificateStoreBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 1
    private val TAG = "CSV"
    private lateinit var spnStore : Spinner
    private var lastStore : String = "N/A"
    private lateinit var myAdapter : CertificateRecyclerViewAdapter
    private lateinit var viewModel: CurCertificateViewModel
    private lateinit var listViewModel : CurCertificateStoreViewModel
    private lateinit var spinnerViewModel : CertificateStoreViewModel
    private  lateinit var onCertificateClickListener: OnCertificateClickListener

    interface OnCertificateClickListener {
        fun onCertificateClick(certDetails : CertificateDetail)
    }


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
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MDebug.enter()
        super.onViewCreated(view, savedInstanceState)

        spinnerViewModel = ViewModelProvider(this).get(CertificateStoreViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(CurCertificateViewModel::class.java)
        listViewModel = ViewModelProvider(this).get(CurCertificateStoreViewModel::class.java)

        binding.rvCertList?.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            myAdapter = CertificateRecyclerViewAdapter {
                certificateDetail -> viewModel.setCurCertificate(certificateDetail)
                var navController = view.findNavController()
                navController.navigate(R.id.action_certificateStoreView_to_certDetailFragment)
            }

            this.adapter = myAdapter
            listViewModel.certificateList.observe(
                viewLifecycleOwner, Observer {
                    myAdapter.replaceItems(it)
                    viewModel.initCurCertificate(myAdapter.getCertificate(0))
                }
            )
            viewModel.curCertificate.observe(viewLifecycleOwner, Observer {
                myAdapter.notifyDataSetChanged()
            })
        }

        spnStore = view.findViewById<Spinner>(R.id.spnStore)

        var adapter = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, spinnerViewModel.stores)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnStore.adapter = adapter
        spnStore.setSelection(0)
        spnStore.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                MDebug.enter()
                Log.i(TAG, "index: " + p2 + " id: " + p3)
                listViewModel.setStoreName(spinnerViewModel.stores[p2])
                MDebug.exit()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        MDebug.exit()
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        @JvmStatic
        fun newInstance(columnCount : Int) =
            CertificateStoreFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}