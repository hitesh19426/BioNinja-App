package com.example.pepcoder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class amino_weight_result extends AppCompatActivity {

    TextView textView_amino_weight_result;
    TextView textView_amino_Seq_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amino_weight_result);

        textView_amino_weight_result = findViewById(R.id.textView_amino_weight_result);
        textView_amino_Seq_result = findViewById(R.id.textView_amino_Seq_result);

        String inputSequence = getIntent().getStringExtra("KEY1");

        if (inputSequence.equals("")) {
            inputSequence = getIntent().getStringExtra("KEY2");
            if (inputSequence.equals("")) {
                Toast.makeText(this, "Either upload txt file or Paste the sequence in the box above!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }
            else {
                inputSequence = inputSequence.substring(0, inputSequence.length()-1);
                inputSequence = inputSequence.toUpperCase();

                if (!checkIfDNA(inputSequence)) {
                    Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,U,G,C or a,u,g,c!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    return;
                }

                int MASSofProtein = getTheMassCalculated(inputSequence);
                if (MASSofProtein == -1) {
                    Toast.makeText(this, "Sequence should contain AUG/aug for initiation of Translation!", Toast.LENGTH_LONG).show();
                    onBackPressed();
                    return;
                }
                else {
                    textView_amino_Seq_result.setText(getTheProteinSeq(inputSequence) + "");
                    textView_amino_weight_result.setText((MASSofProtein + ""));
                }
            }
        }
        else {
            inputSequence = inputSequence.toUpperCase();

            if (!checkIfDNA(inputSequence)) {
                Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,U,G,C or a,u,g,c!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }

            int MASSofProtein = getTheMassCalculated(inputSequence);
            if (MASSofProtein == -1) {
                Toast.makeText(this, "Sequence should contain AUG/aug for initiation of Translation!", Toast.LENGTH_LONG).show();
                onBackPressed();
                return;
            }
            else {
                textView_amino_Seq_result.setText(getTheProteinSeq(inputSequence) + "");
                textView_amino_weight_result.setText((MASSofProtein + ""));
            }
        }


    }


    public String getTheProteinSeq(String RNA) {
        RNA = RNA.toUpperCase();
        ArrayList<String> ProteinSeq= Convert_mRNA_to_Protein(RNA);
        String Protein_final = "";
        for (int i = 0; i < ProteinSeq.size(); i++)
            Protein_final += ProteinSeq.get(i) + "-";
        Protein_final += "Stop";
        return Protein_final;
    }


    public int getTheMassCalculated(String RNA) {

        RNA = RNA.toUpperCase();

        ArrayList<String> ProteinSeq= Convert_mRNA_to_Protein(RNA);

        return Calculate_Mass_from_Protein(ProteinSeq);

    }

    //Calculate Mass of the given Protein
    public int Calculate_Mass_from_Protein(ArrayList<String> protein) {
        if (protein.size() == 0) {
            return -1;
        }
        HashMap<String, Integer> mass=new HashMap<>();
        initialiseMass(mass);

        int massValue=0;
        for (int i=0;i<protein.size();i++)
            massValue += mass.get(protein.get(i));
        //System.out.println("Mass : " + massValue);
        return massValue;
    }


    // program to convert mRna to protein sequence
    public ArrayList<String> Convert_mRNA_to_Protein(String rna)
    {

        if (rna.equals("EXIT")) {
            return new ArrayList<String>();
        }


        HashMap<String, String> map=new HashMap<String, String>();
        initialise(map);

        int startPoint=-1;
        for (int i=0;i<rna.length()-2;i++)
        {
            if (rna.substring(i, i+3).equals("AUG"))
            {
                startPoint=i;
                break;
            }
        }

        if (startPoint == -1) {
            return new ArrayList<String>();
        }

        ArrayList<String> protein=new ArrayList<String>();
        for (int i=startPoint;i<rna.length()-2;i+=3)
        {
            String AminoAcidSeq=map.get(rna.substring(i, i+3));
            if (AminoAcidSeq.equals("Stop"))
                break;
            else
                protein.add(AminoAcidSeq);
        }

        return protein;

    }

    private void initialiseMass(HashMap<String, Integer> mass) {
        // TODO Auto-generated method stub
        mass.put("Ala", 89);
        mass.put("Arg", 174);
        mass.put("Asn", 132);
        mass.put("Asp", 133);

        mass.put("Cys", 121);
        mass.put("Gln", 146);
        mass.put("Glu", 147);
        mass.put("Gly", 75);

        mass.put("His", 155);
        mass.put("Ile", 131);
        mass.put("Leu", 131);
        mass.put("Lys", 146);

        mass.put("Met", 149);
        mass.put("Phe", 165);
        mass.put("Pro", 115);
        mass.put("Ser", 105);

        mass.put("Thr", 119);
        mass.put("Trp", 204);
        mass.put("Tyr", 181);
        mass.put("Val", 117);

    }

    private void initialise(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        map.put("UUU", "Phe");
        map.put("UUC", "Phe");

        map.put("UUA", "Leu");
        map.put("UUG", "Leu");
        map.put("CUU", "Leu");
        map.put("CUC", "Leu");
        map.put("CUA", "Leu");
        map.put("CUG", "Leu");

        map.put("AUG", "Met");

        map.put("GUU", "Val");
        map.put("GUC", "Val");
        map.put("GUA", "Val");
        map.put("GUG", "Val");

        map.put("UCU", "Ser");
        map.put("UCC", "Ser");
        map.put("UCA", "Ser");
        map.put("UCG", "Ser");

        map.put("CCU", "Pro");
        map.put("CCC", "Pro");
        map.put("CCA", "Pro");
        map.put("CCG", "Pro");

        map.put("ACU", "Thr");
        map.put("ACC", "Thr");
        map.put("ACA", "Thr");
        map.put("ACG", "Thr");

        map.put("GCU", "Ala");
        map.put("GCC", "Ala");
        map.put("GCA", "Ala");
        map.put("GCG", "Ala");

        map.put("UAC", "Tyr");
        map.put("UAU", "Tyr");

        map.put("UAA", "Stop");
        map.put("UAG", "Stop");
        map.put("UGA", "Stop");

        map.put("CAU", "His");
        map.put("CAC", "His");

        map.put("CAA", "Gln");
        map.put("CAG", "Gln");

        map.put("AAU", "Asn");
        map.put("AAC", "Asn");

        map.put("AAA", "Lys");
        map.put("AAG", "Lys");

        map.put("GAU", "Asp");
        map.put("GAC", "Asp");

        map.put("GAA", "Glu");
        map.put("GAG", "Glu");

        map.put("UGC", "Cys");
        map.put("UGU", "Cys");

        map.put("UGG", "Trp");

        map.put("CGU", "Arg");
        map.put("CGC", "Arg");
        map.put("CGA", "Arg");
        map.put("CGG", "Arg");
        map.put("AGA", "Arg");
        map.put("AGG", "Arg");

        map.put("AGU", "Ser");
        map.put("AGC", "Ser");

        map.put("GGU", "Gly");
        map.put("GGC", "Gly");
        map.put("GGA", "Gly");
        map.put("GGG", "Gly");

        map.put("AUU", "Ile");
        map.put("AUA", "Ile");
        map.put("AUC", "Ile");

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
            else if (splittedString[i].equals("U")) {
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