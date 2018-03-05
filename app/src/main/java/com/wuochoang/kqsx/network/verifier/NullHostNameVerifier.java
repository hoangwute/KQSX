package com.wuochoang.kqsx.network.verifier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import timber.log.Timber;

public class NullHostNameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
           Timber.i("Approving certificate for " + hostname);
            return true;
        }

    }