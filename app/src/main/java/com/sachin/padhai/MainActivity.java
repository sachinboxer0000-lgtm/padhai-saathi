package com.sachin.padhai;
import android.app.*;import android.content.*;import android.net.Uri;import android.os.*;import android.provider.Settings;import android.webkit.*;
public class MainActivity extends Activity{
WebView w;ValueCallback<Uri[]> cb;
protected void onCreate(Bundle b){super.onCreate(b);
getWindow().setStatusBarColor(0xFF0F1325);getWindow().setNavigationBarColor(0xFF192039);
if(Build.VERSION.SDK_INT>=33)requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"},1);
w=new WebView(this);setContentView(w);WebSettings s=w.getSettings();
s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setAllowFileAccess(true);s.setAllowUniversalAccessFromFileURLs(true);
w.setWebViewClient(new WebViewClient());
w.setWebChromeClient(new WebChromeClient(){public boolean onShowFileChooser(WebView v,ValueCallback<Uri[]> c,FileChooserParams p){cb=c;try{startActivityForResult(p.createIntent(),1);}catch(Exception e){cb=null;return false;}return true;}});
w.addJavascriptInterface(new Br(),"Android");
w.loadUrl("file:///android_asset/www/index.html");}
protected void onActivityResult(int r,int c,Intent d){if(cb!=null){cb.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(c,d));cb=null;}}
public void onBackPressed(){moveTaskToBack(true);}
class Br{
AlarmManager am(){return (AlarmManager)getSystemService(ALARM_SERVICE);}
@JavascriptInterface public void alarm(int code,long at,String msg){if(code>59)return;
Intent i=new Intent(MainActivity.this,AlarmReceiver.class).putExtra("m",msg);
PendingIntent p=PendingIntent.getBroadcast(MainActivity.this,code,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
am().setAlarmClock(new AlarmManager.AlarmClockInfo(at,p),p);}
@JavascriptInterface public void cancel(){for(int c=0;c<60;c++){PendingIntent p=PendingIntent.getBroadcast(MainActivity.this,c,new Intent(MainActivity.this,AlarmReceiver.class),PendingIntent.FLAG_NO_CREATE|PendingIntent.FLAG_IMMUTABLE);if(p!=null){am().cancel(p);p.cancel();}}}
@JavascriptInterface public boolean strict(boolean on,long until){
Intent s=new Intent(MainActivity.this,StrictService.class);
if(!on){stopService(s);return true;}
if(!Settings.canDrawOverlays(MainActivity.this)){startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:"+getPackageName())));return false;}
AppOpsManager a=(AppOpsManager)getSystemService(APP_OPS_SERVICE);
if(a.checkOpNoThrow("android:get_usage_stats",android.os.Process.myUid(),getPackageName())!=AppOpsManager.MODE_ALLOWED){startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));return false;}
s.putExtra("u",until);if(Build.VERSION.SDK_INT>=26)startForegroundService(s);else startService(s);return true;}
@JavascriptInterface public void open(String u){startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(u)));}
}}
