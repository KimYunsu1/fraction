package com.example.fractionstart1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.fractionstart1.login2.setinital;


public class login extends AppCompatActivity {


    private static final String TAG = "EmailPassword";
    EditText mID, mPSW, Name, sex, age;
    TextView Info1;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static String userName, userS, userA, userE, userState;
    static int ib = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mID = findViewById(R.id.idsign);
        mPSW = findViewById(R.id.password);
        Name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        Info1 = (TextView) findViewById(R.id.info);
        Button Signin = findViewById(R.id.signin);
        Button Login = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Spinner elespin = (Spinner)findViewById(R.id.ele);
        Spinner sexspin = (Spinner)findViewById(R.id.sex);
        Spinner statespin = findViewById(R.id.state);

        statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userState = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        elespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userE = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sexspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userS = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void signinclick(View view) {

        ib = 1;
        if (Name.getText().toString().length() > 0) {
            userName = String.valueOf(Name.getText());
            userA = String.valueOf(age.getText());
            Info1.setText(userName);
        }

        String email = mID.getText().toString().trim();
        String password = mPSW.getText().toString().trim();


        progressDialog.setMessage("등록중입니다");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(login.this, "가입을 환영합니다", Toast.LENGTH_SHORT).show();
                    updateProfile();


                    /*finish();
                    startActivity(new Intent(getApplicationContext(), login2.class));*/

                } else {
                    Toast.makeText(login.this, "정확한 메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

        });





    }


    public void updateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

        FirebaseAnalytics mFirebaseAnalytics;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserProperty("Age", userA);
        mFirebaseAnalytics.setUserProperty("Gender", userS);
        mFirebaseAnalytics.setUserProperty("State", userState);
        mFirebaseAnalytics.setUserProperty("Region", userE);


        // [END update_profile]

    }

    public void loginclick(View view) {

        finish();
        startActivity(new Intent(getApplicationContext(), login2.class));

    }
}