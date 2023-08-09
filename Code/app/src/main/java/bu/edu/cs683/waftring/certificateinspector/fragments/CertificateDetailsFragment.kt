package bu.edu.cs683.waftring.certificateinspector.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bu.edu.cs683.waftring.certificateinspector.MDebug
import bu.edu.cs683.waftring.certificateinspector.R
import bu.edu.cs683.waftring.certificateinspector.viewmodel.CurCertificateViewModel

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
        val certState = view.findViewById<TextView>(R.id.tvState)
        val viewModel = ViewModelProvider(requireActivity()).get(CurCertificateViewModel::class.java)
        viewModel.curCertificate.observe(viewLifecycleOwner, Observer {
            certTitle.text = it?.id?.toString()
            certState.text = R.string.cert_state_valid.toString()
        })

        MDebug.exit()
    }
}
