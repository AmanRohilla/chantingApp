package com.example.mantra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    RadioButton radioButton;
    RadioGroup radioGroup;
    int chantCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        radioGroup = findViewById(R.id.radioGroup);
        
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if(selectedId == -1){
                    Toast.makeText(getApplicationContext(), "Select something", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(radioButton.getId() == findViewById(R.id.letMeDecide).getId()){
                        showUserSelectionAlertBox();
                    }
                    else if(radioButton.getId() == findViewById(R.id.noob).getId()){
                        chantCount = 11;
                        goToNextActivity(chantCount);
                    }
                    else if(radioButton.getId() == findViewById(R.id.medium).getId()){
                        chantCount = 21;
                        goToNextActivity(chantCount);
                    }
                    else if(radioButton.getId() == findViewById(R.id.average).getId()){
                        chantCount = 51;
                        goToNextActivity(chantCount);
                    }
                    else if(radioButton.getId() == findViewById(R.id.aboveAverage).getId()){
                        chantCount = 101;
                        goToNextActivity(chantCount);
                    }
                }
            }
        });
    }

    void goToNextActivity(int chantCount){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        intent.putExtra("chantCount",chantCount);
        startActivity(intent);
    }

    void showUserSelectionAlertBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Number Of Times You Wanna Chant.");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(Integer.parseInt(input.getText().toString()) > 100000){
                    Toast.makeText(getApplicationContext(), "Enter some low value.", Toast.LENGTH_LONG).show();
                }
                else{
                    chantCount = Integer.parseInt(input.getText().toString());
                    goToNextActivity(chantCount);
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
