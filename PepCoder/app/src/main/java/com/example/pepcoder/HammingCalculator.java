package com.example.pepcoder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HammingCalculator extends AppCompatActivity {

    Button button_submit_hamming_result;
    EditText EditText_Sequence1Input_Hamming;
    EditText EditText_Sequence2Input_Hamming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamming_calculator);


        button_submit_hamming_result = findViewById(R.id.button_submit_hamming_result);
        button_submit_hamming_result.setOnClickListener(v -> {
            EditText_Sequence1Input_Hamming = findViewById(R.id.EditText_Sequence1Input_Hamming);
            EditText_Sequence2Input_Hamming = findViewById(R.id.EditText_Sequence2Input_Hamming);
            String Sequence1 = EditText_Sequence1Input_Hamming.getText().toString();
            String Sequence2 = EditText_Sequence2Input_Hamming.getText().toString();

            //Log.v("here",data);
            Intent toRes = new Intent(HammingCalculator.this, hamming_distance_result.class);
            toRes.putExtra("KEY1",Sequence1);
            toRes.putExtra("KEY2",Sequence2);

            startActivity(toRes);
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        });
    }


    public void AboutHammingDistance(View view)
    {
        Intent intent = new Intent(HammingCalculator.this,HammingDistanceAboutIT.class);
        startActivity(intent);
        //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }


}