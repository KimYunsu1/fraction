package com.example.fractionstart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.Arrays;


import static com.example.fractionstart1.MainActivity.curcount;
import static com.example.fractionstart1.MainActivity.currentcount;
import static com.example.fractionstart1.MainActivity.database;
import static com.example.fractionstart1.MainActivity.hig;
import static com.example.fractionstart1.MainActivity.k;
import static com.example.fractionstart1.MainActivity.low;
import static com.example.fractionstart1.MainActivity.mCount;
import static com.example.fractionstart1.MainActivity.max;
import static com.example.fractionstart1.MainActivity.mid;
import static com.example.fractionstart1.MainActivity.mycount;
import static com.example.fractionstart1.Main2Activity.no;
import static com.example.fractionstart1.MainActivity.ox;
import static com.example.fractionstart1.MainActivity.pagen;
import static com.example.fractionstart1.MainActivity.size;
import static com.example.fractionstart1.MainActivity.page;
import static com.example.fractionstart1.MainActivity.uid;
import static com.example.fractionstart1.login2.totalcounth;
import static com.example.fractionstart1.login2.totalcountl;
import static com.example.fractionstart1.login2.totalcountm;
import static com.example.fractionstart1.statics.nameofwrong;

public class totalscore extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView Mscore, Myrep, TAdd;
    int howscore;
    static int[] totalcount ={0,0,0,0,0,0};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalscore);

        mCount = 1;

        Mscore = findViewById(R.id.myscore);
        Myrep = findViewById(R.id.rep);
        TAdd = findViewById(R.id.addtxt);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        final DatabaseReference db = database.getReference();


        max(curcount);
        String c = "<현재 총 틀린항목>\n";
        for(int i = 0; i < 6; i++){
            c =  c + nameofwrong[i] + " : " +curcount[i] +"개\n";
        }

        TAdd.setText(c+"\n"+nameofwrong[k]+"을(를) 집중해서 풀어볼것");

        figureoutstatic();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //점수알아내기
                Object value = dataSnapshot.child("users").child(uid).child("score/" + no).getValue(Object.class);
                Mscore.setText(String.valueOf(value));

                howscore = Integer.parseInt(String.valueOf(value));

                    if (howscore > 17) {
                        Myrep.setText("고성취");
                        if (pagen == 0) {
                            db.child("statics/higq/no").setValue(1 + hig);
                            for (int i = 0; i < curcount.length; i++) {
                                db.child("statics/higq/wrongpart/" + i).setValue(totalcounth[i] + curcount[i]);
                                setuserp(nameofwrong[i], String.valueOf(totalcounth[i] + curcount[i]));
                                pagen = howscore;
                            }
                        }
                    } else if (howscore >= 14 && howscore <= 17) {
                        Myrep.setText("중간성취");
                        if (pagen == 0) {
                            db.child("statics/midq/no").setValue(1 + mid);
                            for (int i = 0; i < curcount.length; i++) {
                                db.child("statics/midq/wrongpart/" + i).setValue(totalcountm[i] + curcount[i]);
                                setuserp(nameofwrong[i], String.valueOf(totalcountm[i] + curcount[i]));
                                pagen = howscore;
                            }
                        }
                    } else if (howscore < 14 && howscore >= 0) {
                        Myrep.setText("저성취");
                        if (pagen == 0) {
                            db.child("statics/lowq/no").setValue(1 + low);
                            for (int i = 0; i < curcount.length; i++) {
                                db.child("statics/lowq/wrongpart/" + i).setValue(totalcountl[i] + curcount[i]);
                                setuserp(nameofwrong[i], String.valueOf(totalcountl[i] + curcount[i]));
                                pagen = howscore;
                            }
                        }
                    } else {
                        Myrep.setText("오류");
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    public void FirstPage(View view){
        page = 1;
        mCount = 1;
        Arrays.fill(ox, 0);
        size = 0;
        pagen = 0;
        no++;
        Arrays.fill(curcount, 0);
        finish();
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
    }

    public void Staticpage(View view) {
        finish();
        startActivity(new Intent(getApplicationContext(), statics.class));
    }

    public void Oxpage(View view) {
        int c=0;
        for(int i = 0; i < 6; i++){
            c =  c + curcount[i];
        }
        if(c>0) {
            mCount = 1;
            pagen = 1;
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if(c==0) {
            Toast.makeText(totalscore.this, "틀린 문제가 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    public void setuserp(String a, String b){
        FirebaseAnalytics mFirebaseAnalytics;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserProperty(a,b);
    }

    static public void figureoutstatic(){

        DatabaseReference db = database.getReference();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (int i = 0; i < 6; i++) {
                    Object totallow = dataSnapshot.child("statics/lowq/wrongpart/" + i).getValue();
                    totalcountl[i] = Integer.parseInt(totallow.toString());
                    Object totalhig = dataSnapshot.child("statics/higq/wrongpart/" + i).getValue();
                    totalcounth[i] = Integer.parseInt(totalhig.toString());
                    Object totalmid = dataSnapshot.child("statics/midq/wrongpart/" + i).getValue();
                    totalcountm[i] = Integer.parseInt(totalmid.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}

