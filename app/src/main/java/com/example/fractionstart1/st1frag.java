package com.example.fractionstart1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.fragment.app.Fragment;

import static com.example.fractionstart1.MainActivity.database;
import static com.example.fractionstart1.MainActivity.k;
import static com.example.fractionstart1.MainActivity.max;
import static com.example.fractionstart1.MainActivity.mycount;
import static com.example.fractionstart1.statics.nameofwrong;


public class st1frag extends Fragment {

    @Nullable
    @Override

    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.act_st1, container, false);

        TextView myW = view.findViewById(R.id.myworst);
        TextView Bwrong = view.findViewById(R.id.bestwrong);
        TextView wrongg = view.findViewById(R.id.wrongthings);

        k = 0;
        max(mycount);

        double sum = 0;
        for (int j= 0; j < 6; j++){
            sum += mycount[j];
        }
        myW.setText(nameofwrong[k]);

        String c = "<지금까지 총 틀린항목 오답률>\n";
        for(int i = 0; i < 6; i++){
            double s = ( mycount[i]  / sum) * 100.0 ;
            c =  c + nameofwrong[i] + " : " + Math.round(s*100)/100.0 +"%\n";
        }
        wrongg.setText(c+"\n"+nameofwrong[k]+"을(를) 집중해서 풀어볼것");

        return view;
    }

}
