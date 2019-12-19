package com.example.fractionstart1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.fractionstart1.MainActivity.k;
import static com.example.fractionstart1.MainActivity.max;
import static com.example.fractionstart1.totalscore.figureoutstatic;

public class statics extends AppCompatActivity {


    static String qos;
    static int find = 0;
    int qcount=0;
    static String c = "";
    TextView scoreee;


    FragmentManager fragmentManager;
    static String[] nameofwrong = {"덧셈","뺄셈","분수","소수","숫자 배열","가분수 변환"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statics);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, new st1frag());
        fragmentTransaction.commit();
        scoreee = findViewById(R.id.scoree);

        figureoutstatic();

    }

    public void beforeClick(View view) {
        finish();
        startActivity(new Intent(getApplicationContext(), totalscore.class));
    }

    public void st1Click(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, new st1frag());
        fragmentTransaction.commit();
     }

    public void st2Click(View view) {
        c = "오답 비율\n";
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, new st2frag());
        fragmentTransaction.commit();
    }




    public static void whatpercent1(int[] x){

        k = 0;
        max(x);
        double sum = 0;
        for (int j= 0; j < 6; j++){
            sum += x[j];
        }
        for(int i = 0; i < 6; i++){
            double s = ( x[i]  / sum) * 100.0;
            c =  c + nameofwrong[i] + " : " + Math.round(s*100)/100.0 +"%\n";
        }
    }
}

