package ie.tcd.scss.q_dj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ed on 29/01/16.
 */
public class PlaybackBootReceiver extends BroadcastReceiver {
    PlaybackAlarmReceiver playbackAlarmReceiver = new PlaybackAlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
            playbackAlarmReceiver.setAlarm(context);
    }
}
