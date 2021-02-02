package com.example.pepcoder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Spinner spinner;
    private TextView textView;

    //For Requesting Permission of Read Storage
    private static final int PERMISSION_REQUEST_STORAGE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)   !=  PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            /*for (int i=0; i<grantResults.length; i++) {
                //System.out.print(grantResults[i] + ", ");
                Log.d("Yeh rha bhaiya!", grantResults[i] + ": " + i);
            }
             */

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permission Not Granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }


    public void calculateHammingDistance(View view)
    {

        Intent intent = new Intent(MainActivity.this,HammingCalculator.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void calculateAminoWeight(View view)
    {
        Intent intent = new Intent(MainActivity.this,AminoWeightCalculator.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void calculateNucleotideFrequency(View view)
    {
        Intent intent = new Intent(MainActivity.this,NucleotideFreCalculator.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void calculateGC(View view)
    {
        Intent intent = new Intent(MainActivity.this,GcCalculator.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void convertToAmino(View view)
    {
        Intent intent = new Intent(MainActivity.this,RnaDnaConvertor.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void aboutApp(View view)
    {
        Intent intent = new Intent(MainActivity.this,AboutIt.class);
        startActivity(intent);
        //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}