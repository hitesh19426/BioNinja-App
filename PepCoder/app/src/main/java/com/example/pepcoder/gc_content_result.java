package com.example.pepcoder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.ArrayList;

public class gc_content_result extends AppCompatActivity {

    TextView setGC;
    TextView setA;
    TextView setT;
    TextView setG;
    TextView setC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_content_result);
        setGC = findViewById(R.id.textView_GC_Content_result);
        setA = findViewById(R.id.textView_A_result);
        setG = findViewById(R.id.textView_G_result);
        setT = findViewById(R.id.textView_T_result);
        setC = findViewById(R.id.textView_C_result);



        String InputSequence = getIntent().getStringExtra("key");
        //InputSequence = getIntent().getStringExtra("key2");
        //InputSequence = InputSequence.substring(0, InputSequence.length()-1);
        //Toast.makeText(this, InputSequence.equals("") + "", Toast.LENGTH_SHORT).show();


        if (InputSequence.equals("")) {
            InputSequence = getIntent().getStringExtra("key2");


            if (InputSequence.equals("")) {
                Toast.makeText(this, "Either upload txt file or Paste the sequence in the box above!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }
            else {
                InputSequence = InputSequence.substring(0, InputSequence.length()-1);
                InputSequence = InputSequence.toUpperCase();

                if (!checkIfDNA(InputSequence)) {
                    Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,T,G,C or a,t,g,c!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    return;
                }
                ArrayList<Double> result = (GC_Content_Calculate(InputSequence));

                if ((result.get(5) + "").length() <= 7) {
                    //ArrayList<Double> result = (GC_Content_Calculate(InputSequence));
                    setGC.setText((( result.get(5)+ "") + "%"));
                    setA.setText(( result.get(0)+ ""));
                    setT.setText(( result.get(1)+ ""));
                    setG.setText(( result.get(2)+ ""));
                    setC.setText(( result.get(3)+ ""));
                    drawPie(result.get(0), result.get(1), result.get(2), result.get(3));
                } else {

                    setGC.setText((( result.get(5)+ "").substring(0, 6) + "%"));
                    setA.setText(( result.get(0)+ ""));
                    setT.setText(( result.get(1)+ ""));
                    setG.setText(( result.get(2)+ ""));
                    setC.setText(( result.get(3)+ ""));
                    drawPie(result.get(0), result.get(1), result.get(2), result.get(3));
                    //setGC.setText(((GC_Content_Calculate(InputSequence) + "").substring(0, 6) + "%"));
                }
            }
        }
        else {
            InputSequence = InputSequence.toUpperCase();

            if (!checkIfDNA(InputSequence)) {
                Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,T,G,C or a,t,g,c!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }
            ArrayList<Double> result = (GC_Content_Calculate(InputSequence));

            if ((result.get(5) + "").length() <= 7) {
//                setGC.setText(((GC_Content_Calculate(InputSequence) + "") + "%"));
                //ArrayList<Double> result = (GC_Content_Calculate(InputSequence));
                setGC.setText((( result.get(5)+ "") + "%"));
                setA.setText(( result.get(0)+ ""));
                setT.setText(( result.get(1)+ ""));
                setG.setText(( result.get(2)+ ""));
                setC.setText(( result.get(3)+ ""));
                drawPie(result.get(0), result.get(1), result.get(2), result.get(3));
            } else {
                //setGC.setText(((GC_Content_Calculate(InputSequence) + "").substring(0, 6) + "%"));
                setGC.setText((( result.get(5)+ "").substring(0, 6) + "%"));
                setA.setText(( result.get(0)+ ""));
                setT.setText(( result.get(1)+ ""));
                setG.setText(( result.get(2)+ ""));
                setC.setText(( result.get(3)+ ""));
                drawPie(result.get(0), result.get(1), result.get(2), result.get(3));
            }
        }
    }

    public ArrayList<Double> GC_Content_Calculate(String str) {

        double counta=0;
        double countt=0;
        double countg=0;
        double countc=0;
        double gc = 0;
        ArrayList<Double> arraylist = new ArrayList<>();
        for (int i=0;i<str.length();i++)
        {
            if (str.charAt(i)=='G')
            {
                countg++;
            }
            else if(str.charAt(i)=='A')
            {
                counta++;
            }
            else if(str.charAt(i)=='T')
            {
                countt++;
            }
            else if (str.charAt(i) == 'C')
            {
                countc++;
            }
            else {
                //Do Nothing
            }

        }

        gc += countc+countg;
        arraylist.add(counta);
        arraylist.add(countt);
        arraylist.add(countg);
        arraylist.add(countc);
        arraylist.add(gc);
        double gc_percent = (double)((double)gc/str.length())*100;
        arraylist.add(gc_percent);
        return arraylist;
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
    public void drawPie(double adenine, double thymine, double guanine, double cytosine)
    {
        AnimatedPieView animatedPieView = findViewById(R.id.animatedPieView);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.startAngle(-90)// Starting angle offset
                .addData(new SimplePieInfo(adenine, Color.parseColor("#ff00ff"), "Adenine"))//Data (bean that implements the IPieInfo interface)
                .addData(new SimplePieInfo(thymine, Color.parseColor("#33cc5a"), "Thymine"))
                .addData(new SimplePieInfo(guanine, Color.parseColor("#ff0006"), "Guanine"))
                .addData(new SimplePieInfo(cytosine, Color.parseColor("#00ffce"), "Cytosine"))
                .drawText(true).strokeMode(false)
                .duration(2000).textSize(40);// draw pie animation duration

        animatedPieView.applyConfig(config);
        animatedPieView.start();
    }
}