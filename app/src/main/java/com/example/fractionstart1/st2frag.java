package com.example.fractionstart1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.fragment.app.Fragment;

import static com.example.fractionstart1.MainActivity.database;
import static com.example.fractionstart1.MainActivity.hig;
import static com.example.fractionstart1.MainActivity.k;
import static com.example.fractionstart1.MainActivity.low;
import static com.example.fractionstart1.MainActivity.max;
import static com.example.fractionstart1.MainActivity.mid;
import static com.example.fractionstart1.MainActivity.mycount;
import static com.example.fractionstart1.login2.totalcounth;
import static com.example.fractionstart1.login2.totalcountl;
import static com.example.fractionstart1.login2.totalcountm;
import static com.example.fractionstart1.statics.c;
import static com.example.fractionstart1.statics.whatpercent1;
import static com.example.fractionstart1.totalscore.totalcount;
import static com.example.fractionstart1.statics.nameofwrong;
import static com.example.fractionstart1.statics.qos;
public class st2frag extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.act_st2, container, false);

        final TextView myW1 = view.findViewById(R.id.myworst1);
        final TextView wrongg = view.findViewById(R.id.wrongthings);
        Button search = view.findViewById(R.id.serachst);
        int find;

        final String [] values = {"저성취", "중간성취", "고성취"};
        final Spinner spinner = (Spinner) view.findViewById(R.id.qofs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        myW1.setVisibility(View.INVISIBLE);
        wrongg.setVisibility(View.INVISIBLE);



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myW1.setVisibility(View.VISIBLE);
                wrongg.setVisibility(View.VISIBLE);
                String text = spinner.getSelectedItem().toString();

                c = ""+"오답률\n\n";
                if(text == "저성취"){
                    whatpercent1(totalcountl);
                    myW1.setText("가장 많이 틀린 항목 : " + nameofwrong[k]);
                    wrongg.setText(hig+"명의"+ values[0] +"학생의 "+ c +"\n"+ values[0] +"학생은 "+nameofwrong[k]+"을(를) 가장 많이 틀립니다.");
                    c = "오답률\n\n";
                } else if (text == "중간성취") {
                    whatpercent1(totalcountm);
                    myW1.setText("가장 많이 틀린 항목 : " + nameofwrong[k]);
                    wrongg.setText(mid+"명의 "+ values[0] +"학생의 "+ c +"\n"+ values[1] +"학생은 "+nameofwrong[k]+"을(를) 가장 많이 틀립니다.");
                    c = "오답률\n\n";
                } else if (text == "고성취") {
                    whatpercent1(totalcounth);
                    myW1.setText("가장 많이 틀린 항목 : " + nameofwrong[k]);
                    wrongg.setText(hig+"명의 "+ values[0] +"학생의 "+ c +"\n"+ values[2] + "학생은 "+nameofwrong[k]+"을(를) 가장 많이 틀립니다.");
                    c = "오답률\n\n";
                }

            }
        });



        return view;

    }


}

