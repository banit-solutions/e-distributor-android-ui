package ke.co.banit.e_distributor_android.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ke.co.banit.e_distributor_android.data.PrefManager
import ke.co.banit.e_distributor_android.databinding.ActivitySplashBinding
import ke.co.banit.e_distributor_android.util.loadActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val prefManager = PrefManager()

            if (prefManager.isFirstTimeLaunch()){
                loadActivity(OnBoardActivity::class.java)
            }else{
                loadActivity(GetStartedActivity::class.java)
            }

            finish()
        }, 3000)
    }
}