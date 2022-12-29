package ons.dsi.usabrowser

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd.load
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.startapp.sdk.adsbase.StartAppAd
import kotlinx.android.synthetic.main.activity_splash.*
import org.json.JSONObject
import java.util.*


class SplashActivity : AppCompatActivity() {

    private var adRespons = true
    private var runable: Runnable? = null
    var handler = Handler()
    private val TAG: String = SplashActivity::class.java.getSimpleName()
    private var interstitialAd: InterstitialAd? = null
    private lateinit var isdialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        AudienceNetworkAds.initialize(this)

        showSplash()
    }

    private fun showLoading() {
        val loading = LoadingDialog(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                loading.isDismiss()
            }

        },1000)

    }

    private fun showSplash(){
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val lastopen = prefs.getInt("lastopen", 0)
        val editor = prefs.edit()
        editor.putInt("lastopen", Date().day)
        editor.apply()

        Log.i("lastOpen : ", lastopen.toString())
        Log.i("Day : ", Date().day.toString())

        runable = Runnable {

            if (lastopen == Date().day){
                val loading = LoadingDialog(this)
                loading.startLoading()
                val handler = Handler()
                handler.postDelayed(object :Runnable{
                    override fun run() {
                        val i = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(i)
                    }

                },500)
            }else{
                facebookAdsShow()
            }

        }
        startDelay(1000)
    }


    private fun facebookAdsShow() {
        showLoading()
        //testing
//        AdSettings.addTestDevice("728f5511-4bc7-42c1-949d-716277710700");

        //Config
        interstitialAd = InterstitialAd(this, "842365426987834_843727863518257")
        val config = interstitialAd!!.buildLoadAdConfig().withAdListener(object : InterstitialAdListener{
            override fun onError(p0: Ad?, error: AdError?) {
                Log.e("Ads error :", error.toString())

                val i = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(i)
            }

            override fun onAdLoaded(ad: Ad?) {
                Log.i("Ads Load: ", ad.toString())
                runable = Runnable {
                    if (interstitialAd== null){
                        val i = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(i)
                    }else{
                        interstitialAd!!.show()
                    }
                }
                startDelay(1000)
            }

            override fun onAdClicked(ad: Ad?) {
                Log.i("Add clicked: ", ad.toString())
            }

            override fun onLoggingImpression(ad: Ad?) {
                Log.i("logging Impression : ", ad.toString())
            }

            override fun onInterstitialDisplayed(ad: Ad?) {
                Log.i("Inters Displayed : ", ad.toString())
            }

            override fun onInterstitialDismissed(ad: Ad?) {
                Log.i("Inters Dismissed : ", ad.toString())
                val i = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(i)
            }

        }).build()
        interstitialAd!!.loadAd(config)

    }


    fun startDelay(time: Long){
        handler.removeCallbacks(runable!!)
        handler.postDelayed(runable!!, time)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val i = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}
