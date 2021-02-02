package com.example.pepcoder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class hamming_distance_result extends AppCompatActivity {

    TextView textView_hamming_distance_result;
    TextView textView_DNA1;
    TextView textView_DNA2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamming_distance_result);
        textView_hamming_distance_result = findViewById(R.id.textView_hamming_distance_result);
        textView_DNA1 = findViewById(R.id.textView_DNA1);
        textView_DNA2 = findViewById(R.id.textView_DNA2);

        String InputSequence1 = getIntent().getStringExtra("KEY1");
        InputSequence1 = InputSequence1.toUpperCase();
        String InputSequence2 = getIntent().getStringExtra("KEY2");
        InputSequence2 = InputSequence2.toUpperCase();

        if (InputSequence1.equals("") || InputSequence2.equals("")) {
            Toast.makeText(this, "Input not given in either of the boxes !", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

        if (!checkIfDNA(InputSequence1)) {
            Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,T,G,C or a,t,g,c!", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

        if (!checkIfDNA(InputSequence2)) {
            Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,T,G,C or a,t,g,c!", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

        int HamingDistance = CalculateHamingDistance(InputSequence1, InputSequence2);
        if (HamingDistance == -1) {
            Toast.makeText(this, "Sequences length should be equal !", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

        textView_hamming_distance_result.setText((HamingDistance + ""));

    }

    public int CalculateHamingDistance(String str, String str2) {

        if (str.length()!=str2.length())
        {
            //System.out.println("The length pf string is not equal to calculate hamming distance");
            return -1;
        }

        int count=0;
        String dna1 = "";
        String dna2 = "";
        for (int i=0;i<str.length();i++) {
            if (str.charAt(i) != str2.charAt(i)) {
                count++;
                dna1 = dna1 + " '" + str.charAt(i) + "' ";
                dna2 = dna2 + " '" + str2.charAt(i) + "' ";
            }
            else {
                dna1 = dna1 + str.charAt(i) + " ";
                dna2 = dna2 + str2.charAt(i) + " ";
            }

            textView_DNA1.setText(dna1);
            textView_DNA2.setText(dna2);
        }
        //System.out.println("Hamming Distance: " + count);

        return count;
    }


    public boolean checkIfDNA(String string) {
        String[] splittedString = string.split("");
        for (int i=0; i<splittedString.length; i++) {
//            if (!(splittedString[i].equals("A") || splittedString[i].equals("T") || splittedString[i].equals("G") || splittedString[i].equals("C"))) {
//                return false;
//            }
            if (splittedString[i].equals("A")) {
                continue;
            }
            else if (splittedString[i].equals("T")) {
                continue;
            }
            else if (splittedString[i].equals("G")) {
                continue;
            }
            else if (splittedString[i].equals("C")) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }
}