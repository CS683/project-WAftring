package bu.edu.cs683.waftring.certificateinspector

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView

class CertificateStoreView : Fragment(R.layout.fragment_certificate_store) {
    private val TAG = "CSV"
    private lateinit var spnStore : Spinner
    private lateinit var svCerts : ScrollView
    private lateinit var llCerts : LinearLayout
    private var lastStore : String = "N/A"
    companion object {
        fun newInstance() = CertificateStoreView()
    }

    private lateinit var viewModel: CertificateStoreViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MDebug.enter()
        viewModel = CertificateStoreViewModel()
        super.onViewCreated(view, savedInstanceState)

        svCerts = view.findViewById<ScrollView>(R.id.svCerts)
        llCerts = view.findViewById<LinearLayout>(R.id.llCerts)
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
                displayCertsInStore()
                MDebug.exit()
            }

            @Override
            override fun onNothingSelected(p0: AdapterView<*>?) {
                MDebug.enter()
                TODO("Not yet implemented")
                MDebug.exit()
            }
        }
        displayCertsInStore()
        MDebug.exit()
    }

    fun displayCertsInStore() {
        var storeName : String = spnStore.selectedItem.toString()
        if(storeName != lastStore) {
            llCerts.removeAllViews()
            for (certs in viewModel.getCertsInStore(storeName)) {
                var tv = TextView(this.requireContext())
                tv.setText(certs.type)
                llCerts.addView(tv)
            }
            lastStore = storeName
        }
    }

}