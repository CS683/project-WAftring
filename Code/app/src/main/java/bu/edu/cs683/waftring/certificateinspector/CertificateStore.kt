package bu.edu.cs683.waftring.certificateinspector

import java.security.KeyStore
import java.security.cert.Certificate

class CertificateStore {
    private var keyStore : KeyStore
    var certificates : MutableList<Certificate> = arrayListOf()
    var name : String
    constructor(storeName : String) {
        MDebug.enter()
        name = storeName
        keyStore = KeyStore.getInstance(storeName)
        keyStore.load(null)
        for(alias in keyStore.aliases()) {
            certificates.add(keyStore.getCertificate(alias))
        }
        MDebug.exit()
    }
}