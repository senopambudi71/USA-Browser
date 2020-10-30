package ons.dsi.usabrowser

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.ads.*
import java.util.*


class SplashActivity : AppCompatActivity() {

    private var adRespons = true
    private var runable: Runnable? = null
    var handler = Handler()
    private val TAG: String = SplashActivity::class.java.getSimpleName()
    private var interstitialAd: InterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val lastopen = prefs.getInt("lastopen", 0)
        val editor = prefs.edit()
        editor.putInt("lastopen", Date().hours)
        editor.apply()

//        if (lastopen == Date().hours){
////            Toast.makeText(this, "in same day", Toast.LENGTH_SHORT).show()
//            val i = Intent(this@SplashActivity, MainActivity::class.java)
//            startActivity(i)
//            return
//        }
        AudienceNetworkAds.initialize(this)
        facebookAdsLoad()

//        startDelay(4000)
    }


    private fun facebookAdsLoad() {
    //facebookAds
        interstitialAd = InterstitialAd(this, getString(R.string.id_interstitial))
//        AdSettings.addTestDevice("411297a5-4b09-431b-9099-e7fc6a6c88e0")
        interstitialAd!!.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad?) {}
            override fun onInterstitialDismissed(ad: Ad?) {
                finish()
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            override fun onError(ad: Ad?, adError: AdError?) {
                // Interstitial ad is loaded and ready to be displayed
//                Log.e(TAG, "Interstitial ad is error to display")
//                // Show the ad
//                if(!adRespons)
//                    return
//
//                handler.removeCallbacks(runable)
//                handler.post(runable)
                Log.i("Ads", "onAdError")
            }
            override fun onAdLoaded(ad: Ad?) {
                // Interstitial ad is loaded and ready to be displayed
//                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
//                // Show the ad
//                if(!adRespons)
//                    return
//
//                handler.removeCallbacks(runable)
//                handler.post(runable)
                Log.i("Ads", "onAdsLoad")
            }

            override fun onAdClicked(ad: Ad?) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad?) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!")
            }
        })
        interstitialAd!!.loadAd()

//        runable = Runnable {
//            adRespons = false
//            if (interstitialAd!!.isAdLoaded){
//                Log.i("Ads", "Adds Show")
//                interstitialAd!!.show()
//            }else{
//                Log.d("Ads", "Ads not show")
//                val i = Intent(this@SplashActivity, MainActivity::class.java)
//                startActivity(i)
//            }
//
//        }
        handler.postDelayed(Runnable {
            // Check if interstitialAd has been loaded successfully
            if (interstitialAd == null || !interstitialAd!!.isAdLoaded) {
                Toast.makeText(this, "ads Loads", Toast.LENGTH_SHORT).show()
                return@Runnable
            }
            // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
            if (interstitialAd!!.isAdInvalidated) {
                Toast.makeText(this, "ads Expired", Toast.LENGTH_SHORT).show()
                return@Runnable
            }
            // Show the ad
            interstitialAd!!.show()
        }, 4000.toLong())

    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (null != connectivity) {
            val info = connectivity.activeNetworkInfo
            if (null != info && info.isConnected) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }

    fun startDelay(time: Long){
        handler.removeCallbacks(runable)
        handler.postDelayed(runable, time)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
    override fun onDestroy() {
        if (interstitialAd != null) {
            interstitialAd!!.destroy()
        }
        super.onDestroy()
    }
}
