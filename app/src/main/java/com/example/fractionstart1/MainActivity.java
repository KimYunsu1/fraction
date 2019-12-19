package com.example.fractionstart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.fractionstart1.Main2Activity.no;

import static com.example.fractionstart1.login.userS;


public class MainActivity extends AppCompatActivity {

    static int uadd, usub, ufra, udec, udig, ucon, hig, mid, low, pagen = 0, page=1, k = 0, weaksize = 0;


    static String currentcount;

    static int mCount = 1, score = 0, size=0;
    static int mycount[] = {0,0,0,0,0,0};
    static int setwrong[]= {0,0,0,0,0,0};
    static int curcount[]= {0,0,0,0,0,0};
    static int weakmode[] = new int[20];
    static int ox[] = new int[20];




    TextView Q1, REALA1;
    EditText A1, EQ1;
    Button SUBMIT1;


    FirebaseAuth firebaseAuth;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static String uid = user.getUid();
    static DatabaseReference useruid = database.getReference("users");
    DatabaseReference myq1 = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Q1 = findViewById(R.id.q1);
        SUBMIT1 = findViewById(R.id.submit1);
        REALA1 = findViewById(R.id.reala1) ;
        REALA1.setVisibility(View.INVISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();
        A1 = findViewById(R.id.a1);
        EQ1 =  findViewById(R.id.eq1);
        Q1 = findViewById(R.id.q1);
        mCount = 1;
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setPositiveButton("시작", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if(pagen == 0) {
            alert.setMessage("※ 다음 수학 문제를 잘 읽고, 식과 답을 모두 적으세요. \n" +
                    "※ 모든 문제는 순서대로 풀도록 하세요. 단, 잘 모를 경우 제출 버튼 클릭 후 다음 문제로 넘어가세요.");
            alert.show();
            questionmake();
        } else if(pagen ==1) {
            SUBMIT1.setEnabled(false);
            REALA1.setVisibility(View.VISIBLE);
            oxnote();
        } else if(pagen == 2){
            max(mycount);
            weakm();
            weaksize = 0;
        }

    }

    public static void findVal() {
        //개인이 틀린 부분 데이터 값 가져오기
        useruid.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int i =0; i < mycount.length; i++) {
                    Object mycounti = dataSnapshot.child("wrongpart/"+i).getValue(Object.class);
                    mycount[i] = Integer.parseInt(String.valueOf(mycounti));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //질문 데이터 + 속성 가져오기
    public void questionmake() {
        myq1.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mCount < page + 1 && mCount >0) {
                    Object valueh = dataSnapshot.child("statics/higq/no").getValue(Object.class);
                    Object valuem = dataSnapshot.child("statics/midq/no").getValue(Object.class);
                    Object valuel = dataSnapshot.child("statics/lowq/no").getValue(Object.class);

                    Object value = dataSnapshot.child("qandabox/qbox/" + mCount).getValue(Object.class);
                    Object value2 = dataSnapshot.child("qandabox/abox/" + mCount).getValue(Object.class);
                    if(value == null){
                        finish();
                        startActivity(new Intent(getApplicationContext(), totalscore.class));
                    } else {
                        hig = Integer.parseInt(valueh.toString());
                        mid = Integer.parseInt(valuem.toString());
                        low = Integer.parseInt(valuel.toString());

                        for (int i = 0; i < setwrong.length; i++) {
                            Object add1 = dataSnapshot.child("settingbox/" + mCount + "/" + i).getValue(Object.class);
                            setwrong[i] = Integer.parseInt(add1.toString());
                        }
                        Q1.setText(mCount+". "+value.toString());
                        REALA1.setText(value2.toString());
                    }

                } else {
                    finish();
                    startActivity(new Intent(getApplicationContext(), totalscore.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void weakfinder(){
        myq1.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int no1 = 0;
                for (int j = 1; j < 21; j++) {
                    for (int i = 0; i < setwrong.length; i++) {
                        Object add1 = dataSnapshot.child("settingbox/" + j + "/" + i).getValue(Object.class);
                        setwrong[i] = Integer.parseInt(add1.toString());
                    }
                    if (setwrong[k] == 1) {
                        weakmode[no1] = j;
                        no1++;
                        weaksize++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void weakm(){

        weakfinder();
        myq1.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mCount < weaksize + 1 && mCount > 0) {
                    Object value = dataSnapshot.child("qandabox/qbox/" + weakmode[mCount-1]).getValue(Object.class);
                    Object value2 = dataSnapshot.child("qandabox/abox/" + weakmode[mCount-1]).getValue(Object.class);
                    Object valueh = dataSnapshot.child("statics/higq/no").getValue(Object.class);
                    Object valuem = dataSnapshot.child("statics/midq/no").getValue(Object.class);
                    Object valuel = dataSnapshot.child("statics/lowq/no").getValue(Object.class);

                    if (value == null) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), totalscore.class));
                    } else {
                        hig = Integer.parseInt(valueh.toString());
                        mid = Integer.parseInt(valuem.toString());
                        low = Integer.parseInt(valuel.toString());
                        Q1.setText(mCount+". "+value.toString());
                        REALA1.setText(mCount+". "+value2.toString());
                    }


                } else {
                    finish();
                    startActivity(new Intent(getApplicationContext(), totalscore.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void oxnote(){
        myq1.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (mCount < size + 1 && mCount > 0) {
                        score = 0;
                        int a = ox[mCount - 1];
                        Object value = dataSnapshot.child("qandabox/qbox/"+ a).getValue(Object.class);
                        Object value2 = dataSnapshot.child("qandabox/abox/"+ a).getValue(Object.class);


                        Q1.setText(a+". "+value.toString());
                        REALA1.setText(a+". "+value2.toString());

                    } else {
                        finish();
                        startActivity(new Intent(getApplicationContext(), totalscore.class));
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    static public void max(int[] x){

        findVal();
        int max = x[0];
        for(int j=0; j < x.length; j++){
            if(max < x[j]){
                max = x[j];
                k = j;
            }
        }
    }


    public void findanswer() {

        String answer = A1.getText().toString();
        //정답확인
        if (!answer.equals(REALA1.getText().toString())) {
            Toast.makeText(getApplicationContext(),"오답",Toast.LENGTH_SHORT).show();
            for(int i=0; i<6; i++){
                useruid.child(uid).child("wrongpart/"+i).setValue(mycount[i] + setwrong[i]);
                curcount[i] = setwrong[i] + curcount[i];
            }
            if (pagen == 2) {
                ox[size] = weakmode[mCount-1];
            } else {
                ox[size] = mCount;
            }

            REALA1.setVisibility(View.VISIBLE);
            score += 0;
            useruid.child(uid).child("score/"+no).setValue(score);
            size += 1;

        } else {
            Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_LONG).show();
            score +=1 ;
            useruid.child(uid).child("score/"+no).setValue(score);

        }
        if (pagen == 2) {
            useruid.child(uid).child("answerbox/q"+weakmode[mCount-1]+"/answer").setValue(answer);
            useruid.child(uid).child("answerbox/q"+weakmode[mCount-1]+"/question").setValue(EQ1.getText().toString());
        } else {
            useruid.child(uid).child("answerbox/q"+mCount+"/answer").setValue(answer);
            useruid.child(uid).child("answerbox/q"+mCount+"/question").setValue(EQ1.getText().toString());
        }

    }

    public void Clicksubmit1(View view) {

        findVal(); //틀린부분찾아옴
        findanswer();

    }

   public void Clicknext1(View view) {

        EQ1.setText(null);
        A1.setText(null);
        if(pagen == 0) {
            REALA1.setVisibility(View.INVISIBLE);
            page += 1;
            mCount += 1;
            questionmake();
        } else if(pagen == 2){
            REALA1.setVisibility(View.INVISIBLE);
            weakm();
            mCount +=1;
        } else {
            mCount += 1;
            oxnote();
        }

    }

    public void Clickbefore1(View view) {
        mCount = mCount - 1;
        page = page - 1;
        questionmake();
    }
}
