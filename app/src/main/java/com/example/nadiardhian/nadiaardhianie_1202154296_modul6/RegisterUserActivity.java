package com.example.nadiardhian.nadiaardhianie_1202154296_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterUserActivity extends AppCompatActivity {
    Button daftar; //deklarasi button dafttar
    EditText edEmail, edPass;// deklarasi email dan password

    FirebaseAuth mAuth; //authentikasi firebase
    FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog mProgressDialog; //deklarasi dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user); //layout yang digunakan

        edEmail = (EditText)findViewById(R.id.edEmail); //get id email
        edPass = (EditText)findViewById(R.id.edPass); //get id password
        daftar = (Button)findViewById(R.id.daftar); //get id button daftar

        mProgressDialog = new ProgressDialog(this); //membuat dialog message


        mAuth = FirebaseAuth.getInstance(); //FIREBASE AUTHENTICATION INSTANCES


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) { //membuat FirebaseAuth.AuthStateListener baru


                FirebaseUser user = firebaseAuth.getCurrentUser(); //nama variable getCurrentUser dari FirebaseAuth pada FirebaseUser

                if (user!=null){ //Intent dari RegisterUserActivity ke PopotoanHome
                    Intent moveToHome = new Intent(RegisterUserActivity.this,PopotoanHome.class); //setelah dari class ini menuju ke clas popotoan home
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    startActivity(moveToHome); //memulai activity
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener); //FirebaseAuth menambahkan AuthStateListener dengan FirebaseAuth.AuthStateListener


        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //ketika button daftar di klik
                mProgressDialog.setTitle("Create Account");
                mProgressDialog.setMessage("Wait while account is being created...");
                mProgressDialog.show();

                //method createUserAccount
                createUserAccount();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //FirebaseAuth menambahkan AuthStateListener dengan FirebaseAuth.AuthStateListener
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //FirebaseAuth meremove AuthStateListener dengan FirebaseAuth.AuthStateListener
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void createUserAccount() {

        String emailUser, passUser;

        //getText dari Edit Text pada email dan password ketika login
        emailUser = edEmail.getText().toString().trim();
        passUser = edPass.getText().toString().trim();

        if(!TextUtils.isEmpty(emailUser) && !TextUtils.isEmpty(passUser)){

            //FirebaseAuth dengan signInWithEmailAndPassword
            mAuth.createUserWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(RegisterUserActivity.this,"Account Created Success",Toast.LENGTH_LONG).show();
                        //dismiss progress dialog
                        mProgressDialog.dismiss();

                        //Intent dari RegisterUserActivity ke PopotoanHome
                        Intent moveToHome = new Intent(RegisterUserActivity.this, PopotoanHome.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                        startActivity(moveToHome);

                    }else{
                        Toast.makeText(RegisterUserActivity.this,"Account Created Failed",Toast.LENGTH_LONG).show();
                        //dismiss progress dialog
                        mProgressDialog.dismiss();
                    }

                }
            });
        }
    }
}
