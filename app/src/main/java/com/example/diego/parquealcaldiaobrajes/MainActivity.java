package com.example.diego.parquealcaldiaobrajes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    //defining views
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();


        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), bienvenido.class));
        }


        editTextEmail = (EditText) findViewById(R.id.correo);
        editTextPassword = (EditText) findViewById(R.id.contrasena);
        buttonSignIn = (Button) findViewById(R.id.ingresar);
        textViewSignup  = (TextView) findViewById(R.id.registro);

        progressDialog = new ProgressDialog(this);


        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }


    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();



        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"Ingrese su correo",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese su contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Ingresando...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){

                            finish();
                            startActivity(new Intent(getApplicationContext(),bienvenido.class));

                        }
                        else {


                            return;
                        }


                    }
                });

    }

    public void ingresar(View view)
    {
        Log.e("Hello","Eres gay");
        //Change
    }
    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();

        }

        if(view == textViewSignup){
            startActivity(new Intent(this, RegistroBien.class));
            finish();
        }
    }




}

