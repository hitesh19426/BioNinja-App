package com.example.pepcoder;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

public class NfResult extends AppCompatActivity
{
    TextView aa;
    TextView at;
    TextView ag;
    TextView ac;
    TextView ta;
    TextView tt;
    TextView tg;
    TextView tc;
    TextView ga;
    TextView gt;
    TextView gc;
    TextView gg;
    TextView ca;
    TextView ct;
    TextView cg;
    TextView cc;
    TextView[] tv = new TextView[16];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nf_result);
        aa = findViewById(R.id.aa);
        at = findViewById(R.id.at);
        ag = findViewById(R.id.ag);
        ac = findViewById(R.id.ac);
        ta = findViewById(R.id.ta);
        tt = findViewById(R.id.tt);
        tg = findViewById(R.id.tg);
        tc = findViewById(R.id.tc);
        ga = findViewById(R.id.ga);
        gt = findViewById(R.id.gt);
        gg = findViewById(R.id.gg);
        gc = findViewById(R.id.gc);
        ca = findViewById(R.id.ca);
        ct = findViewById(R.id.ct);
        cg = findViewById(R.id.cg);
        cc = findViewById(R.id.cc);
        tv[0] = aa;
        tv[1] = at;
        tv[2] = ag;
        tv[3] = ac;
        tv[4] = ta;
        tv[5] = tt;
        tv[6] = tg;
        tv[7] = tc;
        tv[8] = ga;
        tv[9] = gt;
        tv[10] = gg;
        tv[11] = gc;
        tv[12] = ca;
        tv[13] = ct;
        tv[14] = cg;
        tv[15] = cc;

        String received = getIntent().getStringExtra("data1");

        if (received.equals("")) {
            received = getIntent().getStringExtra("data2");

            if (received.equals("")) {
                Toast.makeText(this, "Either upload txt file or Paste the sequence in the box above!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }
            else {
                received = received.substring(0, received.length()-1);
                received = received.toUpperCase();

                if (!checkIfDNA(received)) {
                    Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,T,G,C or a,t,g,c!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    return;
                }

                int[] count = NucleotideFreqCalculator(received);
                drawPie(count);
            }
        }
        else {
            received = received.toUpperCase();
            if (!checkIfDNA(received)) {
                Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,T,G,C or a,t,g,c!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }
            int[] count = NucleotideFreqCalculator(received);
            drawPie(count);
        }

    }
    public int[] NucleotideFreqCalculator(String in) {

        String[] pairs= {"AA", "AT", "AG", "AC", "TA", "TT", "TG", "TC", "GA", "GT", "GG", "GC", "CA", "CT", "CG", "CC"};
        int[] count= {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};

        for (int i=0;i<in.length()-1;i++)
        {
            for (int j=0;j<pairs.length;j++)
                if (in.substring(i, i+2).equals(pairs[j]))
                    count[j]++;
        }

        for (int i=0;i<16;i++)
        {

            tv[i].setText(count[i] + "");
//            System.out.println(pairs[i]+" : " + count[i]);
        }
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



    public void drawPie(int[] count)
    {
        AnimatedPieView animatedPieView = findViewById(R.id.animatedPieView);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.startAngle(-90)// Starting angle offset
                .addData(new SimplePieInfo(count[0], Color.parseColor("#00cc00")))
                .addData(new SimplePieInfo(count[1], Color.parseColor("#FFcc00")))
                .addData(new SimplePieInfo(count[2], Color.parseColor("#ccFFFF")))
                .addData(new SimplePieInfo(count[3], Color.parseColor("#FF0000")))

                .addData(new SimplePieInfo(count[4], Color.parseColor("#FF6600")))
                .addData(new SimplePieInfo(count[5], Color.parseColor("#0000FF")))
                .addData(new SimplePieInfo(count[6], Color.parseColor("#FF00FF")))
                .addData(new SimplePieInfo(count[7], Color.parseColor("#FFFFFF")))

                .addData(new SimplePieInfo(count[8], Color.parseColor("#0099FF")))
                .addData(new SimplePieInfo(count[9], Color.parseColor("#666666")))
                .addData(new SimplePieInfo(count[10], Color.parseColor("#FFFF66")))
                .addData(new SimplePieInfo(count[11], Color.parseColor("#FFcccc")))

                .addData(new SimplePieInfo(count[12], Color.parseColor("#00FFFF")))
                .addData(new SimplePieInfo(count[13], Color.parseColor("#FF6666")))
                .addData(new SimplePieInfo(count[14], Color.parseColor("#9900FF")))
                .addData(new SimplePieInfo(count[15], Color.parseColor("#663300")))

                .drawText(false)
                .duration(2000);
        aa.setTextColor(Color.parseColor("#00cc00"));
        at.setTextColor(Color.parseColor("#FFcc00"));
        ag.setTextColor(Color.parseColor("#ccFFFF"));
        ac.setTextColor(Color.parseColor("#FF0000"));

        ta.setTextColor(Color.parseColor("#FF6600"));
        tt.setTextColor(Color.parseColor("#0000FF"));
        tg.setTextColor(Color.parseColor("#FF00FF"));
        tc.setTextColor(Color.parseColor("#FFFFFF"));

        ga.setTextColor(Color.parseColor("#0099FF"));
        gt.setTextColor(Color.parseColor("#666666"));
        gg.setTextColor(Color.parseColor("#FFFF66"));
        gc.setTextColor(Color.parseColor("#FFcccc"));

        ca.setTextColor(Color.parseColor("#00FFFF"));
        ct.setTextColor(Color.parseColor("#FF6666"));
        cg.setTextColor(Color.parseColor("#9900FF"));
        cc.setTextColor(Color.parseColor("#663300"));

        animatedPieView.applyConfig(config);
        animatedPieView.start();
    }
}
