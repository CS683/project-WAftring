package bu.edu.cs683.waftring.certificateinspector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.edu.cs683.waftring.certificateinspector.datalayer.CertificateDetail
import bu.edu.cs683.waftring.certificateinspector.fragments.CertificateStoreFragment

class MainActivity : AppCompatActivity(), CertificateStoreFragment.OnCertificateClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MDebug.enter()
        setContentView(R.layout.activity_main)
        MDebug.exit()
    }

    override fun onCertificateClick(certDetails: CertificateDetail) {
        MDebug.enter()
        MDebug.exit()
    }
}