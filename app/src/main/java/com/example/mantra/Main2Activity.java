package com.example.mantra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {
    RelativeLayout mainScreen;
    LottieAnimationView animationView;
    LinearLayout scoreScreen;
    TextView tapOnscreenTextView;
    TextView targetCountView;
    TextView currentChantCountView;
    boolean firstTimeTouched = false;
    int totalChantingCount;
    int currentChantCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mainScreen = (RelativeLayout)findViewById(R.id.mainScreen);
        animationView = (LottieAnimationView)findViewById(R.id.animationScreen);
        scoreScreen = (LinearLayout) findViewById(R.id.scoreScreen);
        tapOnscreenTextView = (TextView) findViewById(R.id.tapOnScreenView);
        targetCountView = (TextView) findViewById(R.id.targetScoreView);
        currentChantCountView = (TextView) findViewById(R.id.currentChantCountView);

        //To get data from the previous activity
        Bundle bundle = getIntent().getExtras();
        totalChantingCount = bundle.getInt("chantCount");

        mainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstTimeTouched){
                    firstTimeTouched = true;
                    setFirstTimeTouched();
                }
                else{
                    startChanting();
                }
            }
        });


    }

    void setFirstTimeTouched(){
        tapOnscreenTextView.setVisibility(View.INVISIBLE);
        scoreScreen.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
        setTarget(totalChantingCount);
        animationView.playAnimation();
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.EFFECT_TICK));
        }
    }

    void setTarget(int chantingCount){
        targetCountView.setText("  TARGET: "+chantingCount+" ");
    }

    void startChanting(){
        currentChantCount = currentChantCount+1;
        changeChantingDisplay(currentChantCount);
        chantTouchVibration();
        animationView.playAnimation();
        checkIfCountEqualToTarget(currentChantCount);
    }

    void changeChantingDisplay(int count){
        currentChantCountView.setText("  CHANTED: "+count+" ");
    }

    void chantTouchVibration(){
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.EFFECT_TICK));
        }
    }

    void checkIfCountEqualToTarget(int count){
        if(count == totalChantingCount){
            Bundle bundle = new Bundle();
            Intent intent = new Intent(Main2Activity.this,CongratulationsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        quitDialogBox();
    }

    void quitDialogBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You really want to quit?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Bundle bundle = new Bundle();
                                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
