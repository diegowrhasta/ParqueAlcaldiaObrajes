package com.example.diego.parquealcaldiaobrajes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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



public class RegistroBien extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;

    private TextView textViewSignin;

    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_bien);
        getSupportActionBar().hide();
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){

            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), registro1.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingrese su correo",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese su contraña",Toast.LENGTH_LONG).show();
            return;
        }



        progressDialog.setMessage("Registrando...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), registro1.class));
                        }else{

                            Toast.makeText(RegistroBien.this,"Ya existe una cuenta con ese correo o la contraseña es muy corta",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }
}