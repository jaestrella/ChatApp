package com.iesvirgendelcarmen.dam.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.historial)
    TextView historial;

    @BindView(R.id.enviar)
    Button enviar;

    @BindView(R.id.entrada)
    EditText entrada;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        conectarFirebase();
        leerFirebase();
    }

    public void leerFirebase(){
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot != null && dataSnapshot.getValue() != null){
                    Mensaje msg=dataSnapshot.getValue(Mensaje.class);
                    insertarMensaje(msg);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void insertarMensaje(Mensaje msg){
        if(msg.getMsg() !=null) {
            historial.append("\n");
            historial.append(msg.getMsg());
        }
    }

    public void conectarFirebase(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("chat");
    }

    @OnClick(R.id.enviar)
    public void enviarMensaje(){
        String cadena=entrada.getText().toString();
        limpiarEntrada();
        Mensaje msg=new Mensaje(cadena);
        guardarMensaje(msg);
    }

    public void guardarMensaje(Mensaje msg){
        myRef.push().setValue(msg);
    }

    public void limpiarEntrada(){
        entrada.setText("");
    }
}
