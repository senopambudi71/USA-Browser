package ons.dsi.usabrowser

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
import org.json.JSONObject
import java.util.*


class SplashActivity : AppCompatActivity() {

    private var adRespons = true
    private var runable: Runnable? = null
    var handler = Handler()
    private val TAG: String = SplashActivity::class.java.getSimpleName()
    private var interstitialAd: InterstitialAd? = null
//    google adsense
//    private var mInterstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // NOTE always use test ads during development and testing
//        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

        //for customizing splash on StartApp
//        StartAppAd.showSplash(this, savedInstanceState, SplashConfig()
//                .setTheme(SplashConfig.Theme.USER_DEFINED)
//                .setCustomScreen(R.layout.activity_splash)
//        )

        setContentView(R.layout.activity_splash)
        AudienceNetworkAds.initialize(this)
//        MobileAds.initialize(this)

//        var adRequest = AdRequest.Builder().build()

//        RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("9A06C1F3F383553862F17FD820AAE154"))

//        val testDeviceIds = Arrays.asList("9A06C1F3F383553862F17FD820AAE154")
//        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(configuration)

//        com.google.android.gms.ads.interstitial.InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                Log.d(TAG, adError?.message)
//                mInterstitialAd = null
//            }
//
//            override fun onAdLoaded(interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd) {
//                Log.d(TAG, "Ad was loaded.")
//                mInterstitialAd = interstitialAd
//            }
//        })

//        cekUpdateApp()
        showSplash()
    }

    private fun cekUpdateApp() {
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://raw.githubusercontent.com/triutami11/triutami11.github.io/main/xturbopro.json"

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val lastopen = prefs.getInt("lastopen", 0)
        val editor = prefs.edit()
        editor.putInt("lastopen", Date().day)
        editor.apply()

        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> {response ->
                val resp= response.toString()
                val jsonObject= JSONObject(resp)
                val version = jsonObject.getInt("version_code")
                val interstitialIdObject = jsonObject.getString("id_interstitial")
                val bannerIdObject = jsonObject.getString("id_banner")

                Log.i("id_ads", "id interstitial : $interstitialIdObject id banner : $bannerIdObject")

                val manager = this.packageManager
                val info = manager.getPackageInfo(this.packageName, PackageManager.GET_ACTIVITIES)
                val infovCode= info.versionCode

                if(infovCode < version){
                    Log.i("Respons", "New version is available on Google play store. Please Updated soon !!")
                    if (lastopen == Date().day) {
                        runable = Runnable {
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Information Update")
                            builder.setMessage("New version App is available on Google play store. Please Update Now !")

                            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse("https://play.google.com/store/apps/details?id=ons.dsi.xturbopro")
                                startActivity(intent)
                            }

                            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                                Log.i("Respons", "cancel update and load ads from server")
                                showSplash()
                            }

                            builder.show()
                        }
                        startDelay(1000)

                    } else {
                        facebookAdsLoadServer(interstitialIdObject)
                    }

                }else{
                    Log.i("Respons", "nothing updated in play store and load ads from server")
                    if (lastopen == Date().day) {
                        showSplash()
                    }else{
                        facebookAdsLoadServer(interstitialIdObject)
                    }

                }
            },
            Response.ErrorListener {
                Log.e("ResponsError", "No Data and load ads from local")
                if (lastopen == Date().day) {
                    showSplash()
                }else{
                    facebookAdsLoadServer("")
                }
            })
        queue.add(stringReq)
    }

    private fun showSplash(){
        runable = Runnable {
                val i = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(i)
        }
        startDelay(1000)
    }

    private fun showStartApp() {
        runable = Runnable {
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            StartAppAd.showAd(this)
        }
        startDelay(4000)
    }

    private fun facebookAdsLoadServer(idInterstitial: String) {
        Log.i("Ads", idInterstitial)
    //facebookAds
        interstitialAd = InterstitialAd(this, idInterstitial)

//  //      AdSettings.addTestDevice("1cca8ddf-a54b-4f4f-aa8b-d1a556c8a014")
//        interstitialAd!!.setAdListener(object : InterstitialAdListener {
//            override fun onInterstitialDisplayed(ad: Ad?) {}
//            override fun onInterstitialDismissed(ad: Ad?) {
////                finish()
//                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//            }
//            override fun onError(ad: Ad?, adError: AdError?) {
//                // Interstitial ad is loaded and ready to be displayed
////                Log.e(TAG, "Interstitial ad is error to display")
////                // Show the ad
////                if(!adRespons)
////                    return
////
////                handler.removeCallbacks(runable)
////                handler.post(runable)
//                Log.i("Ads", "onAdError ${adError?.errorMessage}")
//            }
//            override fun onAdLoaded(ad: Ad?) {
//                // Interstitial ad is loaded and ready to be displayed
////                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
////                // Show the ad
//                if(!adRespons)
//                    return
//
//                handler.removeCallbacks(runable)
//                handler.post(runable)
////                interstitialAd!!.show()
//                Log.i("Ads", "onAdsLoad")
//            }
//
//            override fun onAdClicked(ad: Ad?) {
//                // Ad clicked callback
//                Log.d(TAG, "Interstitial ad clicked!")
//            }
//
//            override fun onLoggingImpression(ad: Ad?) {
//                // Ad impression logged callback
//                Log.d(TAG, "Interstitial ad impression logged!")
//            }
//        })
        if(!adRespons)
            return

        handler.removeCallbacks(runable!!)
        handler.post(runable!!)
//                interstitialAd!!.show()
        Log.i("Ads", "onAdsLoad")
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

        startDelay(2000)

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
