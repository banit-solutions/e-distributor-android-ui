package ke.co.banit.e_distributor_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ke.co.banit.e_distributor_android.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        val handler = Handler(Looper.myLooper()!!)
    }
}