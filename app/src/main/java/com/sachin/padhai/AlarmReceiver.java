package com.sachin.padhai;
import android.app.*;import android.content.*;import android.media.*;import android.os.*;
public class AlarmReceiver extends BroadcastReceiver{
public void onReceive(Context c,Intent i){
NotificationManager nm=(NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
if(Build.VERSION.SDK_INT>=26)nm.createNotificationChannel(new NotificationChannel("a","Alarm",NotificationManager.IMPORTANCE_HIGH));
Notification.Builder b=Build.VERSION.SDK_INT>=26?new Notification.Builder(c,"a"):new Notification.Builder(c);
nm.notify((int)System.currentTimeMillis(),b.setContentTitle("पढ़ाई साथी").setContentText(i.getStringExtra("m")).setSmallIcon(android.R.drawable.ic_lock_idle_alarm).setContentIntent(PendingIntent.getActivity(c,0,new Intent(c,MainActivity.class),PendingIntent.FLAG_IMMUTABLE)).build());
final PendingResult pr=goAsync();
try{final Ringtone r=RingtoneManager.getRingtone(c,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));r.play();
((Vibrator)c.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(800);
new Handler(Looper.getMainLooper()).postDelayed(()->{r.stop();pr.finish();},6000);}catch(Exception e){pr.finish();}}}
