package ke.co.banit.e_distributor_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ke.co.banit.e_distributor_android.R
import ke.co.banit.e_distributor_android.databinding.ActivityGetStartedBinding
import ke.co.banit.e_distributor_android.util.loadActivity

class GetStartedActivity : AppCompatActivity() {
    private lateinit var binding:ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        binding.btnSignIn.setOnClickListener {
            loadActivity(SignInActivity::class.java)
        }
        binding.txtSignUp.setOnClickListener {
            loadActivity(SignUpActivity::class.java)
        }
    }
}