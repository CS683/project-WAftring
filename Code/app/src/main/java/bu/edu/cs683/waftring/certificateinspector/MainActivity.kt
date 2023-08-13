package bu.edu.cs683.waftring.certificateinspector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import bu.edu.cs683.waftring.certificateinspector.datalayer.CertificateDetail
import bu.edu.cs683.waftring.certificateinspector.datalayer.CertificateStore
import bu.edu.cs683.waftring.certificateinspector.fragments.CertificateStoreFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MDebug.enter()
        setContentView(R.layout.activity_main)
        CertificateStore().createDummyCert()
        MDebug.exit()
    }
}