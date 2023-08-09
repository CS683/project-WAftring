package bu.edu.cs683.waftring.certificateinspector.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bu.edu.cs683.waftring.certificateinspector.CertificateInspectorApplication
import bu.edu.cs683.waftring.certificateinspector.datalayer.CertificateDetail

class CurCertificateStoreViewModel(application : Application) : AndroidViewModel(application) {
    private val TAG : String = "CCSVM"
    val certificateRepository = (application as CertificateInspectorApplication).certificateStoreRepository
    private var _currentStore : String = ""
    val currentStore : String get() = _currentStore
    private var _certificateList : MutableLiveData<List<CertificateDetail>> = getAllCertificates()
    val certificateList : LiveData<List<CertificateDetail>> get() = _certificateList

    fun setStoreName(storeName : String) {
        Log.i(TAG, "Store Name: " + currentStore + " New Store Name: " + storeName)
        Log.i(TAG,"Store Count: " + certificateList.value?.size)
        _currentStore = storeName
        _certificateList.value = getAllCertificates().value
        Log.i(TAG, "New Store Count: " + certificateList.value?.size)
    }
    private fun getAllCertificates(): MutableLiveData<List<CertificateDetail>> {
        return certificateRepository.getAllCertificates(currentStore)
    }
}
