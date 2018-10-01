package com.example.diego.parquealcaldiaobrajes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro1);
        //initializing firebase authentication object
        SelectImagen=(ImageButton) findViewById(R.id.imagen);
        mStorage= FirebaseStorage.getInstance().getReference();   //Storage de fotos
        SelectImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent =new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            imagenUri=data.getData();

            SelectImagen.setImageURI(imagenUri);  //imagen :)
        }
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

        mProgreso.setMessage("Guardando Datos");
        mProgreso.show();
        //Getting values from database

        if (imagenUri!=null){


            StorageReference foto=mStorage.child("Fotos").child(imagenUri.getLastPathSegment());
            foto.putFile(imagenUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {






                    String link= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    mProgreso.dismiss();

                    //Objeto de la informacion del usuario, lleva a la clase informacion para guardar los datos
                    UserInformation userInformation = new UserInformation(Propietario, Placa ,Clase ,Marca,carnet,Color,crpva,correo,link);

                    //getting the current logged in user


                    databaseReference.child(user.getUid()).setValue(userInformation);

                    mProgreso.dismiss();

                    Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            mProgreso.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            mProgreso.setMessage("Uploaded " + ((int) progress) + "%. ..");
                        }
                    });



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