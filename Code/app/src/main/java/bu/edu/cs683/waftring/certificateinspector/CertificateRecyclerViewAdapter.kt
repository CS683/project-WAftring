package bu.edu.cs683.waftring.certificateinspector

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import bu.edu.cs683.waftring.certificateinspector.databinding.FragmentCertificateCardBinding

class CertificateRecyclerViewAdapter(private val certificates : List<MCertificate>)
    : RecyclerView.Adapter<CertificateRecyclerViewAdapter.ViewHolder>() {

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
        holder.contentView.text = certificate.cert.type
        holder.cardView.setOnClickListener{
            val action = CertificateStoreViewDirections.
            actionCertificateStoreViewToCertDetailFragment(position)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount() : Int = certificates.size

    override fun onBindViewHolder(holder: CertificateRecyclerViewAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
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