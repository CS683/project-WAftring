package bu.edu.cs683.waftring.certificateinspector

import java.security.KeyStore
import java.security.cert.Certificate

class CertificateStore {
    private var keyStore : KeyStore
    var certificates : MutableList<MCertificate> = arrayListOf()
    var activeCertificate : Certificate? = null
    var name : String
    constructor(storeName : String) {
        var i = 0
        MDebug.enter()
        name = storeName
        keyStore = KeyStore.getInstance(storeName)
        keyStore.load(null)
        for(alias in keyStore.aliases()) {
            certificates.add(MCertificate(i++, keyStore.getCertificate(alias)))
        }
        MDebug.exit()
    }
}