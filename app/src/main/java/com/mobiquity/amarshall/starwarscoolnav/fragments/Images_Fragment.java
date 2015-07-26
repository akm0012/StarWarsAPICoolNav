package com.mobiquity.amarshall.starwarscoolnav.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobiquity.amarshall.starwarscoolnav.R;

/**
 * Created by Andrew on 7/26/15.
 */
public class Images_Fragment extends Fragment {

    public static final String TAG = "Images_Tag";
    private Activity mContainingActivity;

    // UI Views
    private WebView webView;

    // Factory
    public static Images_Fragment newInstance() {

        Images_Fragment frag = new Images_Fragment();

        return frag;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

//        if (_activity instanceof Name_List_Interface) {
        mContainingActivity = _activity;
//        } else {
//            throw new IllegalArgumentException("Must implement Name_List_Interface"); // This should never happen
//        }

    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {


        View view = _inflater.inflate(R.layout.images_fragment, _container, false);

        // Get handles to all out views
        webView = (WebView) view.findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());


        go_to_url("");

        return view;
    }

    public void go_to_url(String _url) {

        webView.loadUrl("https://www.google.com/search?tbm=isch&q=" + _url + " Star Wars");
    }



}