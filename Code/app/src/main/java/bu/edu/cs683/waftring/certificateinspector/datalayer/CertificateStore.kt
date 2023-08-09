package bu.edu.cs683.waftring.certificateinspector.datalayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bu.edu.cs683.waftring.certificateinspector.MDebug
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.X509Certificate

class CertificateStore {


    private val CERTIFICATE_STORE_NAMES = listOf("AndroidCAStore", "AndroidKeyStore", "BKS", "BouncyCastle", "PKCS12")
    val defaultStore : String get() = CERTIFICATE_STORE_NAMES[0]
    val stores : List<String>  get() = CERTIFICATE_STORE_NAMES
    private var i : Int = 0

    constructor() {
        MDebug.enter()
        MDebug.exit()
    }

    fun getAllCertificates(storeName: String): MutableLiveData<List<CertificateDetail>> {
        var certificates : MutableList<CertificateDetail> = arrayListOf()
        if(!CERTIFICATE_STORE_NAMES.contains(storeName))
            return MutableLiveData<List<CertificateDetail>>(certificates)
        var keyStore : KeyStore = KeyStore.getInstance(storeName)
        keyStore.load(null)
        for(alias in keyStore.aliases()) {
            var tmpCert : CertificateDetail? = buildCertificateDetails(keyStore.getCertificate(alias))
            if(null != tmpCert)
                certificates.add(tmpCert)
        }
        return MutableLiveData<List<CertificateDetail>>(certificates)
    }

    private fun buildCertificateDetails(certificate: Certificate): CertificateDetail? {
        if(certificate is X509Certificate) {
            var xcert : X509Certificate = certificate
            return CertificateDetail(i++, xcert.serialNumber.toString(16), xcert)
        }
        return null
    }


}