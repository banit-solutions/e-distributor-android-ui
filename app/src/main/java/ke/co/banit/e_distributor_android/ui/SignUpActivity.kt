package ke.co.banit.e_distributor_android.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import ke.co.banit.e_distributor_android.databinding.ActivitySignUpBinding

@SuppressLint("SetTextI18n")
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var position = 0
    private var isEmailVerified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        onBackPressedDispatcher.addCallback(this) {
            onBackPressedCustomized()
        }

        binding.btnNext.setOnClickListener {
            moveAction()
        }
    }

    private fun moveAction() {
        when (position) {
            0 -> {
                if (isEmailVerified) {
                    //showing layout
                    binding.layoutAccountInfo.visibility = View.VISIBLE
                    binding.layoutEmail.visibility = View.GONE
                    //positioning
                    position = 1
                    binding.stepView.done(false)
                    binding.stepView.go(position, true)
                    //changing text button
                    binding.btnNext.text = "Send Email"
                } else {
                    sendVerificationCode()
                }
            }

            1 -> {
                //showing layout
                binding.layoutShopInfo.visibility = View.VISIBLE
                binding.layoutAccountInfo.visibility = View.GONE
                //positioning
                position = 2
                binding.stepView.done(false)
                binding.stepView.go(position, true)
                //changing text button
                binding.btnNext.text = "Continue"
            }

            else -> {
                createAccount()
            }
        }
    }

    private fun createAccount() {

    }

    private fun sendVerificationCode() {
        binding.btnNext.text = "Continue"
        isEmailVerified = true
    }

    private fun onBackPressedCustomized() {
        when (position) {
            0 -> {
                onBackPressedDispatcher.onBackPressed()
            }

            1 -> {
                //showing layout
                binding.layoutAccountInfo.visibility = View.GONE
                binding.layoutEmail.visibility = View.VISIBLE
                //positioning
                position = 0
                binding.stepView.done(false)
                binding.stepView.go(position, true)
                //changing text button
                binding.btnNext.text = "Send Email"
            }

            else -> {
                //showing layout
                binding.layoutShopInfo.visibility = View.GONE
                binding.layoutAccountInfo.visibility = View.VISIBLE
                position = 1
                binding.stepView.done(false)
                binding.stepView.go(position, true)
                //changing text button
                binding.btnNext.text = "Continue"
            }
        }
    }
}