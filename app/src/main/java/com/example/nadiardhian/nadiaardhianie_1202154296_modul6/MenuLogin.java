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

public class MenuLogin extends AppCompatActivity {
    Button daftar,masuk; //deklarasi button daftar dan masuk
    EditText edEmail, edPass; //deklarasi email dan password

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_login); //layout yang digunakan

        edEmail = (EditText)findViewById(R.id.edEmail);//get id email
        edPass = (EditText)findViewById(R.id.edPass); //get id pass
        masuk = (Button)findViewById(R.id.masuk); //get id button masuk
        daftar = (Button)findViewById(R.id.daftar); //get id button daftar

        mProgressDialog = new ProgressDialog(this); //PROGRESS DIALOG CONTEXT
        mAuth = FirebaseAuth.getInstance(); //FIREBASE AUTHENTICATION INSTANCES

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) { //membuat FirebaseAuth.AuthStateListener baru

                //CHECKING USER PRESENCE
                //nama variable getCurrentUser dari FirebaseAuth pada FirebaseUser
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if( user != null )
                {
                    Intent moveToHome = new Intent(MenuLogin.this, PopotoanHome.class); //Intent dari MenuLogin ke PopotoanHome
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome); //memulai aktifitas

                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener); //FirebaseAuth menambahkan AuthStateListener dengan FirebaseAuth.AuthStateListener

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuLogin.this,RegisterUserActivity.class);  //Intent dari MenuLogin ke RegisterUserActivity
                startActivity(intent); //memulai activity
            }
        });

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //jika button masuk di klik
                
                mProgressDialog.setTitle("Loging in the user"); //dialog dengan judul tsb
                mProgressDialog.setMessage("Please wait...."); // dan pesannya
                mProgressDialog.show(); //dialog di tampilkan
                loginUser(); //method loginUser()
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener); //FirebaseAuth menambahkan AuthStateListener dengan FirebaseAuth.AuthStateListener
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener); //FirebaseAuth meremove AuthStateListener dengan FirebaseAuth.AuthStateListener
    }

    private void loginUser() {

        String userEmail, userPassword;

        //getText dari Edit Text pada email dan password ketika login
        userEmail = edEmail.getText().toString().trim();
        userPassword = edPass.getText().toString().trim();

        if( !TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword))
        {

            //FirebaseAuth dengan signInWithEmailAndPassword
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if( task.isSuccessful())
                    {

                        //dismiss progress dialog
                        mProgressDialog.dismiss();
                        //intent dari MenuLogin ke PopotoanHome
                        Intent moveToHome = new Intent(MenuLogin.this, PopotoanHome.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(moveToHome);

                    }else
                    {

                        Toast.makeText(MenuLogin.this, "Unable to login user", Toast.LENGTH_LONG).show();
                        //dismiss progress dialog
                        mProgressDialog.dismiss();

                    }

                }
            });

        }else
        {

            //Jika email atau password kosong
            Toast.makeText(MenuLogin.this, "Please enter user email and password", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();

        }
    }

}
