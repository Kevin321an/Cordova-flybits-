package io.cordova.hellocordova

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.flybits.commons.library.api.idps.AnonymousIDP
import com.flybits.commons.library.api.results.callbacks.BasicResultCallback
import com.flybits.commons.library.exceptions.FlybitsException
import com.flybits.commons.library.logging.VerbosityLevel
import com.flybits.concierge.*
import com.flybits.concierge.enums.ShowMode
import org.apache.cordova.CordovaActivity

class MainActivity2 : CordovaActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = this.findViewById<Button>(R.id.login)
        val logOut = this.findViewById<Button>(R.id.logout)
        val flybitsLogin = this.findViewById<Button>(R.id.twoStepLogin)


        val concierge = FlybitsConcierge.with(this)
        concierge.setLoggingVerbosity(VerbosityLevel.ALL)
        if (!concierge.isInitialized) {
            val configuration = ConciergeConfiguration.Builder("C0C7D7D7-9716-4223-B4DB-1C9CC560E3C3")
                    .setTermsAndServicesRequired("https://flybits.com/legal/terms-of-use")
                    .setPrivacyPolicyUrl("https://flybits.com/legal/privacy-policy")
                    .setTimeToUploadContext(2).build()
            concierge.initialize(configuration)
        }

        login.setOnClickListener(View.OnClickListener {
            if (concierge.isAuthenticated) {
                showToast("You're already authenticated! Logout first.")
            } else {
                showToast("Authenticating...")
                try {
                    concierge.authenticate(AnonymousIDP())
                } catch (e: Exception) {
                    showToast("Error, something went wrong")
                }
            }
        })

        logOut.setOnClickListener{
            showToast("Logging out...")
            concierge.logOut(object: BasicResultCallback {
                override fun onException(exception: FlybitsException) {
                    showToast("Log out attempt error = ${exception.message}")
                }
                override fun onSuccess() {
                    showToast("Logged out successfully!")
                }
            })
        }

        val idp = AnonymousIDP() // or FlybitsIDP()

        var optin = object: OptIn2PhaseCallback {
            override fun onOptIn2PhaseCallback(
                    optInStatus: Boolean,
                    conciergeConnectCallback: ConciergeConnectCallBack
            ) {
                if(optInStatus) {
                }
                conciergeConnectCallback.connect(idp, null) // with valid idp
            }
        }

        flybitsLogin.setOnClickListener {
            val displayConfiguration = DisplayConfiguration(
                    ConciergeFragment.MenuType.MENU_TYPE_APP_BAR,
                    ShowMode.NEW_ACTIVITY,
                    true,
                    "This is Customizable Title",
                    "This is Customizable Message",true//or false
            )

            //For normal opt in
            //concierge.show(displayConfiguration)

            // For 2 phase Opt In
            concierge.conciergeFragment(
                    "cust1234",
                    displayConfiguration,
                    optin
                    ,null)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }


}