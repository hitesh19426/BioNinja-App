package com.example.pepcoder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class NucleotideFreCalculator extends AppCompatActivity
{
    Button btn;
    EditText rec_input;
    Button button_UploadSeq_Nfreq;

    //For Reading the file from the storage
    public static final int READ_REQUEST_CODE = 42;
    Intent toRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nucleotide_fre_calculator);
        toRes = new Intent(NucleotideFreCalculator.this, NfResult.class);

        btn = findViewById(R.id.submit_btn);
        button_UploadSeq_Nfreq = findViewById(R.id.button_UploadSeq_Nfreq);

        btn.setOnClickListener(v -> {
            rec_input = findViewById(R.id.input_nf);
            String data = rec_input.getText().toString();
            //Log.v("here",data);

            toRes.putExtra("data1",data);
            toRes.putExtra("data2","");
            startActivity(toRes);
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        });

        button_UploadSeq_Nfreq.setOnClickListener(v -> {
            performFileSearch();
        });

    }

    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);
                if (path.contains("emulated")) {
                    path = path.substring(path.indexOf("0") + 1);
                }
                //Toast.makeText(this, "File Path: " + path, Toast.LENGTH_SHORT).show();
                //textView_FileContent.setText(readContent(path));
                String SequenceInTextFile = readContent(path);

                toRes.putExtra("data2",SequenceInTextFile);
                toRes.putExtra("data1","");
                startActivity(toRes);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        }
    }

    public String readContent(String filePath) {
        File file = new File(Environment.getExternalStorageDirectory(), filePath);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }
    public void AboutNF(View view)
    {
        Intent intent = new Intent(NucleotideFreCalculator.this,NF_AboutIT.class);
        startActivity(intent);
        //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

}