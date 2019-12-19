package com.example.fractionstart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

import static com.example.fractionstart1.MainActivity.database;
import static com.example.fractionstart1.MainActivity.uid;
import static com.example.fractionstart1.MainActivity.useruid;
import static com.example.fractionstart1.MainActivity.usub;
import static com.example.fractionstart1.login.ib;
import static com.example.fractionstart1.login.userA;
import static com.example.fractionstart1.login.userE;
import static com.example.fractionstart1.login.userName;
import static com.example.fractionstart1.login.userS;
import static com.example.fractionstart1.login.userState;

public class login2 extends AppCompatActivity implements View.OnClickListener {

    static String[] info = new String[4];

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignin;
    TextView textviewSingin;
    TextView textviewMessage;
    TextView textviewFindPassword;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;

    static int[] totalcountl ={0,0,0,0,0,0};
    static int[] totalcounth ={0,0,0,0,0,0};
    static int[] totalcountm ={0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textviewSingin= (TextView) findViewById(R.id.textViewSignin);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        textviewFindPassword = (TextView) findViewById(R.id.textViewFindpassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignin.setOnClickListener(this);
        textviewSingin.setOnClickListener(this);
        textviewFindPassword.setOnClickListener(this);
    }

    public static void setinital(){
        for(int i = 0; i < 6; i++){
            useruid.child(uid).child("wrongpart/"+i).setValue(0);
        }
        useruid.child(uid).child("info/이름").setValue(userName);
        useruid.child(uid).child("info/성별").setValue(userS);
        useruid.child(uid).child("info/초등학교").setValue(userE);
        useruid.child(uid).child("info/나이").setValue(userA);
        useruid.child(uid).child("info/상태").setValue(userState);

    }

    //firebase userLogin method
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
                            textviewMessage.setText("비밀번호를 확인해 주세요");
                        }
                    }
                });
    }

    public static void getInfo(){
        useruid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object valuename = dataSnapshot.child(uid).child("info/이름").getValue(Object.class);
                Object valueage = dataSnapshot.child(uid).child("info/나이").getValue(Object.class);
                Object valuesex = dataSnapshot.child(uid).child("info/성별").getValue(Object.class);
                Object valueele = dataSnapshot.child(uid).child("info/초등학교").getValue(Object.class);

                String[] info = {valuename.toString(), valueage.toString(), valuesex.toString(), valueele.toString()};
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view == buttonSignin) {
            getInfo();
            userLogin();
            if (ib == 1){
                setinital();
            }

        }
        if(view == textviewSingin) {
            finish();
            startActivity(new Intent(this, login.class));
        }
        if(view == textviewFindPassword) {
            finish();

        }
    }


}