package com.example.fractionstart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.fractionstart1.MainActivity.currentcount;
import static com.example.fractionstart1.MainActivity.database;
import static com.example.fractionstart1.MainActivity.findVal;
import static com.example.fractionstart1.MainActivity.mCount;
import static com.example.fractionstart1.MainActivity.mycount;
import static com.example.fractionstart1.MainActivity.uid;
import static com.example.fractionstart1.MainActivity.pagen;
import static com.example.fractionstart1.MainActivity.user;
import static com.example.fractionstart1.MainActivity.useruid;
import static com.example.fractionstart1.login.userName;
import static com.example.fractionstart1.login.userState;


public class Main2Activity extends AppCompatActivity {

    static int no = 1;
    TextView Q2, NameP, TN;
    Button setq, setset;
    EditText Question, Answer;
    String number;
    Spinner noofqspin;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference("users/"+uid);
        db.child("score").child("/"+no).setValue(0);
        findVal();

        Q2 = findViewById(R.id.q2);
        NameP = findViewById(R.id.textname);
        setq = findViewById(R.id.setq);
        Question = findViewById(R.id.qset);
        Answer = findViewById(R.id.aset);
        TN = findViewById(R.id.textno);
        setset = findViewById(R.id.setfin);


        noofqspin = findViewById(R.id.noofq);
        String list1[] ={"1","2","3"};

        noofqspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                number = String.valueOf(parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                number = "1";

            }
        });


        Question.setVisibility(View.INVISIBLE);
        Answer.setVisibility(View.INVISIBLE);
        noofqspin.setVisibility(View.INVISIBLE);
        TN.setVisibility(View.INVISIBLE);
        setset.setVisibility(View.INVISIBLE);

        if(userState == "학생"){
            setq.setVisibility(View.INVISIBLE);

        } else {
            setq.setVisibility(View.VISIBLE);

        }

        NameP.setText(user.getDisplayName()+"님");
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void Starttest(View view) {
        mCount = 1;
        pagen = 0;
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void weakman(View view) {
        mCount = 1;
        pagen = 2;
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    public void Teachersetq(View view) {
        DatabaseReference db1 = database.getReference();
        db1.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.child("qandabox/qbox/" + number).getValue(Object.class);
                Object value2 = dataSnapshot.child("qandabox/abox/" + number).getValue(Object.class);
                Question.setText(value.toString());
                Answer.setText(value2.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        setset.setVisibility(View.VISIBLE);
        Question.setVisibility(View.VISIBLE);
        Answer.setVisibility(View.VISIBLE);
        noofqspin.setVisibility(View.VISIBLE);
        TN.setVisibility(View.VISIBLE);
    }


    public void SetFin(View view) {
        String A = String.valueOf(Answer.getText());
        String Q = String.valueOf(Question.getText());
        DatabaseReference db1 = database.getReference();
        db1.child("qandabox/abox/"+number).setValue(A);
        db1.child("qandabox/qbox/"+number).setValue(Q);
    }
}
