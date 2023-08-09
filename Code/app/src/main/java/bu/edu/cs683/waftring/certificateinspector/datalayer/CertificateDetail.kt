package bu.edu.cs683.waftring.certificateinspector.datalayer

import java.math.BigInteger
import java.security.cert.Certificate
import java.security.cert.X509Certificate

data class CertificateDetail(var id : Int,
                             var serialNumber : String,
                             var cert : X509Certificate?) {
}