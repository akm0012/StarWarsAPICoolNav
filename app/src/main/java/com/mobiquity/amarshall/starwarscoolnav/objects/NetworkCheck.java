package com.mobiquity.amarshall.starwarscoolnav.objects;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Andrew on 7/19/15.
 */
public class NetworkCheck {
    private static final int GENERIC = 0;
    private static final int WIFI = 1;
    private static final int MOBILE = 2;
    private static final int NONE = -1;

    public static int getStatus(Context _context) {
        return getStatus(_context, false);
    }

    public static int getStatus(Context _context, boolean _requestType) {

        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Check to make sure it has the network hardware
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();

            // no network check
            if (info != null) {
                // Generic connection check
                if (info.isConnected() && !_requestType) {
                    return GENERIC;
                }

                // Specific connection check
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    // Reliable high-speed wifi
                    return WIFI;
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // cell network internet connection
                    return MOBILE;
                }

                return GENERIC;
            }
        }

        return NONE; // Not connected
    }

}
