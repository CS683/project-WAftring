package bu.edu.cs683.waftring.certificateinspector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MDebug.enter()
        setContentView(R.layout.activity_main)
        MDebug.exit()
    }
}