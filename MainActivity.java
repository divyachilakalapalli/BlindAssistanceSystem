package com.example.divya.blindassistance;

import android.app.LauncherActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView txvResult;
    private TextToSpeech myTTS;
    MediaPlayer c;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);

        initializeTextToSpeech();
    }

    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (myTTS.getEngines().size() == 0) {
                    Toast.makeText(MainActivity.this, "There is no TTS ENGINE ON YOUR DEVICE", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    myTTS.setLanguage(Locale.US);
                    speak("Hello! I am ready.");
                }
            }
        });
    }

    private void speak(String s) {
        if (Build.VERSION.SDK_INT >= 21) {
            myTTS.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            myTTS.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }

    }


    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "your device don't support speech input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String s = result.get(0);
                    txvResult.setText(result.get(0));
                    if (s.indexOf("what") != -1) {
                        if (s.indexOf("your name") != -1) {
                            speak("My name is blind assistance system");
                        }
                        if (s.indexOf("time") != -1) {
                            Date now = new Date();
                            String time = DateUtils.formatDateTime(this, now.getTime(), DateUtils.FORMAT_SHOW_TIME);
                            speak("The time now is " + time);
                            txvResult.setText("The time now is " + time);
                        }
                        if (s.indexOf("date") != -1) {
                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            speak("today date is "+ date);
                            txvResult.setText("today date is "+ date);
                        }
                    } else if (s.indexOf("open") != -1) {
                        if (s.indexOf("browser") != -1) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                            startActivity(intent);
                        }
                    } else if (s.indexOf("make") != -1) {
                        if (s.indexOf("call") != -1) {
                           Intent openPhone = getPackageManager().getLaunchIntentForPackage("com.example.divya.phone");
                           startActivity(openPhone);
                        }
                    } else if (s.indexOf("send") != -1) {
                        if (s.indexOf("message") != -1) {
                            Intent openmsg = getPackageManager().getLaunchIntentForPackage("com.example.divya.sms");
                            startActivity(openmsg);
                        }

                    }
                    else if (s.indexOf("play") != -1) {

                        if (s.indexOf("gaali vaaluga") != -1) {
                            MediaPlayer gaali = MediaPlayer.create(MainActivity.this, R.raw.gaalivaaluga);
                            c=gaali;
                                gaali.start();
                            txvResult.setText("playing gaali vaaluga");
                           str= "playing gaali vaaluga";
                        }
                        else if (s.indexOf("Ringa Ringa") != -1) {
                            MediaPlayer ringaa= MediaPlayer.create(MainActivity.this,R.raw.ringa);
                            c=ringaa;
                            ringaa.start();
                            txvResult.setText("playing ringa ringa");
                            str="playing ringa ringa";
                        }
                       else if (s.indexOf("inkem inkem kaavaale") != -1) {
                            MediaPlayer ink= MediaPlayer.create(MainActivity.this,R.raw.inkem);
                           c=ink;
                            ink.start();
                            txvResult.setText("playing inkem inkem kavale");
                            str="playing inkem inkem kavale";
                        }
                        else if (s.indexOf("rangamma Mangamma") != -1) {
                            MediaPlayer ranga= MediaPlayer.create(MainActivity.this,R.raw.rangamanga);
                            c=ranga;
                            ranga.start();
                            txvResult.setText("playing rangamma mangamma");
                            str="playing rangamma mangamma";
                        }
                       else if (s.indexOf("swing Zara") != -1) {
                            MediaPlayer swingg= MediaPlayer.create(MainActivity.this,R.raw.swingzara);
                            c=swingg;
                            swingg.start();
                            txvResult.setText("playing swing zara");
                            str="playing swing zara";
                        }
                       else if (s.indexOf("pilla Ra") != -1) {
                            MediaPlayer pilla= MediaPlayer.create(MainActivity.this,R.raw.pilaaraa);
                            c=pilla;
                            pilla.start();
                            txvResult.setText("playing pilla ra");
                            str="playing pilaaa raa";
                        }
                        else{
                            speak("sorry!this song is not available in your database");
                        }

                    }
                    else if (s.indexOf("resume") != -1) {
                        c.start();
                        txvResult.setText(str);
                    }
                    else if (s.indexOf("stop") != -1) {
                        c.stop();
                        txvResult.setText("music stopped");
                    }
                    else if (s.indexOf("pause") != -1) {
                        c.pause();
                        txvResult.setText("music paused");
                    }
                    else{
                        speak("sorry! no such functionality exists in this app. ");
                    }
                    break;
                }
        }
    }
}