package com.example.pepcoder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class dna_to_rna_result extends AppCompatActivity {

    TextView textView_DNA_to_mRNA_result;
    TextView textView_mRNA_to_Protein_result;
    TextView textView_Protein_Weight_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dna_to_rna_result);
        textView_DNA_to_mRNA_result = findViewById(R.id.textView_DNA_to_mRNA_result);
        textView_mRNA_to_Protein_result = findViewById(R.id.textView_mRNA_to_Protein_result);
        textView_Protein_Weight_result = findViewById(R.id.textView_Protein_Weight_result);


        String InputSequence_DNAtoRNA = getIntent().getStringExtra("KEY_DNAtoRNA1");

        if (InputSequence_DNAtoRNA.equals("")) {
            InputSequence_DNAtoRNA = getIntent().getStringExtra("KEY_DNAtoRNA2");
            if (InputSequence_DNAtoRNA.equals("")) {
                Toast.makeText(this, "Either upload txt file or Paste the sequence in the box above!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }
            else {
                InputSequence_DNAtoRNA = InputSequence_DNAtoRNA.substring(0, InputSequence_DNAtoRNA.length()-1);
                String DNA = InputSequence_DNAtoRNA;
                DNA = DNA.toUpperCase();

                if (!checkIfDNA(DNA)) {
                    Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,T,G,C or a,t,g,c!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    return;
                }

                String RNA = Convert_DNA_To_RNA(DNA);
                ArrayList<String> ProteinSeq = Convert_mRNA_to_Protein(RNA);

                String Protein_final = "";
                for (int i = 0; i < ProteinSeq.size(); i++)
                    Protein_final += ProteinSeq.get(i) + "-";
                Protein_final += "Stop";

                int MASSofProtein = Calculate_Mass_from_Protein(ProteinSeq);
                if (MASSofProtein == -1) {
                    Toast.makeText(this, "Sequence should contain TAC/tac for initiation of Transcription!", Toast.LENGTH_LONG).show();
                    onBackPressed();
                    return;
                } else {
                    textView_DNA_to_mRNA_result.setText((RNA + ""));
                    textView_mRNA_to_Protein_result.setText((Protein_final + ""));
                    textView_Protein_Weight_result.setText((MASSofProtein + ""));
                }

            }
        }
        else {
            String DNA = InputSequence_DNAtoRNA;
            DNA = DNA.toUpperCase();

            if (!checkIfDNA(DNA)) {
                Toast.makeText(this, "Sequence shouldn't contain any alphabets other than A,T,G,C or a,t,g,c!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }

            String RNA = Convert_DNA_To_RNA(DNA);
            ArrayList<String> ProteinSeq = Convert_mRNA_to_Protein(RNA);

            String Protein_final = "";
            for (int i = 0; i < ProteinSeq.size(); i++)
                Protein_final += ProteinSeq.get(i) + "-";
            Protein_final += "Stop";

            int MASSofProtein = Calculate_Mass_from_Protein(ProteinSeq);
            if (MASSofProtein == -1) {
                Toast.makeText(this, "Sequence should contain TAC/tac for initiation of Transcription!", Toast.LENGTH_LONG).show();
                onBackPressed();
                return;
            } else {
                textView_DNA_to_mRNA_result.setText((RNA + ""));
                textView_mRNA_to_Protein_result.setText((Protein_final + ""));
                textView_Protein_Weight_result.setText((MASSofProtein + ""));
            }
        }



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


    // DNA to mRna program
    public String Convert_DNA_To_RNA(String str)
    {
        // TODO Auto-generated method stub
        //String str="ATGCATGCATGCATGC";

        char[] str_array=str.toCharArray();
        char[] rna= Arrays.copyOf(str_array, str_array.length);

        for (int i=0;i<rna.length;i++)
        {
            if (rna[i]=='A')
                rna[i]='U';
            else if (rna[i]=='T')
                rna[i]='A';
            else if (rna[i]=='G')
                rna[i]='C';
            else if (rna[i]=='C')
                rna[i]='G';
            else
            {
                //System.out.println("Seq is wrong.");
                return "EXIT";
            }
        }

        String rna_str=String.valueOf(rna);
        //System.out.println(rna_str);
        return rna_str;

    }



    // program to convert mRna to protein sequence
    public ArrayList<String> Convert_mRNA_to_Protein(String rna)
    {
        // TODO Auto-generated method stub
        //String rna="AUGAAAGGCAGCCCAGCGUGAUGCAACGUGAAUAUGGACCGCAGACACAUGGGCUGCCCUGAAUGACCCGGGAAAUUUCAGGCAAGCCCAAUGAAACCCUGCGCGGUA";

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

        /*for (int i=0;i<protein.size();i++)
            System.out.print(protein.get(i)+"-");
        System.out.println("Stop");
         */

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