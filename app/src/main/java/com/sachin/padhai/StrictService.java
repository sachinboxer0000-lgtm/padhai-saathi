package com.sachin.padhai;
import android.app.*;import android.app.usage.*;import android.content.*;import android.os.*;
public class StrictService extends Service{
volatile boolean on;volatile long until;Thread t;
public IBinder onBind(Intent i){return null;}
public int onStartCommand(Intent i,int f,int id){
if(Build.VERSION.SDK_INT>=26)((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(new NotificationChannel("s","Study mode",NotificationManager.IMPORTANCE_LOW));
Notification.Builder nb=Build.VERSION.SDK_INT>=26?new Notification.Builder(this,"s"):new Notification.Builder(this);
startForeground(1,nb.setContentTitle("पढ़ाई का समय चल रहा है").setSmallIcon(android.R.drawable.ic_lock_idle_lock).build());
if(i!=null)until=i.getLongExtra("u",0);on=true;
if(t==null){t=new Thread(this::loop);t.start();}
return START_NOT_STICKY;}
boolean ok(String p){return p.equals(getPackageName())||p.contains("dialer")||p.contains("telecom")||p.contains("launcher")||p.equals("com.android.systemui")||p.equals("com.android.phone");}
void loop(){UsageStatsManager u=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);String last="";
while(on&&System.currentTimeMillis()<until){try{long n=System.currentTimeMillis();UsageEvents e=u.queryEvents(n-4000,n);UsageEvents.Event ev=new UsageEvents.Event();String p=null;
while(e.hasNextEvent()){e.getNextEvent(ev);if(ev.getEventType()==UsageEvents.Event.MOVE_TO_FOREGROUND)p=ev.getPackageName();}
if(p!=null)last=p;
if(!last.isEmpty()&&!ok(last)){startActivity(new Intent(this,BlockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));last=getPackageName();}
Thread.sleep(800);}catch(Exception x){}}
stopSelf();}
public void onDestroy(){on=false;super.onDestroy();}}
