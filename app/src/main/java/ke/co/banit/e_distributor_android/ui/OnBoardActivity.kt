package ke.co.banit.e_distributor_android.ui

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager
import com.xcode.onboarding.MaterialOnBoarding
import com.xcode.onboarding.OnBoardingActivity
import com.xcode.onboarding.OnBoardingPage
import com.xcode.onboarding.OnFinishLastPage
import ke.co.banit.e_distributor_android.R
import ke.co.banit.e_distributor_android.core.adapters.OnBoardingAdapter
import ke.co.banit.e_distributor_android.data.PrefManager
import ke.co.banit.e_distributor_android.databinding.ActivityOnBoardBinding
import ke.co.banit.e_distributor_android.util.loadActivity

class OnBoardActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOnBoardBinding
    private lateinit var dots: Array<TextView?>
    private lateinit var layouts: IntArray
    private lateinit var mContext: Context

    //  viewpager change listener
    private var viewPagerPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                addBottomDots(position)

                // changing the next button text 'NEXT' / 'GOT IT'
                if (position == layouts.size - 1) {
                    // show button
                    binding.cardSignIn.visibility = View.VISIBLE
                } else {
                    // show button
                    binding.cardSignIn.visibility = View.GONE
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        }
    private var prefManager: PrefManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        prefManager = PrefManager()
        binding = ActivityOnBoardBinding.inflate(layoutInflater)

        setContentView(binding.root)

        layouts = intArrayOf(
            R.layout.welcome_slide1,
            R.layout.welcome_slide2,
            R.layout.welcome_slide3,
            R.layout.welcome_slide4
        )

        addBottomDots(0)

        val myViewPagerAdapter = OnBoardingAdapter(mContext, layouts)
        binding.viewPager.adapter = myViewPagerAdapter
        binding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
        binding.cardSignIn.setOnClickListener { launchHomeScreen(0) }
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        binding.layoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            binding.layoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun getItem(i: Int): Int {
        return binding.viewPager.currentItem + i
    }

    private fun launchHomeScreen(position: Int) {
        //checking if user is null
        prefManager!!.setFirstTimeLaunch()
        if (position == 0) { //open sign up
            loadActivity(GetStartedActivity::class.java)
            finish()
        }
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }
}