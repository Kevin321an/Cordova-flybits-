/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package io.cordova.hellocordova;

import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.flybits.commons.library.api.idps.AnonymousIDP;
import com.flybits.commons.library.logging.VerbosityLevel;
import com.flybits.concierge.ConciergeConfiguration;
import com.flybits.concierge.FlybitsConcierge;

import org.apache.cordova.*;

public class MainActivity extends CordovaActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);

        Toast.makeText(this,"this is flybits",Toast.LENGTH_LONG).show();

        FlybitsConcierge flybitsConcierge = FlybitsConcierge.with(this);
        flybitsConcierge.setLoggingVerbosity(VerbosityLevel.ALL);

        if (!flybitsConcierge.isInitialized()){
            ConciergeConfiguration configuration = new ConciergeConfiguration.Builder("your-flybits-project-id")
                    .setTermsAndServicesRequired("https://flybits.com/legal/terms-of-use")
                    .setPrivacyPolicyUrl("https://flybits.com/legal/privacy-policy")
                    .setTimeToUploadContext(2).build();

            flybitsConcierge.initialize(configuration);
        }

        try {
            flybitsConcierge.authenticate(new AnonymousIDP());

        } catch (Exception e) {
            e.getStackTrace();
        }



    }
}
