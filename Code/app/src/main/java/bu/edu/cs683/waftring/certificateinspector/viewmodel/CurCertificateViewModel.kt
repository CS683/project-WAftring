package bu.edu.cs683.waftring.certificateinspector.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bu.edu.cs683.waftring.certificateinspector.CertificateInspectorApplication
import bu.edu.cs683.waftring.certificateinspector.datalayer.CertificateDetail

class CurCertificateViewModel(application: Application) : AndroidViewModel(application) {
    private val _curCertificateDetails : MutableLiveData<CertificateDetail> = MutableLiveData()
    val curCertificate : LiveData<CertificateDetail> get() = _curCertificateDetails

    fun initCurCertificate(cert : CertificateDetail) {
        if(_curCertificateDetails.value == null)
            _curCertificateDetails.value = cert
    }

    fun setCurCertificate(cert: CertificateDetail) {
        _curCertificateDetails.value = cert
    }

    fun isCurCertificate(cert: CertificateDetail) : Boolean {
        return _curCertificateDetails.value?.id == cert.id
    }

}