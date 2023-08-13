package bu.edu.cs683.waftring.certificateinspector.datalayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bu.edu.cs683.waftring.certificateinspector.MDebug
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.asn1.x509.CRLDistPoint
import org.bouncycastle.asn1.x509.DistributionPoint
import org.bouncycastle.asn1.x509.DistributionPointName
import org.bouncycastle.asn1.x509.Extension
import org.bouncycastle.asn1.x509.GeneralName
import org.bouncycastle.asn1.x509.KeyUsage
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Security
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.util.Calendar
import org.bouncycastle.cert.X509v3CertificateBuilder
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder

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

        fun createDummyCert() {
            Security.addProvider(org.bouncycastle.jce.provider.BouncyCastleProvider())
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC")
            keyPairGenerator.initialize(2048)
            val keyPair = keyPairGenerator.generateKeyPair()

            val dummycert = createSelfSignedCertificate(keyPair)

            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val entry = KeyStore.PrivateKeyEntry(keyPair.private, arrayOf(dummycert))
            keyStore.setEntry("WAFTRING_DUMMY", entry, null)
        }

        private fun createSelfSignedCertificate(keyPair: KeyPair): X509Certificate {
            val subject = X500Name("CN=WAFTRING_CERT")
            val issuer = subject
            val serialNumber : BigInteger = BigInteger.valueOf(System.currentTimeMillis())
            val notBefore = Calendar.getInstance()
            val notAfter = Calendar.getInstance()
            notAfter.add(Calendar.HOUR, 24)

            val crlUrl1 = GeneralName(GeneralName.uniformResourceIdentifier, "http://wanl.blue/crl.pem")
            val crlUrl2 = GeneralName(GeneralName.uniformResourceIdentifier, "http://bogus.wanl.blue/crl.pem")
            val crlUrl3 = GeneralName(GeneralName.uniformResourceIdentifier, "http://wanl.blue:9000/crl.pem")
            val dpn = DistributionPointName(DistributionPointName.FULL_NAME, crlUrl1)
            val dpn2 = DistributionPointName(DistributionPointName.FULL_NAME, crlUrl2)
            val dpn3 = DistributionPointName(DistributionPointName.FULL_NAME, crlUrl3)
            val dp = DistributionPoint(dpn, null, null)
            val dp2 = DistributionPoint(dpn2, null, null)
            val dp3 = DistributionPoint(dpn3, null, null)
            val cdp = CRLDistPoint(arrayOf(dp, dp2, dp3))

            var certBuilder = X509v3CertificateBuilder(issuer, serialNumber, notBefore.time, notAfter.time, subject, SubjectPublicKeyInfo.getInstance(keyPair.public.encoded))
            certBuilder.addExtension(Extension.cRLDistributionPoints, false, cdp);
            certBuilder.addExtension(Extension.keyUsage, true, KeyUsage(KeyUsage.keyCertSign or KeyUsage.digitalSignature or KeyUsage.keyEncipherment))
            certBuilder.addExtension(Extension.subjectAlternativeName, false, GeneralName(GeneralName.dNSName, "wanl.blue"))

            val signer = JcaContentSignerBuilder("SHA256withRSA").build(keyPair.private)
            val certHolder = certBuilder.build(signer)
            return JcaX509CertificateConverter().getCertificate(certHolder)
        }
}