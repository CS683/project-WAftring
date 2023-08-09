package bu.edu.cs683.waftring.certificateinspector.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import bu.edu.cs683.waftring.certificateinspector.CertificateInspectorApplication

class CertificateStoreViewModel(application: Application) : AndroidViewModel(application) {
    val certificateStoreRepository = (application as CertificateInspectorApplication).certificateStoreRepository
    private val _storeList : List<String> = getAllStores()
    val stores : List<String> get() = _storeList

    private fun getAllStores(): List<String> {
        return certificateStoreRepository.stores
    }
}