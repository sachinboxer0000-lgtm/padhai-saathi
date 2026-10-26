package com.sachin.padhai;
import android.app.Activity;import android.content.Intent;import android.os.Bundle;import android.widget.*;
public class BlockActivity extends Activity{
protected void onCreate(Bundle b){super.onCreate(b);
LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setGravity(17);l.setBackgroundColor(0xFF0F1325);l.setPadding(60,60,60,60);
TextView t=new TextView(this);t.setText("अभी पढ़ाई का समय है\n\nपढ़ाई पूरी करके फिर फ़ोन चलाओ।");t.setTextColor(0xFFECEFF9);t.setTextSize(22);t.setGravity(17);
Button x=new Button(this);x.setText("पढ़ाई पर वापस जाओ");x.setOnClickListener(v->back());
l.addView(t);l.addView(x);setContentView(l);}
void back(){startActivity(new Intent(this,MainActivity.class));finish();}
public void onBackPressed(){back();}}
