package bu.edu.cs683.waftring.certificateinspector

import android.app.Application
import bu.edu.cs683.waftring.certificateinspector.datalayer.CertificateStore

class CertificateInspectorApplication : Application() {
    var certificateStoreRepository : CertificateStore = CertificateStore()
}