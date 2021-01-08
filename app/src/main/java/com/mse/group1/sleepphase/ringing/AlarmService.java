package com.mse.group1.sleepphase.ringing;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.mse.group1.sleepphase.R;

import static com.mse.group1.sleepphase.application.SleepphaseApp.CHANNEL_ID;
import static com.mse.group1.sleepphase.ringing.AlarmReceiver.*;

public class AlarmService extends Service {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.feeling_good);
        mediaPlayer.setLooping(true);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, RingingActivity.class);
        notificationIntent.putExtra(ALARM_ID, intent.getStringExtra(ALARM_ID));

        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String alarmTitle = intent.getStringExtra(TITLE) + " Alarm";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Alarm " + alarmTitle + " is ringing!" )
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentIntent(pendingIntent)
                .build();
        String alarmSong = intent.getStringExtra(SONG);
        //TODO CHANGE SONG BASED ON INTENT
//        int res = 0;
//        if (alarmSong.equals("Feeling Good")) {
//            res =  R.raw.feeling_good;
//        } else if (alarmSong.equals("Strings Galore")) {
//            res =  R.raw.strings_galore;
//        } else if (alarmSong.equals("Tropical Keys")) {
//            res =  R.raw.tropical_keys;
//        }
//        mediaPlayer = MediaPlayer.create(this, res);
//        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        long[] pattern = { 0, 100, 1000 };
        vibrator.vibrate(pattern, 0);

        startForeground(1, notification);

        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
