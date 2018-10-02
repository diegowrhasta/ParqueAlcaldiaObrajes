package com.example.diego.parquealcaldiaobrajes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

//Aca haremos el registro de los datos
public class registro1 extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    private ImageButton SelectImagen;
    private StorageReference mStorage;

    private static  final int GALLERY_REQUEST=1;
    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Uri imagenUri=null;
    Uri descargarURL;

    //defining a database reference
    private DatabaseReference databaseReference;
    private ProgressDialog mProgreso;
    //our new views
    private EditText editTextPropietario, editTextPlaca, editTextClase, editTextMarca, editTextcarnet, editTextColor,editTextCrpva;
    private Button buttonSave;
    //declaramos variables de firebase
    private DatabaseReference databaseCliente,databaseAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro1);
        //initializing firebase authentication object
        SelectImagen=(ImageButton) findViewById(R.id.imagen);
        mStorage= FirebaseStorage.getInstance().getReference();   //Storage de fotos
        //iniciamos las variables de firebase para el cliente y auto
        databaseCliente = FirebaseDatabase.getInstance().getReference("Cliente");
        databaseAuto = FirebaseDatabase.getInstance().getReference("Auto");
        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();
        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, RegistroBien.class));
        }

        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        //getting the views from xml resource
        editTextPropietario= (EditText) findViewById(R.id.propietario);
        editTextPlaca = (EditText) findViewById(R.id.placa);
        editTextClase = (EditText) findViewById(R.id.Clase);
        editTextMarca = (EditText) findViewById(R.id.marca);
        editTextcarnet= (EditText) findViewById(R.id.carnet);
        editTextColor = (EditText) findViewById(R.id.color);
        editTextCrpva = (EditText) findViewById(R.id.crpva);




        buttonSave = (Button) findViewById(R.id.registro);
        mProgreso=new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("" + user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }





    private void saveUserInformation() {

        final   String Propietario = editTextPropietario.getText().toString().trim();
        final String Placa = editTextPlaca.getText().toString().trim();
        final String Clase = editTextClase.getText().toString().trim();
        final String Marca= editTextMarca.getText().toString().trim();
        final String carnet= editTextcarnet.getText().toString().trim();

        final String Color= editTextColor.getText().toString().trim();
        final String crpva= editTextCrpva.getText().toString().trim();

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        final String correo= user.getEmail();

        if (!TextUtils.isEmpty(Propietario)) {

            String link="NULL";
            String idCliente = databaseCliente.push().getKey();
            databaseCliente = FirebaseDatabase.getInstance().getReference("Cliente");
            //objeto que indica los datos especificos del auto
            Auto auto = new Auto(Placa,Clase ,Marca,Color,crpva,link);
            //objeto que indica los datos del usuario
            UserInformation userInformation = new UserInformation(Propietario,carnet,correo);
            //se ingresan los datos del usuario en la base
            databaseCliente.child(idCliente).setValue(userInformation);
            //se genera un codigo para el auto
            String idAuto = databaseAuto.push().getKey();
            //se ingrsean los datos del auto
            databaseCliente.child(idCliente).child(idAuto).setValue(auto);

                    Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG).show();




        }

    }





    //displaying a success toast



    @Override
    public void onClick(View view) {
        //if logout is pressed
        if (view == buttonLogout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        if(view == buttonSave){
            saveUserInformation();
            startActivity(new Intent(this, bienvenido.class));

        }

    }
}