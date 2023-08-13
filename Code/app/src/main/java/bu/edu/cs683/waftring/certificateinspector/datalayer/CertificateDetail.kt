package bu.edu.cs683.waftring.certificateinspector.datalayer

import android.util.Log
import bu.edu.cs683.waftring.certificateinspector.MDebug
import java.math.BigInteger
import java.security.cert.CRL
import java.security.cert.CertSelector
import java.security.cert.Certificate
import java.security.cert.X509CertSelector
import java.security.cert.X509Certificate
import java.security.cert.X509Extension
import org.bouncycastle.asn1.ASN1InputStream
import org.bouncycastle.asn1.ASN1OctetString
import org.bouncycastle.asn1.ASN1Primitive
import org.bouncycastle.asn1.x509.CRLDistPoint

data class CertificateDetail(var id : Int,
                             var serialNumber : String,
                             var cert : X509Certificate?) {
    val TAG = "CD"
    var CRL_EXTENSION = "2.5.29.31"
    val DIGITAL_SIGNATURE = 0;
    val NON_REPUDIATION = 1;
    val KEY_ENCIPHERMENT = 2;
    val DATA_ENCIPHERMENT = 3;
    val KEY_AGREEMENT = 4;
    val KEY_CERT_SIGN = 5;
    val CRL_SIGN = 6;
    val ENCIPHER_ONLY = 7;
    val DECIPHER_ONLY = 8;

    fun getPrincipalName() : String {
        var principal = cert?.subjectX500Principal
        if (principal != null) {
            return principal.getName()
        }
        return getFormattedSerialNumber()
    }

    fun getFormattedSerialNumber() : String {
        var i = 0
        var formattedSerialNumber : String = ""
        for(c in serialNumber) {
            if (i % 2 == 0 && i != 0) {
                formattedSerialNumber += ":"
            }
            formattedSerialNumber += c;
            i++
        }
        return formattedSerialNumber
    }
    fun getDetails(): String {
        MDebug.enter()
        if(cert == null)
            Log.i(TAG, "Certificate == NULL")
        else
            Log.i(TAG, "Certificate != NULL")
        MDebug.exit()
        if(cert != null)
            return cert.toString()
        else
            return ""
    }

    fun getCRLs(): List<String> {
        var crls : MutableList<String> = arrayListOf()
        var certExtension =  cert?.getExtensionValue(CRL_EXTENSION)
        if(null != certExtension) {
            val extensionValue = ASN1InputStream(ASN1OctetString.getInstance(certExtension).octets).readObject() as ASN1Primitive
            var crlDistPoints = CRLDistPoint.getInstance(extensionValue)
            for(distpoint in crlDistPoints.distributionPoints) {
                // This is not good. But I'm out of time.
                crls.add(distpoint.distributionPoint.name.toString().split("6:")[1].removeSuffix("\n"))
            }
        }
        return crls
    }
}