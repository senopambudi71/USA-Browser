package ons.dsi.usabrowser

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.facebook.ads.*
import org.jsoup.Jsoup
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
        AudienceNetworkAds.initialize(this)


        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val lastopen = prefs.getInt("lastopen", 0)
        val editor = prefs.edit()
        editor.putInt("lastopen", Date().hours)
        editor.apply()

        if (lastopen == Date().hours){
            //GetLastVersion PlayStore
            Log.i("LastVersionPS : ", Jsoup.connect("https://play.google.com/store/apps/details?id=lite.open.browser5g")
                    .timeout(30000)
                    .get()
                    .select(("div.hAyfc:nth-child(4)>"+
                            "span:nth-child(2) > div:nth-child(1)"+
                            "> span:nth-child(1)"))
                    .first()
                    .ownText()
                    .toString())

            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            return
        } else{
            //GetLastVersion PlayStore
            Log.i("LastVersionPS : ", Jsoup.connect("https://play.google.com/store/apps/details?id=lite.open.browser5g")
                    .timeout(30000)
                    .get()
                    .select(("div.hAyfc:nth-child(4)>"+
                            "span:nth-child(2) > div:nth-child(1)"+
                            "> span:nth-child(1)"))
                    .first()
                    .ownText()
                    .toString())
            facebookAdsLoad()
        }



    }


    private fun facebookAdsLoad() {
    //facebookAds
        interstitialAd = InterstitialAd(this, getString(R.string.id_interstitial))
//        AdSettings.addTestDevice("0ac682ab-6be1-4f86-a2b3-43733630205f")
        interstitialAd!!.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad?) {}
            override fun onInterstitialDismissed(ad: Ad?) {
//                finish()
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
                Log.i("Ads", "onAdError ${adError?.errorMessage}")
            }
            override fun onAdLoaded(ad: Ad?) {
                // Interstitial ad is loaded and ready to be displayed
//                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
//                // Show the ad
                if(!adRespons)
                    return

                handler.removeCallbacks(runable)
                handler.post(runable)
//                interstitialAd!!.show()
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

        runable = Runnable {
            adRespons = false
            if (interstitialAd!!.isAdLoaded){
                Log.i("Ads", "Adds Show")
//                opened this comment if you want show this ads
                interstitialAd!!.show()
            }else{
                Log.d("Ads", "Ads not show")
                val i = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(i)
            }
        }

        startDelay(4000)

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
}
