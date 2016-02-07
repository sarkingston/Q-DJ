package ie.tcd.scss.q_dj;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ed on 29/01/16.
 */
public class PlaybackService extends IntentService {

    public PlaybackService(){super("PlaybackService");}

    @Override
    protected void onHandleIntent(Intent intent) {
        //launch querying method for queue
    }

    public static boolean isRunning(Class<?> serviceClass, Activity activity){
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
