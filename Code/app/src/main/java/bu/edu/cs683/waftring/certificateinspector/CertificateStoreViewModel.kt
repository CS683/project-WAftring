package bu.edu.cs683.waftring.certificateinspector

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import java.security.cert.Certificate

class CertificateStoreViewModel : ViewModel, Parcelable {
    private val CERTIFICATE_STORE_NAMES = listOf("AndroidCAStore", "AndroidKeyStore", "BKS", "BouncyCastle", "PKCS12")
    private var certificateStores : MutableList<CertificateStore> = arrayListOf()

    constructor(parcel: Parcel) : this() {

    }

    constructor() {
        MDebug.enter()
        for(storeName in CERTIFICATE_STORE_NAMES) {
            certificateStores.add(CertificateStore(storeName))
        }
        MDebug.exit()
    }
    public fun getCertStoreNames() : List<String> {
        return certificateStores.map { it.name }
    }

    public fun getCertsInStore(storeName : String) : List<MCertificate> {
        return certificateStores.filter{ certificateStore -> certificateStore.name == storeName }[0].certificates
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CertificateStoreViewModel> {
        override fun createFromParcel(parcel: Parcel): CertificateStoreViewModel {
            return CertificateStoreViewModel(parcel)
        }

        override fun newArray(size: Int): Array<CertificateStoreViewModel?> {
            return arrayOfNulls(size)
        }
    }

}
