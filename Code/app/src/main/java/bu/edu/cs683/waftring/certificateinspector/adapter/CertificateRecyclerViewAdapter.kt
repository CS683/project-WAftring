package bu.edu.cs683.waftring.certificateinspector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import bu.edu.cs683.waftring.certificateinspector.databinding.FragmentCertificateCardBinding
import bu.edu.cs683.waftring.certificateinspector.datalayer.CertificateDetail
import java.lang.RuntimeException

class CertificateRecyclerViewAdapter(private val onCertificateClick : (CertificateDetail) -> Unit)
    : RecyclerView.Adapter<CertificateRecyclerViewAdapter.ViewHolder>() {

    private val certificates = mutableListOf<CertificateDetail>()
    interface OnCertificateClickListener {
        fun onCertificateClick(certificateDetail: CertificateDetail)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
            return ViewHolder((FragmentCertificateCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
        }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val certificate = certificates[position]
        holder.idView.text = (certificate.id + 1).toString()
        holder.contentView.text = certificate.serialNumber
        holder.cardView.setOnClickListener{
            onCertificateClick(certificate)
        }
    }

    override fun getItemCount() : Int = certificates.size

    fun getCertificate(pos : Int) : CertificateDetail {
        if(certificates.size > 0)
            return certificates[pos]
        else
            return CertificateDetail(-1, "No certificates available", null)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cert = certificates[position]
        holder.idView.text = (cert.id + 1).toString()
        holder.contentView.text = "Lorem Ipsum"
        holder.cardView.setOnClickListener {
            onCertificateClick(cert)
        }
    }

    fun replaceItems(it: List<CertificateDetail>) {
        certificates.clear()
        certificates.addAll(it)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: FragmentCertificateCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.certIdView
        val contentView: TextView = binding.certNameView
        val cardView: CardView = binding.certCard

        override fun toString() : String {
            return super.toString() + " '" + contentView + "'"
        }
    }
}