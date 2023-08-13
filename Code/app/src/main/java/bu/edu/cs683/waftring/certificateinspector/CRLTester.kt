package bu.edu.cs683.waftring.certificateinspector

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException

class CRLTester {
    val TAG = "CRLT"
    val DEFAULT_PORT = 80
    fun test(crl: String) : String {
        MDebug.enter()
        Log.i(TAG, "CRL Test for " + crl)
        // Expected format http://domain.name/crl.pem
        var hostname = crl.replace("http://", "").split("/")[0].trim()
        var result = "Successfully accessed " + crl
        var port = DEFAULT_PORT
        if(hostname.contains(":")) {
            val split = hostname.split(":")
            hostname = split[0]
            port = split[1].toInt()
        }
        try {
            Log.i(TAG, "Open socket started")
            val socket = Socket(hostname, port)
            socket.close()
            Log.i(TAG, "Socket closed")
        }
        // DNS Resolution failed
        catch (e : UnknownHostException) {
            result = "DNS Resolution for " + hostname + " failed"
        }
        // TCP connectivity failed
        catch(e : IOException) {
            Log.i(TAG, "Socket connection timed out")
            result = "TCP connection to " + hostname + ":" + port + " failed"
        }
        MDebug.exit()
        return result
    }
}