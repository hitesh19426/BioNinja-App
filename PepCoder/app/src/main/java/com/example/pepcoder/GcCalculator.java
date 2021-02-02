package com.example.pepcoder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GcCalculator extends AppCompatActivity {

    Button button_submit_gc_calculator;
    EditText EditText_SequenceInput;
    Button button_UploadSeq_GC;

    //For Reading the file from the storage
    public static final int READ_REQUEST_CODE = 42;
    Intent toRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_calculator);

        button_submit_gc_calculator = findViewById(R.id.button_submit_gc_calculator);
        button_UploadSeq_GC = findViewById(R.id.button_UploadSeq_GC);
        toRes = new Intent(GcCalculator.this, gc_content_result.class);

        button_submit_gc_calculator.setOnClickListener(v -> {
            EditText_SequenceInput = findViewById(R.id.EditText_SequenceInput);
            String InputSequence = EditText_SequenceInput.getText().toString();
            //Log.v("here",data);

            toRes.putExtra("key",InputSequence);
            toRes.putExtra("key2", "");
            startActivity(toRes);
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        });

        button_UploadSeq_GC.setOnClickListener(v -> {
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

                toRes.putExtra("key2",SequenceInTextFile);
                toRes.putExtra("key","");
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


    public void AboutGCContent(View view)
    {
        Intent intent = new Intent(GcCalculator.this,GC_Content_AboutIT.class);
        startActivity(intent);
        //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

}