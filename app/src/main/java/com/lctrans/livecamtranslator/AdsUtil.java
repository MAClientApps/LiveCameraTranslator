package com.lctrans.livecamtranslator;

import android.app.Activity;

import com.appodeal.ads.Appodeal;

public class AdsUtil {
    public static int ADS_COUNTER=0;
    public final static String ADS_KEY = "cae0397bc68391a1591c0d946ec803f3dc6da3dfcff0e230";
    public static void initialiseAds(Activity activity){
       if (AppConstants.SHOW_ADS){
           Appodeal.initialize(activity, ADS_KEY, Appodeal.BANNER | Appodeal.REWARDED_VIDEO);
       }
    }
    public static void showVideoAds(Activity activity, boolean iscountcheck) {
        try {
            if(AppConstants.SHOW_ADS) {
                if (iscountcheck){
                    ADS_COUNTER++;
                    if (ADS_COUNTER == 4) {
                        if (Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)) {
                            Appodeal.show(activity, Appodeal.REWARDED_VIDEO);
                        }
                        ADS_COUNTER = 0;
                    }
                }else{
                        Appodeal.show(activity, Appodeal.REWARDED_VIDEO);
                }
            }
        }
        catch (Exception e){}
    }
    public static void showBannerAds(Activity activity,int bannerView)
    {
        if(AppConstants.SHOW_ADS) {
            Appodeal.setBannerViewId(bannerView);
            Appodeal.show(activity, Appodeal.BANNER_VIEW);
        }
    }

}
