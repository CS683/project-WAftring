package bu.edu.cs683.waftring.certificateinspector.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bu.edu.cs683.waftring.certificateinspector.CRLTester
import bu.edu.cs683.waftring.certificateinspector.MDebug
import bu.edu.cs683.waftring.certificateinspector.R
import bu.edu.cs683.waftring.certificateinspector.viewmodel.CurCertificateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [CertificateDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CertificateDetailsFragment : Fragment(R.layout.fragment_certificate_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MDebug.enter()
        super.onViewCreated(view, savedInstanceState)
        val certTitle = view.findViewById<TextView>(R.id.tvCertName)
        val certDetailsView = view.findViewById<ScrollView>(R.id.svCertDetails)
        val certCrls = view.findViewById<Spinner>(R.id.spCrl)
        val crlButton = view.findViewById<Button>(R.id.btTestCrl)
        val viewModel = ViewModelProvider(requireActivity()).get(CurCertificateViewModel::class.java)
        var cert = viewModel.curCertificate.value
        certTitle.text = cert?.getFormattedSerialNumber()

        var tv = TextView(requireContext())
        tv.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        var crlSpinnerAdapter = cert?.let { ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, it.getCRLs()) }
        certCrls.adapter = crlSpinnerAdapter
        certCrls.setSelection(0)

        crlButton.setOnClickListener {
            val selectedCRL = certCrls.selectedItem
            if(null != selectedCRL) {
                var bg = CoroutineScope(Dispatchers.IO).async {
                    CRLTester().test(selectedCRL.toString())
                }
                CoroutineScope(Dispatchers.Main).launch {
                    var result = bg.await()
                    Handler(Looper.getMainLooper()).post{
                        showCrlResults(result)
                    }
                }
            } else {
                showCrlResults("No CRL available")
            }
        }

        tv.text = cert?.getDetails()
        certDetailsView.removeAllViews()
        certDetailsView.addView(tv)
        MDebug.exit()
    }

    private fun showCrlResults(result: String) {
        Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
    }
}
